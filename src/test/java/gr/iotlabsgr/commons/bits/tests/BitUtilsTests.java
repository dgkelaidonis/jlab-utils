package gr.iotlabsgr.commons.bits.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import gr.dgk.utils.bits.BitUtils;

public class BitUtilsTests {
    private String originalString;
    private String binaryOfString;
    private String binaryA;
    private String binaryB;
    private String binaryAnd;
    private String binaryOr;
    private String binarySL;
    private String binarySR;

    @Before
    public void init() {
	originalString = "Hello World!";
	binaryOfString = "010010000110010101101100011011000110111100100000010101110110111101110010011011000110010000100001";
	binaryA = "000010"; // 2
	binaryB = "010100"; // 20
	binaryAnd = "0"; // 0
	binaryOr = "10110"; // 22
	binarySL = "1000"; // 8
	binarySR = "1"; // 1
    }

    @Test
    public void test_objectToBinary() {
	assertEquals("Test <objectToBinary(T)> conversion", binaryOfString, BitUtils.objectToBinary(originalString));
    }

    @Test
    public void test_binaryToObject() {
	assertEquals("Test <binaryToObject(Sting,T)> conversion", originalString,
		BitUtils.binaryToObject(binaryOfString, String.class));
    }

    @Test
    public void test_binaryAND() {
	assertTrue(BitUtils.binaryAND(binaryA, binaryB) == binaryAnd);
    }

    @Test
    public void test_binaryOR() {
	assertEquals(binaryOr, BitUtils.binaryOR(binaryA, binaryB));
    }

    @Test
    public void test_binaryXOR() {
	assertTrue(BitUtils.binaryXOR(binaryA, binaryB).equals(binaryOr));
    }

    @Test
    public void test_binaryShiftLeft() {
	assertEquals(binarySL, BitUtils.binaryShiftLeft(binaryA, 2));
    }

    @Test
    public void test_binaryShiftRight() {
	assertEquals(binarySR, BitUtils.binaryShiftRight(binaryB, 4));
    }
}