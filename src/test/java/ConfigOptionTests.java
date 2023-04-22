import org.junit.jupiter.api.Test;
import serialization.ExampleConfigSerialization;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class ConfigOptionTests {

    @Test
    void testConfig() {
        File folder = new File("test_configs");
        if(!folder.exists()) folder.mkdirs();
        File configFile = new File(folder, "simple_test.yml");
        if(configFile.exists()) configFile.delete(); //delete old config file to make tests reproducible
        ExampleConfigSimple config = new ExampleConfigSimple();
        config.initialize();
        assertEquals("Hello World!", config.helloWorld);
        assertEquals("Hello World!", config.getDocument().getString("helloWorld"));

        assertEquals("This option was migrated from an old key!", config.exampleMigratedOption);
        assertEquals("This option was migrated from an old key!", config.getDocument().getString("iWasMigrated"));
        assertEquals(List.of(" Line 1", " Line 2", " Line 3", " Line 4"), config.getDocument().getBlock("iHaveMultipleComments").getComments());
    }

    @Test
    void testSerializedConfig() {
        File folder = new File("test_configs");
        if(!folder.exists()) folder.mkdirs();
        File configFile = new File(folder, "serialization_test.yml");
        if(configFile.exists()) configFile.delete(); //delete old config file to make tests reproducible
        ExampleConfigSerialization config = new ExampleConfigSerialization(configFile);
        config.initialize();

        assertEquals("John Doe", config.customer.getName());
        assertEquals("Address{street='123 Main Street', city='New York City', state='NY'}", config.customer.getAddress().toString());
        assertEquals(69, config.customer.getAge());

        /*assertEquals(new serialization.ManyFields(
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
        ), config.manyFields);*/

        /*assertEquals(1, config.manyFields.getaNumber());
        assertEquals("Hello World!", config.manyFields.getaString());
        assertTrue(config.manyFields.isaBoolean());
        assertEquals(69.0, config.manyFields.getaDouble());
        assertEquals(1.2345f, config.manyFields.getaFloat());
        assertEquals(1234567890L, config.manyFields.getaLong());
        assertEquals((short) 420, config.manyFields.getaShort());
        assertEquals((byte) 5, config.manyFields.getaByte());
        assertEquals('a', config.manyFields.getaChar());
        assertEquals(new int[]{1, 2, 3, 4, 5}, config.manyFields.getaNumberArray());
        assertEquals(new String[]{"Hello", "World", "!"}, config.manyFields.getaStringArray());
        assertEquals(new boolean[]{true, false, true, false, true}, config.manyFields.getaBooleanArray());
        assertEquals(new double[]{1.0, 2.0, 3.0, 4.0, 5.0}, config.manyFields.getaDoubleArray());
        assertEquals(new float[]{1.0f, 2.0f, 3.0f, 4.0f, 5.0f}, config.manyFields.getaFloatArray());
        assertEquals(new long[]{1L, 2L, 3L, 4L, 5L}, config.manyFields.getaLongArray());
        assertEquals(new short[]{1, 2, 3, 4, 5}, config.manyFields.getaShortArray());
        assertEquals(new byte[]{1, 2, 3, 4, 5}, config.manyFields.getaByteArray());
        assertEquals(new char[]{'a', 'b', 'c', 'd', 'e'}, config.manyFields.getaCharArray());*/

        try {
            List<String> lines = Files.readAllLines(configFile.toPath());
            String fileContent = String.join("\n", lines);
            assertEquals("""
                    manyFields:
                      aBoolean: true
                      aChar: a
                      aDoubleArray:
                      - 1.0
                      - 2.0
                      - 3.0
                      - 4.0
                      - 5.0
                      aNumberArray:
                      - 1
                      - 2
                      - 3
                      - 4
                      - 5
                      aLong: 1234567890
                      aCharArray:
                      - a
                      - b
                      - c
                      - d
                      - e
                      aFloat: 1.2345
                      aFloatArray:
                      - 1.0
                      - 2.0
                      - 3.0
                      - 4.0
                      - 5.0
                      aDouble: 69.0
                      aStringArray:
                      - Hello
                      - World
                      - '!'
                      aByteArray: !!binary |-
                        AQIDBAU=
                      aNumber: 1
                      aShort: 420
                      aBooleanArray:
                      - true
                      - false
                      - true
                      - false
                      - true
                      aShortArray:
                      - 1
                      - 2
                      - 3
                      - 4
                      - 5
                      aLongArray:
                      - 1
                      - 2
                      - 3
                      - 4
                      - 5
                      aByte: 5
                      ==: serialization.ManyFields
                    house:
                      rooms: 5
                      address:
                        city: New York
                        street: 123 Main Street
                        state: NY
                      color: 255,0,0
                      garage: true
                      ==: serialization.House
                    customer:
                      address:
                        city: New York City
                        street: 123 Main Street
                        state: NY
                        ==: serialization.Address
                      name: John Doe
                      age: 69
                      ==: serialization.Customer""", fileContent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
