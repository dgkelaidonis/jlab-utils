package gr.iotlabsgr.commons.security.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import gr.iotlabsgr.commons.security.AccessControlUtils;

public class AccessControlUtilsTests {

    private Inet4Address clientIp;
    private String allowedCidr;
    private String clientId;
    private String clientApiKey;
    private List<String> allowedClientIds;
    private List<String> allowedClientApiKey;

    @Before
    public void init() throws UnknownHostException {
	clientIp = (Inet4Address) Inet4Address.getByName("192.168.1.5");
	allowedCidr = "192.168.1.1/24";
	clientId = "unique-identifier-012345";
	clientApiKey = "very-long-token-in-sha256-sha-512-etc.";
	allowedClientIds = new ArrayList<String>();
	allowedClientIds.add(clientId);
	allowedClientApiKey = new ArrayList<String>();
	allowedClientApiKey.add(clientApiKey);
    }

    @Test
    public void test_isAuthorizedApiClient() {
	assertTrue(AccessControlUtils.isAuthorizedApiClient(allowedClientIds, allowedClientApiKey, clientId,
		clientApiKey));
    }

    @Test
    public void test_isAuthorizedApiClient_False() {
	assertFalse(AccessControlUtils.isAuthorizedApiClient(allowedClientIds, allowedClientApiKey, clientId,
		clientApiKey + "malformed"));
    }

    @Test
    public void test_isAccessibleByIp() {
	assertTrue(AccessControlUtils.isAccessibleByIp(clientIp, allowedCidr));
    }

}
