package yt.sehrschlecht.javaconfig.serialization.annotation;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import yt.sehrschlecht.javaconfig.serialization.DefaultSerializationAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines an option as serializable. That means that its type will be serialized and deserialized.
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface SerializedOption {
    /**
     * @return The class of the adapter that should be used to serialize and deserialize the option. If empty, the default adapter will be used. The class must have an empty constructor.
     */
    Class<? extends TypeAdapter> adapter() default DefaultSerializationAdapter.class;
}
