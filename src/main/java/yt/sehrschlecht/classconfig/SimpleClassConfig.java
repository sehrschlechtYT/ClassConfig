package yt.sehrschlecht.classconfig;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.Block;
import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yt.sehrschlecht.classconfig.options.ConfigOption;
import yt.sehrschlecht.classconfig.options.MigrateOption;
import yt.sehrschlecht.classconfig.serialization.annotation.SerializedOption;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A simple class config can be used to read data from a yaml config file.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public abstract class SimpleClassConfig {
    protected final File file;
    protected YamlDocument document;
    protected Map<String, Object> defaultValues;
    protected final Logger logger;
    private boolean initializedDefaultValues = false;
    private boolean hasInitialized = false;

    /**
     * Creates a new instance of the config. Must be called via super() in the constructor of the extending class.
     * @param file The file to load the config from. Must be a yaml (.yml/.yaml) file.
     */
    protected SimpleClassConfig(@NotNull File file) {
        Objects.requireNonNull(file, "File must not be null!");
        this.file = file;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    /**
     * Initializes the config and field values. This method must be called after the constructor.
     */
    public void initialize() {
        if(hasInitialized) {
            logger.warn("Config has already been initialized!");
            return;
        }
        hasInitialized = true;
        defaultValues = retrieveDefaultValues();
        initializedDefaultValues = true;
        registerSerializers();
        if(file.exists()) {
            try {
                document = YamlDocument.create(file);
                upgrade();
            } catch (IOException e) {
                logger.error("Failed to load config file", e);
            }
        } else {
            try {
                boolean created = file.createNewFile();
                if(created) {
                    document = YamlDocument.create(file);
                } else {
                    logger.error("Failed to create file {}!", file.toPath());
                    return;
                }
                document.reload();
                resetToDefaultValues();
                setComments();
                document.save();
            } catch (IOException e) {
                logger.error("Failed to create config file", e);
            }
        }
        try {
            document.save();
        } catch (IOException e) {
            logger.error("Failed to save config file", e);
        }
    }

    /**
     * Registers the serializers for all {@link SerializedOption}s.
     */
    @ApiStatus.Internal
    private void registerSerializers() {
        getOptions().forEach((field, option) -> {
            if(field.isAnnotationPresent(SerializedOption.class)) {
                SerializedOption serializedOption = field.getAnnotation(SerializedOption.class);
                registerSerializationType(field, serializedOption);
            }
        });
    }

    @SuppressWarnings("unchecked")
    @ApiStatus.Internal
    private <C> void registerSerializationType(Field field, SerializedOption serializedOption) {
        try {
            Class<? extends TypeAdapter<C>> adapterClass = (Class<? extends TypeAdapter<C>>) serializedOption.adapter();
            Class<C> typeClass = (Class<C>) field.getType();

            TypeAdapter<C> adapter = adapterClass.getConstructor(Class.class).newInstance(typeClass);
            StandardSerializer standardSerializer = StandardSerializer.getDefault();
            standardSerializer.register(typeClass, adapter);
        } catch (Exception e) {
            logger.error("Failed to create adapter for type {}!", field.getType().getSimpleName(), e);
        }
    }

    /**
     * @return The default values of the config. This method is only used by the constructor to initialize the default values.
     * @throws IllegalStateException If this method is called after the config has been initialized.
     */
    @ApiStatus.Internal
    private @NotNull Map<String, Object> retrieveDefaultValues() {
        if (initializedDefaultValues) {
            throw new IllegalStateException(
                    "Default values are already initialized! This method must not be called after the config has been initialized! Get the default values via the defaultValues field instead!"
            );
        }
        Map<String, Object> defaultValues = new HashMap<>();
        getOptions().forEach((field, option) -> {
            String key = getKey(field, option);
            defaultValues.put(key, getValue(field));
        });
        logger.debug("Default values: {}", defaultValues.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue()).collect(Collectors.joining(", ")));
        return defaultValues;
    }

    /**
     * Resets a config option to its default value. Default values are retrieved from the values of the option fields.
     * @param key The key of the option that should be reset
     * @return The default value the option was set to, or null
     */
    protected @Nullable Object resetOption(@NotNull String key) {
        Objects.requireNonNull(key, "Key must not be null!");
        Object def = defaultValues.get(key);
        if(def == null) return null;
        document.set(key, def);
        return def;
    }

    /**
     * Sets all config options in the config to their default values. After that, the fields are updated to reflect the changes.
     */
    protected void resetToDefaultValues() {
        defaultValues.forEach(document::set);
        updateFields();
    }

    /**
     * Options are migrated (if necessary) and the comments are set. After that, all options that are not yet set in the config are set to their default values.
     */
    protected void upgrade() {
        getOptions().forEach((field, option) -> {
            if(field.isAnnotationPresent(MigrateOption.class)) {
                migrateOption(field, option, field.getAnnotation(MigrateOption.class));
            }
        });
        defaultValues.forEach((key, value) -> {
            if(!document.contains(key)) {
                document.set(key, value);
            }
        });
        setComments();
        updateFields();
    }

    /**
     * @return A map containing all config options of the config. The key is the field of the option and the value is the config option annotation.
     */
    protected @NotNull Map<Field, ConfigOption> getOptions() {
        Map<Field, ConfigOption> options = new HashMap<>();
        for (Field field : getClass().getFields()) {
            if(field.isAnnotationPresent(ConfigOption.class)) {
                ConfigOption option = field.getAnnotation(ConfigOption.class);
                if(!verifyOption(field, option)) continue;
                options.put(field, option);
            }
        }
        return options;
    }

    /**
     * Migrates a config option from an old key to a new key. If the old key is not set or the new key is already set, nothing happens.
     * @param field The field of the option
     * @param option The config option annotation
     * @param migration The migration annotation
     */
    protected void migrateOption(@NotNull Field field, @NotNull ConfigOption option, @NotNull MigrateOption migration) {
        Objects.requireNonNull(field, "Field must not be null!");
        Objects.requireNonNull(option, "ConfigOption must not be null!");
        Objects.requireNonNull(migration, "MigrateOption must not be null!");
        if(document.contains(getKey(field, option))) return;
        if(migration.oldKeys().length == 0 || Arrays.stream(migration.oldKeys()).allMatch(String::isBlank)) return;
        boolean deleteOldValues = migration.deleteOldKeys();
        for (String oldKey : migration.oldKeys()) {
            if(document.contains(oldKey)) {
                Object value = document.get(oldKey);
                String newKey = getKey(field, option);
                document.set(newKey, value);
                logger.info("Migrated config option from key {} to key {} ({})", oldKey, newKey, (deleteOldValues ? "Deleting old keys" : "Keeping old keys"));
                break;
            }
        }
        if(deleteOldValues) {
            Arrays.stream(migration.oldKeys()).forEach(document::remove);
        }
    }

    /**
     * Verifies that the type of a field matches the type specified in the {@link ConfigOption} annotation.
     * @param field The field of the option
     * @param option The config option annotation
     * @return True if the types match, false otherwise
     */
    protected boolean verifyOption(@NotNull Field field, @NotNull ConfigOption option) {
        Objects.requireNonNull(field, "Field must not be null!");
        Objects.requireNonNull(option, "ConfigOption must not be null!");
        if(field.getType().isAssignableFrom(option.type())) {
            return true;
        } else {
            logger.error("ConfigOption {} was defined as type {}, yet the field's default value is of type {}. The option will be skipped.",
                    getKey(field, option), option.type().getSimpleName(), field.getType().getSimpleName());
            return false;
        }
    }

    /**
     * @param field The field of the option
     * @param option The config option annotation
     * @return The key of the option. If the key is not set in the annotation, the field name is used.
     */
    protected String getKey(@NotNull Field field, @NotNull ConfigOption option) {
        Objects.requireNonNull(field, "Field must not be null!");
        Objects.requireNonNull(option, "ConfigOption must not be null!");
        if(option.key() == null || option.key().isBlank()) {
            return field.getName();
        }
        return option.key();
    }

    /**
     * @param field An accessible field
     * @return The value of the field
     */
    protected @Nullable Object getValue(@NotNull Field field) {
        Objects.requireNonNull(field, "Field must not be null!");
        try {
            logger.debug("Getting value of field {}", field.getName());
            logger.debug("Field value: {}", field.get(this));
            return field.get(this);
        } catch (IllegalAccessException e) {
            logger.error("Failed to get value of field {}", field.getName(), e);
            return null;
        }
    }

    /**
     * Sets the comments of all config options in the config, if they are set via the {@link ConfigOption} annotation.
     */
    protected void setComments() {
        getOptions().forEach(((field, option) -> {
            if(option.comments().length == 0 || Arrays.stream(option.comments()).allMatch(String::isBlank)) return;
            String key = getKey(field, option);
            Block<?> block = document.getBlock(key);
            block.removeComments();
            block.setComments(List.of(option.comments()));
        }));
    }

    /**
     * Reloads the config from the file. After that, the fields are updated to reflect the changes.
     */
    public void reload() {
        try {
            document.reload();
            updateFields();
        } catch (IOException e) {
            logger.error("Failed to reload config", e);
        }
    }

    /**
     * Updates all fields of the config to reflect the values in the config file.
     */
    protected void updateFields() {
        getOptions().forEach(((field, option) -> {
            String key = getKey(field, option);
            Object newValue = document.get(key);
            if(newValue == null) {
                Object defaultValue = resetOption(key);
                if(defaultValue == null) return;
                try {
                    field.set(this, defaultValue);
                } catch (IllegalAccessException e) {
                    logger.error("Failed to reset field {} to default value", field.getName(), e);
                }
                return;
            }
            try {
                field.set(this, document.get(key));
            } catch (IllegalAccessException e) {
                logger.error("Failed to update field", e);
            }
        }));
    }
}
