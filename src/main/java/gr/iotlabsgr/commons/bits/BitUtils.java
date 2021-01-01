package gr.iotlabsgr.commons.bits;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class BitUtils {

    public static <T> String objectToBinary(T dataObject) {
	if (dataObject instanceof Integer)
	    return Integer.toBinaryString((Integer) dataObject);
	else if (dataObject instanceof Long)
	    return Long.toBinaryString((Long) dataObject);
	else if (dataObject instanceof BigInteger)
	    return ((BigInteger) dataObject).toString(2);
	else if (dataObject instanceof String)
	    return bytesToBinary(((String) dataObject).getBytes());
	else
	    return null;
    }

    public static <T> T binaryToObject(String binaryString, Class<T> clazz) {
	if (clazz.getTypeName().equals(Integer.class.getTypeName()))
	    return (T) binaryToInteger(binaryString);
	else if (clazz.getTypeName().equals(Long.class.getTypeName()))
	    return (T) binaryToLong(binaryString);
	else if (clazz.getTypeName().equals(BigInteger.class.getTypeName()))
	    return (T) binaryToBigInteger(binaryString);
	else if (clazz.getTypeName().equals(String.class.getTypeName()))
	    return (T) binaryToString(binaryString);
	else if (clazz.isArray() && clazz.getTypeName().equals(Integer.class.getTypeName()))
	    return (T) binaryToByteArray(binaryString);
	else
	    return null;
    }

    public static BigInteger binaryToBigInteger(String binaryString) {
	return new BigInteger(binaryString, 2);
    }

    public static byte[] binaryToByteArray(String binaryString) {
	int CHAR_BIT_BLOCK = 8; // 8-bit = 1 char
	int BINARY_SIZE = binaryString.length(); // number of binary digits
	int BLOCKS_OF_BYTES = BINARY_SIZE / CHAR_BIT_BLOCK;// total chars
	byte[] bytes = new byte[BLOCKS_OF_BYTES];
	for (int blockIndex = 0; blockIndex < BLOCKS_OF_BYTES; blockIndex++) {
	    bytes[blockIndex] = Byte.parseByte(
		    binaryString.substring(CHAR_BIT_BLOCK * blockIndex, (blockIndex + 1) * CHAR_BIT_BLOCK), 2);
	}
	return bytes;
    }

    public static Integer binaryToInteger(String binaryString) {
	return new BigInteger(binaryString, 2).intValueExact();
    }

    public static Long binaryToLong(String binaryString) {
	return Long.parseLong(binaryString, 2);
    }

    public static String binaryToString(String binaryString) {
	int CHAR_BIT_BLOCK = 8; // 8-bit = 1 char
	int BINARY_SIZE = binaryString.length(); // number of binary digits
	int BINARY_BIT_BLOCKS = BINARY_SIZE / CHAR_BIT_BLOCK;// total chars
	String stringResult = "";
	for (int blockIndex = 0; blockIndex < BINARY_BIT_BLOCKS; blockIndex++) {
	    stringResult += (char) Integer.parseInt(
		    binaryString.substring(CHAR_BIT_BLOCK * blockIndex, (blockIndex + 1) * CHAR_BIT_BLOCK), 2);
	}
	return stringResult;
    }

    public static String bytesToBinary(byte[] byteArray) {
	StringBuilder binary = new StringBuilder();
	for (byte byteVal : byteArray) {
	    int bitsOfByte = byteVal;
	    for (int i = 0; i < 8; i++) {
		binary.append((bitsOfByte & 128) == 0 ? 0 : 1);
		bitsOfByte <<= 1;
	    }
	}
	return binary.toString();
    }

    public static String binaryAND(String binaryA, String binaryB) {
	return binaryToBigInteger(binaryA).and(binaryToBigInteger(binaryB)).toString(2);
    }

    public static String binaryOR(String binaryA, String binaryB) {
	return binaryToBigInteger(binaryA).or(binaryToBigInteger(binaryB)).toString(2);
    }

    public static String binaryXOR(String binaryA, String binaryB) {
	return binaryToBigInteger(binaryA).xor(binaryToBigInteger(binaryB)).toString(2);
    }

    public static String binaryShiftLeft(String binary, int shiftDistanseInBits) {
	return binaryToBigInteger(binary).shiftLeft(shiftDistanseInBits).toString(2);
    }

    public String binaryShiftRight(String binary, int shiftDistanseInBits) {
	return binaryToBigInteger(binary).shiftRight(shiftDistanseInBits).toString(2);
    }

    public static void main(String[] args) throws NumberFormatException, UnsupportedEncodingException {
	String string = "Hello World!";
	String binary = BitUtils.objectToBinary(string);
	System.out.println("String: " + string);
	System.out.println("  - String to binary: " + binary);
	string = (String) BitUtils.binaryToObject(binary, String.class);
	System.out.println("  - Binary to string: " + string);
    }
}