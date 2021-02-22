package gr.iotlabsgr.commons.security;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.List;

import gr.iotlabsgr.commons.networking.IPv4Utils;

public class AccessControlUtils {

    /**
     * This method is used for checking whether an IPv4 is allowed to access a
     * protected content, service, etc. In particular, if the CIDR is not set to
     * non-routable/meta-address IP range (0.0.0.0/0) or the given IP is not the
     * LocalHost (127.0.0.1), it is checked whether the given IP belongs to the the
     * given CIDR. The business logic inside this method compose the above
     * consideration so as to conclude whether a client is allowed to access the
     * wrapped content, inspecting its IPv4.
     * 
     * @param clientIp    IPv4 in format x.y.z.t
     * @param allowedCidr The CIDR that defines the allowed IPs for the wrapped
     *                    content.
     * @return boolean true/false for allowed and not allowed clients respectively.
     */
    public static boolean isAccessibleByIp(Inet4Address clientIp, String allowedCidr) {
	try {
	    /* if it is not 127.0.0.1 or CIDR is not set on non-routable / meta-address */
	    return IPv4Utils.isLocalhostAddress(clientIp.getHostAddress())
		    || IPv4Utils.isNonRoutableAddress(allowedCidr)
		    || IPv4Utils.ipv4BelongsToCidr(clientIp, allowedCidr);
	} catch (UnknownHostException e) {
	    e.printStackTrace();
	    return false;
	}
    }

    /**
     * This method can be used for wrapping inside a protected and access
     * controllable content/service/business logic, in a wider platform
     * architecture. It uses a traditional access control by API-KEY token and
     * client id (like username), that if and only if they belong to the Sets of the
     * authorized IDs and Keys, the client can access the protected content. The
     * feed Set<Sting>, can be done by database records, by CSV sting, by JSON
     * object and any other data type that can be formed into Set of unique, not
     * null, and not ordered elements.
     * 
     * @param authorizedClientIds Set of unique, not ordered, identifiers for
     *                            clients, as string data type.
     * @param authorizedApiKeys   Set of unique, not ordered, identifiers for
     *                            clients, as string data type.
     * @param clientId            The Identifier of a client that should be checked
     *                            with the available.
     * @param clientApiKey        The API-KEY of a client that should be checked by
     * @return
     */
    public static boolean isAuthorizedApiClient(List<String> authorizedClientIds, List<String> authorizedApiKeys,
	    String clientId, String clientApiKey) {
	/* since IP access is approved, check the client accessibility keys */
	return clientId != null && !clientId.isEmpty() && clientApiKey != null && !clientApiKey.isEmpty()
		&& authorizedClientIds.contains(clientId) && authorizedApiKeys.contains(clientApiKey);
    }

}
