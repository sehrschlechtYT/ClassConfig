package yt.sehrschlecht.classconfig.serialization.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a class as serializable.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.TYPE)
public @interface SerializableClass {

    /**
     * @return The type of the constructor that should be used to create a new instance of the class.
     */
    ConstructorType constructorType();

    /**
     * Constructor types.
     */
    enum ConstructorType {
        /**
         * A constructor with 0 arguments will be used.
         */
        NO_ARGS,
        /**
         * A constructor with all fields as arguments will be used.
         */
        ALL_ARGS;
    }
}
