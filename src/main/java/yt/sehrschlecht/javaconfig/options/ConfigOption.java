package yt.sehrschlecht.javaconfig.options;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a config option.  The annotated field will be set to the value from the config file.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface ConfigOption {
    /**
     * @return The key of the config option. If empty, the field name will be used.
     */
    String key() default "";

    /**
     * @return The type of the config option. Used to verify the type of the value.
     */
    Class<?> type();

    /**
     * @return An array of comments that will be added to the config file. Comments will be overwritten every time the config file is saved.
     */
    String[] comments() default "";
}
