package gr.iotlabsgr.commons.networking.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import gr.iotlabsgr.commons.networking.IPv4Utils;

public class IPv4UtilsTest {
    public final String CIDR = "192.168.32.1/24";
    public final String CIDR_DEFAULT_IP_RAW = "192.168.32.1";
    public final String CIDR_DEFAULT_IP_BINARY = "11000000101010000010000000000001";
    public final String CIDR_FIRST_IP_BINARY = "11000000101010000010000000000000";
    public final String CIDR_LAST_IP_BINARY = "11000000101010000010000011111111";
    public final String IP_INTO_RANGE = "192.168.32.5";
    public final String IP_OUT_OF_RANGE = "192.168.1.1";
    public IPv4Utils ipv4Utils;

    @Before
    public void initializeClassUnderTesting() {
	ipv4Utils = new IPv4Utils();
    }

    @Test
    public void testIPv4RawToBinary() throws UnknownHostException {
	assertEquals(CIDR_DEFAULT_IP_BINARY, ipv4Utils.ipv4RawToBinary(InetAddress.getByName(CIDR_DEFAULT_IP_RAW)));
    }

    @Test
    public void testCompareIPv4BigDecimalWithIPv4Long() throws UnknownHostException {
	assertEquals(ipv4Utils.ipv4RawToBigInteger(InetAddress.getByName(CIDR_DEFAULT_IP_RAW)).toString(),
		ipv4Utils.ipv4RawToLong(InetAddress.getByName(CIDR_DEFAULT_IP_RAW)).toString());
    }

    @Test
    public void testGetIPv4Class() throws UnknownHostException {
	assertEquals("Checking the class of the IP " + CIDR_DEFAULT_IP_RAW, 'C',
		ipv4Utils.getIPv4Class(InetAddress.getByName(CIDR_DEFAULT_IP_RAW)));
    }

    @Test
    public void testGetFirstCidrIPv4AddressBinary() throws UnknownHostException {
	assertEquals(CIDR_FIRST_IP_BINARY, ipv4Utils.getFirstCidrIPv4AddressBinary(CIDR));
    }

    @Test
    public void testGetLastCidrIPv4AddressBinary() throws UnknownHostException {
	assertEquals(CIDR_LAST_IP_BINARY, ipv4Utils.getLastCidrIPv4AddressBinary(CIDR));
    }

    @Test
    public void testIPv4AddressBelongsToCIDR() throws UnknownHostException {
	assertTrue(ipv4Utils.ipv4BelongsToCidr(InetAddress.getByName(IP_INTO_RANGE), CIDR));
    }

    @Test
    public void testIPv4AddressOutOfCIDR() throws UnknownHostException {
	assertFalse(ipv4Utils.ipv4BelongsToCidr(InetAddress.getByName(IP_OUT_OF_RANGE), CIDR));
    }
}
