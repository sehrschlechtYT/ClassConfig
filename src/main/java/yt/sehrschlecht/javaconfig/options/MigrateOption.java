package yt.sehrschlecht.javaconfig.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to set old keys for a config option that has been renamed.
 * The annotation can also be used just to delete old options: Set {@link #oldKeys()} to an empty array and enable {@link #deleteOldKeys()}.<br>
 * Warning: Changing the type of a config option is not supported and will cause errors. Create a new option instead.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface MigrateOption {
    /**
     * @return An array of the keys the option previously had.
     */
    String[] oldKeys();

    /**
     * @return Whether the old keys should be removed from the config after migrating.
     */
    boolean deleteOldKeys() default true;
}
