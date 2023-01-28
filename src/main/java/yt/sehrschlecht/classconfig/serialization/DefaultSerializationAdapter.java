package yt.sehrschlecht.classconfig.serialization;

import dev.dejvokep.boostedyaml.block.Block;
import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yt.sehrschlecht.classconfig.serialization.annotation.SerializableClass;
import yt.sehrschlecht.classconfig.serialization.annotation.Serialize;
import yt.sehrschlecht.classconfig.serialization.annotation.SerializeAllFields;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The default adapter for serialization. It serializes all fields that are annotated with {@link Serialize}.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class DefaultSerializationAdapter<T> implements TypeAdapter<T> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Class<?> typeClass;

    /**
     * Creates a new instance of the adapter.
     * @param typeClass The class of the type to serialize.
     */
    public DefaultSerializationAdapter(Class<?> typeClass) {
        this.typeClass = typeClass;
    }

    /**
     * Serializes the given instance into a map.
     * <p>
     * The returned map does not need to (but may) contain the type identifier: one entry in the top-level map (the one
     * returned), where the key is defined by the serializer (<code>==</code> for {@link
     * StandardSerializer#getDefault()}) and the value identifies the serialized type - either by the full canonical
     * classname (e.g. <code>me.name.project.objects.CustomObject</code>) or it's alias. <b>If you decide to include a
     * type identifier, it must be registered (this requirement is not verified).</b>
     * <p>
     * If the returned map does not contain the identifier, the {@link StandardSerializer serializer} will automatically
     * use the full classname.
     *
     * @param object object to serialize
     * @return the serialized object
     */
    @NotNull
    @Override
    public Map<Object, Object> serialize(@NotNull T object) {
        if(!object.getClass().isAnnotationPresent(SerializableClass.class)) {
            throw new IllegalStateException("Class " + object.getClass().getSimpleName() + " is not annotated with @" + SerializableClass.class.getSimpleName() + " and cannot be serialized!");
        }
        Map<Object, Object> map = new HashMap<>();
        SerializeAllFields allFields = object.getClass().getAnnotation(SerializeAllFields.class);
        boolean serializeAllFields = allFields != null;
        for (Field field : object.getClass().getDeclaredFields()) {
            if(field.isAnnotationPresent(Serialize.class) || (serializeAllFields && Arrays.stream(allFields.ignoredTypes()).noneMatch(field.getType()::equals))) {
                field.setAccessible(true);
                Serialize annotation = field.getAnnotation(Serialize.class);
                String key = annotation == null ? "" : annotation.key();
                if(key.isBlank()) key = field.getName();
                try {
                    Object value = field.get(object);
                    map.put(key, value);
                } catch (IllegalAccessException e) {
                    logger.error("Failed to serialize field {} of type {}!", field.getName(), field.getType().getSimpleName());
                }
            }
        }
        return map;
    }

    /**
     * Deserializes the given map into instance of this type.
     * <p>
     * The given map is a raw object map; there are no {@link Block blocks}, just native Java objects themselves.
     * <p>
     * Use {@link #toStringKeyedMap(Map)} to convert the map.
     *
     * @param map the raw map to deserialize
     * @return the deserialized object
     */
    @NotNull
    @Override
    public T deserialize(@NotNull Map<Object, Object> map) {
        SerializableClass classAnnotation = typeClass.getAnnotation(SerializableClass.class);
        if(classAnnotation == null) {
            throw new IllegalStateException("Class " + typeClass.getSimpleName() + " is not annotated with @" + SerializableClass.class.getSimpleName() + " and cannot be deserialized!");
        }
        SerializableClass.ConstructorType constructorType = classAnnotation.constructorType();
        if(constructorType == SerializableClass.ConstructorType.NO_ARGS) {
            try {
                SerializeAllFields allFields = typeClass.getAnnotation(SerializeAllFields.class);
                boolean serializeAllFields = allFields != null;
                //noinspection unchecked
                T instance = (T) typeClass.getConstructor().newInstance();
                for (Field field : typeClass.getDeclaredFields()) {
                    if(!field.isAnnotationPresent(Serialize.class) && (!serializeAllFields || Arrays.stream(allFields.ignoredTypes()).noneMatch(field.getType()::equals))) continue;
                    field.setAccessible(true);
                    Serialize annotation = field.getAnnotation(Serialize.class);
                    String key = annotation.key();
                    if(key.isBlank()) key = field.getName();
                    Object value = map.get(key);
                    field.set(instance, value);
                }
                return instance;
            } catch (Exception e) {
                logger.error("Failed to find no-args constructor for class {}!", typeClass.getSimpleName());
            }
        } else if(constructorType == SerializableClass.ConstructorType.ALL_ARGS) {
            List<? extends Class<?>> constructorArgClasses = map.values().stream().map(Object::getClass).toList();
            try {
                //noinspection unchecked
                return (T) typeClass.getConstructor(constructorArgClasses.toArray(Class[]::new)).newInstance(map.values().toArray());
            } catch (Exception exception) {
                logger.error("Failed to find constructor for class {} with arguments {}!", typeClass.getSimpleName(), constructorArgClasses.stream().map(Class::getSimpleName).collect(Collectors.joining(", ")));
            }
        }
        return null;
    }
}
