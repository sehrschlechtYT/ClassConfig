package yt.sehrschlecht.classconfig.serialization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines that all fields of a class should be serialized. You can mark specific fields with {@link Serialize} instead.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SerializeAllFields {
    Class<?>[] ignoredTypes() default {};
}
