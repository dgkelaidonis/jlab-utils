package gr.iotlabsgr.commons.networking.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;

import gr.dgk.utils.networking.IPv4Utils;

public class IPv4UtilsTests {
    private String cidr, cidr_default_ip, cidr_default_ip_binary, cidr_first_ip_binary, cidr_last_ip_binary,
	    ip_into_range, ip_out_of_range, localhost, validIp, invalidIp, validCidr, invalidCidr;

    @Before
    public void init() {
	cidr = "192.168.32.1/24";
	cidr_default_ip = "192.168.32.1";
	cidr_default_ip_binary = "11000000101010000010000000000001";
	cidr_first_ip_binary = "11000000101010000010000000000000";
	cidr_last_ip_binary = "11000000101010000010000011111111";
	ip_into_range = "192.168.32.5";
	ip_out_of_range = "192.168.1.1";
	localhost = "127.0.0.1";
	validIp = "123.23.99.45";
	invalidIp = "353.23.991.12";
	validCidr = "192.168.1.1/24";
	invalidCidr = "192.168.1.12/855";
    }

    @Test
    public void test_isValidCidr() {
	assertTrue(IPv4Utils.isValidCidr(validCidr));
    }

    @Test
    public void test_isValidCidr_False() {
	assertFalse(IPv4Utils.isValidCidr(invalidCidr));
    }

    @Test
    public void test_isValidIPv4() {
	assertTrue(IPv4Utils.isValidIPv4(validIp));
    }

    @Test
    public void test_isValidIPv4_False() {
	assertFalse(IPv4Utils.isValidIPv4(invalidIp));
    }

    @Test
    public void test_isLocalhost() {
	assertTrue(IPv4Utils.isLocalhostAddress(localhost));
    }

    @Test
    public void test_IPv4RawToBinary() throws UnknownHostException {
	assertEquals(cidr_default_ip_binary, IPv4Utils.ipv4ToBinary(InetAddress.getByName(cidr_default_ip)));
    }

    @Test
    public void test_getIPv4Class() throws UnknownHostException {
	assertEquals("Checking the class of the IP " + cidr_default_ip, 'C',
		IPv4Utils.getIPv4Class(InetAddress.getByName(cidr_default_ip)));
    }

    @Test
    public void test_getFirstCidrIPv4AddressBinary() throws UnknownHostException {
	assertEquals(cidr_first_ip_binary, IPv4Utils.getFirstCidrIPv4AddressBinary(cidr));
    }

    @Test
    public void test_getLastCidrIPv4AddressBinary() throws UnknownHostException {
	assertEquals(cidr_last_ip_binary, IPv4Utils.getLastCidrIPv4AddressBinary(cidr));
    }

    @Test
    public void test_ipv4BelongsToCidr() throws UnknownHostException {
	assertTrue(IPv4Utils.ipv4BelongsToCidr(InetAddress.getByName(ip_into_range), cidr));
    }

    @Test
    public void test_ipv4BelongsToCidr_False() throws UnknownHostException {
	assertFalse(IPv4Utils.ipv4BelongsToCidr(InetAddress.getByName(ip_out_of_range), cidr));
    }
}