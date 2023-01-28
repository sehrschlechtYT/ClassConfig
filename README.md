# ClassConfig
![](https://github.com/sehrschlechtYT/ClassConfig/actions/workflows/tests.yml/badge.svg)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/sehrschlechtYT/KeepItems/graphs/commit-activity)
[![](https://dcbadge.vercel.app/api/server/crHgFwH2Gt)](https://discord.gg/crHgFwH2Gt)
![](https://dcbadge.vercel.app/api/shield/450685365876162573)

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