package serialization;

import dev.dejvokep.boostedyaml.block.Block;
import dev.dejvokep.boostedyaml.serialization.standard.StandardSerializer;
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
public class HouseSerializer implements TypeAdapter<House> {
    /**
     * Serializes the given instance into a map.
     * <p>
     * The returned map does not need to (but may) contain the type identifier: one entry in the top-level map (the one
     * returned), where the key is defined by the serializer (<code>==</code> for {@link
     * StandardSerializer#getDefault()}) and the value identifies the serialized type - either by the full canonical
     * classname (e.g. <code>me.name.project.objects.CustomObject</code>) or it's alias. <b>If you decide to include a
     * type identifier, it must be registered (this requirement is not verified).</b>
     * <p>
     * If the returned map does not contain the identifier, the {@link StandardSerializer serializer} will automatically
     * use the full classname.
     *
     * @param object object to serialize
     * @return the serialized object
     */
    @NotNull
    @Override
    public Map<Object, Object> serialize(@NotNull House object) {
        Map<Object, Object> map = new HashMap<>();
        map.put("address", serializeAddress(object.getAddress()));
        Color color = object.getColor();
        map.put("color", color.getRed() + "," + color.getGreen() + "," + color.getBlue());
        map.put("rooms", object.getNumberOfRooms());
        map.put("garage", object.hasGarage());
        return map;
    }

    private Map<Object, Object> serializeAddress(@NotNull Address address) {
        Map<Object, Object> map = new HashMap<>();
        map.put("street", address.getStreet());
        map.put("city", address.getCity());
        map.put("state", address.getState());
        return map;
    }

    /**
     * Deserializes the given map into instance of this type.
     * <p>
     * The given map is a raw object map; there are no {@link Block blocks}, just native Java objects themselves.
     * <p>
     * Use {@link #toStringKeyedMap(Map)} to convert the map.
     *
     * @param map the raw map to deserialize
     * @return the deserialized object
     */
    @NotNull
    @Override
    public House deserialize(@NotNull Map<Object, Object> map) {
        return new House(
                deserializeAddress(toStringKeyedMap((Map<?, ?>) map.get("address"))),
                deserializeColor((String) map.get("color")),
                (boolean) map.get("garage"),
                (int) map.get("rooms")
        );
    }

    private Address deserializeAddress(@NotNull Map<String, Object> map) {
        return new Address(
                (String) map.get("street"),
                (String) map.get("city"),
                (String) map.get("state")
        );
    }

    private Color deserializeColor(@NotNull String color) {
        String[] split = color.split(",");
        return new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
    }
}
