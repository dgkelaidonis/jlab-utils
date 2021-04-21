package gr.dgk.utils.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtils {
    /**
     * JWT Creator. It return a String that constitutes the JWT generated token
     * 
     * @param id        a unique identifier for the current JWT string.
     * @param issuer    the creator of this JWT. It can be an email, a site.
     * @param subject   the username or any other identification
     * @param ttlMillis time to live in milliseconds
     * @param apiKey    This is the key that is used to encrypt the JWT string and
     *                  it should be common and shared among the client/server.
     * @return A string that comprise the JWT in form base64urlEncoding(header) +
     *         '.' + base64urlEncoding(payload) + '.' + base64urlEncoding(signature)
     */

    public String createJWT(String id, String issuer, String subject, String audience, long ttlMillis,
	    String secretEncryptionKey) {
	/* The JWT signature algorithm we will be using to sign the token */
	SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	long nowMillis = System.currentTimeMillis();
	Date now = new Date(nowMillis);
	/* Sign JWT with our application ApiKey secret */
	byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretEncryptionKey);
	Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	/* Let's set the JWT Claims */
	JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(now).setSubject(subject).setIssuer(issuer)
		.setAudience(audience).signWith(signatureAlgorithm, signingKey);
	/* if it has been specified, let's add the expiration */
	if (ttlMillis >= 0) {
	    long expMillis = nowMillis + ttlMillis;
	    Date exp = new Date(expMillis);
	    builder.setExpiration(exp);
	}
	/* Builds the JWT and serialises it to a compact, URL-safe string */
	return builder.compact();
    }

    /**
     * This method is used to decode a JWT string using the unique secret key that
     * was used to encode this.
     * 
     * @param jwt                 The string that constitutes the JWT.
     * @param secretEncryptionKey The secret encryption key, used to encode the JWT
     *                            string.
     * @return A Claim object the gives access to all standard properties that are
     *         included into a JWT.
     */

    public Claims parseJWT(String jwt, String secretEncryptionKey) {
	/* If it is not a signed JWT (as expected), it will throw an exception */
	return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretEncryptionKey)).parseClaimsJws(jwt)
		.getBody();
    }

    /**
     * 
     * @param username            The username of the user that requested the
     *                            password reset token.
     * @param email               The email of the user that requested the password
     *                            reset token.
     * @param timeToLiveInMsec    The total time in milliseconds that the password
     *                            reset token will be valid.
     * @param secretEncryptionKey The unique API or application's Licence KEY that
     *                            will be used as the unique encryption key for the
     *                            encoding/decoding of JWT.
     * @return A JWT-based token as string that will be sent by email to a user who
     *         requests the password reset action by an application.
     * @throws JsonProcessingException
     */
//	@Bean
//	public String generateJwtPasswordResetToken(String username, String email, long timeToLiveInMsec, String secretEncryptionKey)
//			throws JsonProcessingException {
//		/* return encrypted and encoded JWT string */
//		return createJWT(UUID.randomUUID().toString(), email, "anyvet_password_reset_token", username, timeToLiveInMsec,
//				secretEncryptionKey);
//	}

    /**
     * This method decodes a JWT-based password reset token and it returns a
     * UserDetails data transfer object in order to
     * 
     * @param theJwtString
     * @param secretEncryptionKey
     * @return
     */

    public void parseJwtPasswordResetToken(String theJwtString, String secretEncryptionKey) {
	/* decode the received JWT */
	Claims claims = parseJWT(theJwtString, secretEncryptionKey);
	/* build & return an application compatible DTO */
	UserDetailsDto user = new UserDetailsDto();
	user.setUsername(claims.getAudience());
	user.setEmail(claims.getIssuer());
	return user;
    }
}
