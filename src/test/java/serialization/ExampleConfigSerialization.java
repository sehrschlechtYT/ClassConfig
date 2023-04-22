package serialization;

import org.jetbrains.annotations.NotNull;
import yt.sehrschlecht.classconfig.SimpleClassConfig;
import yt.sehrschlecht.classconfig.options.ConfigOption;
import yt.sehrschlecht.classconfig.serialization.annotation.SerializedOption;

import java.awt.*;
import java.io.File;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@SuppressWarnings("removal") // using deprecated methods for testing the backwards compatibility
public class ExampleConfigSerialization extends SimpleClassConfig {
    @ConfigOption
    @SerializedOption
    public Customer customer = new Customer("John Doe", new Address("123 Main Street", "New York City", "NY"), 69);

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

    @ConfigOption(type = House.class)
    @SerializedOption(adapter = HouseSerializer.class)
    public House house = new House(
            new Address("123 Main Street", "New York", "NY"),
            Color.RED,
            true,
            5
    );


    /**
     * Creates a new instance of the config. Must be called via super() in the constructor of the extending class.
     *
     * @param file The file to load the config from. Must be a yaml (.yml/.yaml) file.
     */
    public ExampleConfigSerialization(@NotNull File file) {
        super(file);
    }
}
