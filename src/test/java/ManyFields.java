import yt.sehrschlecht.javaconfig.serialization.annotation.SerializableClass;
import yt.sehrschlecht.javaconfig.serialization.annotation.SerializeAllFields;

/**
 * @author sehrschlechtYT | https://github.com/sehrschlechtYT
 * @since 1.0
 */
@SerializableClass(constructorType = SerializableClass.ConstructorType.NO_ARGS)
@SerializeAllFields(ignoredTypes = {String.class})
public class ManyFields {
    private int aNumber;
    private String aString;
    private boolean aBoolean;
    private double aDouble;
    private float aFloat;
    private long aLong;
    private short aShort;
    private byte aByte;
    private char aChar;
    private int[] aNumberArray;
    private String[] aStringArray;
    private boolean[] aBooleanArray;
    private double[] aDoubleArray;
    private float[] aFloatArray;
    private long[] aLongArray;
    private short[] aShortArray;
    private byte[] aByteArray;
    private char[] aCharArray;

    public ManyFields(int aNumber, String aString, boolean aBoolean, double aDouble, float aFloat, long aLong, short aShort, byte aByte, char aChar, int[] aNumberArray, String[] aStringArray, boolean[] aBooleanArray, double[] aDoubleArray, float[] aFloatArray, long[] aLongArray, short[] aShortArray, byte[] aByteArray, char[] aCharArray) {
        this.aNumber = aNumber;
        this.aString = aString;
        this.aBoolean = aBoolean;
        this.aDouble = aDouble;
        this.aFloat = aFloat;
        this.aLong = aLong;
        this.aShort = aShort;
        this.aByte = aByte;
        this.aChar = aChar;
        this.aNumberArray = aNumberArray;
        this.aStringArray = aStringArray;
        this.aBooleanArray = aBooleanArray;
        this.aDoubleArray = aDoubleArray;
        this.aFloatArray = aFloatArray;
        this.aLongArray = aLongArray;
        this.aShortArray = aShortArray;
        this.aByteArray = aByteArray;
        this.aCharArray = aCharArray;
    }

    public ManyFields() {

    }

    public int getaNumber() {
        return aNumber;
    }

    public String getaString() {
        return aString;
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public double getaDouble() {
        return aDouble;
    }

    public float getaFloat() {
        return aFloat;
    }

    public long getaLong() {
        return aLong;
    }

    public short getaShort() {
        return aShort;
    }

    public byte getaByte() {
        return aByte;
    }

    public char getaChar() {
        return aChar;
    }

    public int[] getaNumberArray() {
        return aNumberArray;
    }

    public String[] getaStringArray() {
        return aStringArray;
    }

    public boolean[] getaBooleanArray() {
        return aBooleanArray;
    }

    public double[] getaDoubleArray() {
        return aDoubleArray;
    }

    public float[] getaFloatArray() {
        return aFloatArray;
    }

    public long[] getaLongArray() {
        return aLongArray;
    }

    public short[] getaShortArray() {
        return aShortArray;
    }

    public byte[] getaByteArray() {
        return aByteArray;
    }

    public char[] getaCharArray() {
        return aCharArray;
    }
}
