# ClassConfig
![](https://github.com/sehrschlechtYT/ClassConfig/workflows/tests/badge.svg)

ClassConfig allows the representation of yaml configuration files as classes.  
Config options can be easily accessed and modified.

## Basic Example:

```java
import yt.sehrschlecht.classconfig.SimpleClassConfig;
import yt.sehrschlecht.classconfig.options.ConfigOption;

public class ExampleConfig extends SimpleClassConfig {
    @ConfigOption(key = "settings.a_boolean", comments = "A basic boolean setting")
    public boolean aBoolean = true;
    
    public ExampleConfig() {
        super(new File("configs", "example.yml"));
    }
}

public class MainClass {
    public static void main(String[] args) {
        ExampleConfig config = new ExampleConfig();
        config.initialize();
        System.out.println("The boolean's value is " + config.aBoolean + "!");
    }
}
```

## Documentation

A full documentation is currently being worked on.