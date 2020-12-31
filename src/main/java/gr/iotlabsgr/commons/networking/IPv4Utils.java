package gr.iotlabsgr.commons.networking;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import gr.iotlabsgr.commons.bit.BitUtils;

public class IPv4Utils {

    /**
     * This method extracts the IP of the web client that performed an HTTP request
     * towards an available HTTP endpoint, that inherits the Servlet API 3.0
     * properties.
     * 
     * @param request HttpServletRequest object that contains the Servlet API 3.0
     *                properties, for manipulating the incoming HTTP request.
     * @return The client IPv4/IPv6 form, as string data type.
     */
    public String getClientIp(HttpServletRequest request) {
	return (request != null) && (request.getHeader("X-Forwarded-For") != null)
		&& (!request.getHeader("X-Forwarded-For").isEmpty()) ? request.getHeader("X-Forwarded-For")
			: request.getRemoteAddr();
    }

    /**
     * This method finds the class, from A to E, of an IPv4 address, that is given
     * in normal blocks format, in string data type.
     * 
     * @param ipv4 As string in form of x.y.z.t
     * @return The letter (A-E), that corresponds to the class of the given IP
     */
    public char getIPv4Class(InetAddress ipv4) {
	int a = Integer.parseInt(ipv4.getHostAddress().split("\\.")[0]);
	if (a >= 0 && a <= 127)
	    return ('A');
	else if (a >= 128 && a <= 191)
	    return ('B');
	else if (a >= 192 && a <= 223)
	    return ('C');
	else if (a >= 224 && a <= 239)
	    return ('D');
	else
	    return ('E');
    }

    /**
     * This method returns the binary sequence of the first address that belongs to
     * the given CIDR. Based on the specification:
     * https://www.geeksforgeeks.org/program-to-find-class-broadcast-and-network-addresses/.
     * for finding the last address of the CIDR we should add bit '1' at the last n
     * positions of the binary sequence.
     * 
     * @param cidr in form x.y.z.t/n
     * @return The binary sequence of the last IPv4 address.
     * @throws UnknownHostException
     */
    public String getFirstCidrIPv4AddressBinary(String cidr) throws UnknownHostException {
	String ipv4 = cidr.split("/")[0];
	int prefix = Integer.parseInt(cidr.split("/")[1]);
	String ipv4Binary = ipv4RawToBinary(InetAddress.getByName(ipv4));
	/* total bits position 32-n */
	int totalNumberOfBits = 32 - prefix;
	/* first address: set last ' 32-n' bits of IP address to 0 */
	return setLastNbitsOfBinary(ipv4Binary, totalNumberOfBits, '0');
    }

    /**
     * This method returns the first IPv4 address into Long data type, by a given
     * CIDR.
     * 
     * @param cidr in form x.y.z.t/n
     * @return The Long value of the first IPv4 address.
     * @throws UnknownHostException
     */
    public Long getFirstCidrIPv4AddressLong(String cidr) throws UnknownHostException {
	return BitUtils.binaryToLong(getFirstCidrIPv4AddressBinary(cidr));
    }

    /**
     * This method returns the binary sequence of the last address that belongs to
     * the given CIDR. Based on the specification:
     * https://www.geeksforgeeks.org/program-to-find-class-broadcast-and-network-addresses/.
     * for finding the last address of the CIDR we should add bit '1' at the last n
     * positions of the binary sequence.
     * 
     * @param cidr in form x.y.z.t/n
     * @return The binary sequence of the last IPv4 address.
     * @throws UnknownHostException
     */
    public String getLastCidrIPv4AddressBinary(String cidr) throws UnknownHostException {
	String ipv4 = cidr.split("/")[0];
	int prefix = Integer.parseInt(cidr.split("/")[1]);
	String ipv4Binary = ipv4RawToBinary(InetAddress.getByName(ipv4));
	/* total bits position 32-n */
	int totalNumberOfBits = 32 - prefix;
	/* last address: set last '32-n' bits of IP address to 1 */
	return setLastNbitsOfBinary(ipv4Binary, totalNumberOfBits, '1');
    }

    /**
     * This method returns the last IPv4 address into Long data type, by a given
     * CIDR.
     * 
     * @param cidr in form x.y.z.t/n
     * @return The Long value of the last IPv4 address.
     * @throws UnknownHostException
     */
    public Long getLastCidrIPv4AddressLong(String cidr) throws UnknownHostException {
	return BitUtils.binaryToLong(getLastCidrIPv4AddressBinary(cidr));
    }

    /**
     * This method converts an IPv4 into Big Decimal.
     * 
     * @param ipv4 IPv4 in format x.y.z.t
     * @return The Big Integer value of the given IPv4
     */
    public BigInteger ipv4RawToBigInteger(InetAddress ipv4) {
	return new BigInteger(1, ipv4.getAddress());
    }

    /**
     * This method convert the given IPv4 (of InetAddress data-type), into binary
     * string (i.e. sequence of bits '010101101', etc.) .
     * 
     * Maximum range of values: as BigInteger = (2^32)*Integer.
     * 
     * @param ipv4 IPv4 in format x.y.z.t
     * @return Binary string of the given IPv4 InetAddress.
     * @throws UnknownHostException
     */
    public String ipv4RawToBinary(InetAddress ipv4) throws UnknownHostException {
	/* 2 means binary representation format */
	System.out.println(ipv4.getAddress().toString());

	return new BigInteger(1, ipv4.getAddress()).toString(2);
    }

    /**
     * This method convert the given IPv4 (of InetAddress data-type), into Long
     * value.
     * 
     * Maximum range of values: as Long = (2^64).
     * 
     * @param ipv4 IPv4 in format x.y.z.t
     * @return The long value of the of the given IPv4 InetAddress.
     * @throws UnknownHostException
     */
    public Long ipv4RawToLong(InetAddress ipv4) throws UnknownHostException {
	return BitUtils.binaryToLong(ipv4RawToBinary(ipv4));
    }

    /**
     * This method checks whether a given IPv4 belongs to the specified CIDR.
     * 
     * @param targetIp IPv4 in form x.y.z.t
     * @param cidr     The CIDR in form x.y.z.t/n, as string value.
     * @return True/ False whether IPv4 belongs to the CIDR or not, respectively.
     * @throws UnknownHostException
     */
    public boolean ipv4BelongsToCidr(InetAddress targetIp, String cidr) throws UnknownHostException {
	Long target = ipv4RawToLong(targetIp);
	Long first = getFirstCidrIPv4AddressLong(cidr);
	Long last = getLastCidrIPv4AddressLong(cidr);
	return first < target && target < last;
    }

    /**
     * This method gets as input a binary sequence and it updates the n-bits
     * starting from the end to the beginning, with the given bit value.
     * 
     * @param binary   The bit sequence in string format (e.g. "1010101101").
     * @param bitsNum  The number of the last N bits that should be updated
     * @param bitValue The bit value that will replace the 'n' last bits (valid
     *                 values 0 or 1)
     * @return
     */
    public String setLastNbitsOfBinary(String binary, int bitsNum, char bitValue) {
	/* i : total number of bit positions that should be updated */
	char[] updatedBitSequence = new char[bitsNum];
	/* array contains the same bit value (0/1) */
	Arrays.fill(updatedBitSequence, bitValue);
	/* replace the last N bits of the given binary, with the the new bits values */
	return binary.substring(0, binary.length() - bitsNum).concat(new String(updatedBitSequence));
    }

    public static void main(String[] args) throws UnknownHostException {
	IPv4Utils ip = new IPv4Utils();
	ip.ipv4RawToBinary(InetAddress.getByName("192.168.1.1"));

    }

}