import dev.dejvokep.boostedyaml.YamlDocument;
import yt.sehrschlecht.javaconfig.options.ConfigOption;
import yt.sehrschlecht.javaconfig.options.MigrateOption;
import yt.sehrschlecht.javaconfig.SimpleJavaConfig;

import java.io.File;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ExampleConfigSimple extends SimpleJavaConfig {

    @ConfigOption(type = String.class, comments = "This is a comment!")
    public String helloWorld = "Hello World!";

    @MigrateOption(oldKeys = "exampleMigratedOption")
    @ConfigOption(type = String.class, key = "iWasMigrated", comments = "This option was migrated from an old key!")
    public String exampleMigratedOption = "This option was migrated from an old key!";

    @ConfigOption(type = String.class, comments = {
            "Line 1",
            "Line 2",
            "Line 3",
            "Line 4"
    })
    public String iHaveMultipleComments = "This option has multiple comments!";

    public ExampleConfigSimple() {
        super(new File("test_configs", "simple_test.yml"));
    }

    public YamlDocument getDocument() {
        return document;
    }
}
