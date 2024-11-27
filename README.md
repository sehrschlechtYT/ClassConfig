# ClassConfig
![](https://github.com/sehrschlechtYT/ClassConfig/actions/workflows/tests.yml/badge.svg)
[![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)](https://GitHub.com/sehrschlechtYT/KeepItems/graphs/commit-activity)
[![](https://jitpack.io/v/sehrschlechtYT/ClassConfig.svg)](https://jitpack.io/#sehrschlechtYT/ClassConfig)


[![](https://dcbadge.vercel.app/api/server/crHgFwH2Gt)](https://discord.gg/crHgFwH2Gt)
![](https://dcbadge.vercel.app/api/shield/450685365876162573)

> [!WARNING]
> This repository is currently unmaintained! No support will be offered regarding bugs or feature requests.

ClassConfig allows the representation of yaml configuration files as classes and config options as fields.
This prevents repeated method calls like `getConfig().get("key")` for accessing config options.

## Key features

- Loading of config files (using [BoostedYAML](https://github.com/dejvokep/boosted-yaml))
- Using fields for representing config options
- (De-)serialization of classes using either your own adapter or the default one (uses reflection)
- Migration of config options (moving them to a new key or just deleting them)
- Setting Comments for config options

Working with the library is easy. Most things are done via annotations.

## Documentation

### [Docs](https://sehrschlecht.gitbook.io/classconfig-documentation/)
### [Javadocs](https://sehrschlechtyt.github.io/ClassConfig/javadocs/)

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
