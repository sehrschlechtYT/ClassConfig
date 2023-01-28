import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.javaconfig.SimpleJavaConfig;
import yt.sehrschlecht.javaconfig.options.ConfigOption;
import yt.sehrschlecht.javaconfig.serialization.annotation.SerializedOption;

import java.io.File;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ExampleConfigSerialization extends SimpleJavaConfig {
    @ConfigOption(type = Customer.class)
    @SerializedOption
    public Customer customer = new Customer("John Doe", "123 Main Street", 69);

    @ConfigOption(type = ManyFields.class)
    @SerializedOption
    public ManyFields manyFields = new ManyFields(
            1,
            "Hello World!",
            true,
            69.0,
            1.2345f,
            1234567890L,
            (short) 420,
            (byte) 5,
            'a',
            new int[]{1, 2, 3, 4, 5},
            new String[]{"Hello", "World", "!"},
            new boolean[]{true, false, true, false, true},
            new double[]{1.0, 2.0, 3.0, 4.0, 5.0},
            new float[]{1.0f, 2.0f, 3.0f, 4.0f, 5.0f},
            new long[]{1L, 2L, 3L, 4L, 5L},
            new short[]{1, 2, 3, 4, 5},
            new byte[]{1, 2, 3, 4, 5},
            new char[]{'a', 'b', 'c', 'd', 'e'}
    );


    /**
     * Creates a new instance of the config. Must be called via super() in the constructor of the extending class.
     *
     * @param file The file to load the config from. Must be a yaml (.yml/.yaml) file.
     */
    protected ExampleConfigSerialization(@NotNull File file) {
        super(file);
    }
}
