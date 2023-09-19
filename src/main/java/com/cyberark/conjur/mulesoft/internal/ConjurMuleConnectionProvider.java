package com.cyberark.conjur.mulesoft.internal;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.api.connection.CachedConnectionProvider;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberark.conjur.authentication.AuthenticationService;
import com.cyberark.conjur.authentication.AuthenticationServiceImpl;
import com.cyberark.conjur.core.ConjurConnection;
import com.cyberark.conjur.domain.ConjurConfiguration;
import com.cyberark.conjur.sdk.AccessToken;
import com.cyberark.conjur.sdk.ApiClient;
import com.cyberark.conjur.sdk.ApiException;
import com.cyberark.conjur.sdk.Configuration;
import com.cyberark.conjur.sdk.endpoint.AuthenticationApi;
import com.cyberark.conjur.sdk.endpoint.SecretsApi;
import com.cyberark.conjur.service.ConjurService;
import com.cyberark.conjur.service.ConjurServiceImpl;

/**
 * This class (as it's name implies) provides connection instances and the
 * funcionality to disconnect and validate those connections.
 * <p>
 * All connection related parameters (values required in order to create a
 * connection) must be declared in the connection providers.
 * <p>
 * This particular example is a {@link PoolingConnectionProvider} which declares
 * that connections resolved by this provider will be pooled and reused. There
 * are other implementations like {@link CachedConnectionProvider} which lazily
 * creates and caches connections or simply {@link ConnectionProvider} if you
 * want a new connection each time something requires one.
 */

public class ConjurMuleConnectionProvider implements PoolingConnectionProvider<ConjurMuleConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(ConjurMuleConnectionProvider.class);

	/**
	 * A parameter that is always required to be configured.
	 */
	@Parameter
	private String requiredParameter;
	@Parameter
	private String conjurAccount;
	@Parameter
	private String conjurApplianceUrl;
	@Parameter
	@Optional
	private String conjurAuthnLogin;
	@Parameter
	@Optional
	private String conjurApiKey;
	@Parameter
	@Optional
	private String conjurSslCertificate;
	@Parameter
	@Optional
	private String conjurCertFile;
	@Parameter
	@Optional
	private String conjurJwtTokenPath;
	@Parameter
	@Optional
	private String conjurAuthenticatorId;
	@Parameter
	private String key;

	/**
	 * A parameter that is not required to be configured by the user.
	 */
	@DisplayName("Friendly Name")
	@Parameter
	@Optional(defaultValue = "100")
	private int optionalParameter;

	@Override
	public ConjurMuleConnection connect() throws ConnectionException {

		//System.out.println("Calling Demo Connection Provider connect()");

		ConjurConfiguration config = new ConjurConfiguration();
		
		config.setConjurAccount(conjurAccount);
		config.setConjurApplianceUrl(conjurApplianceUrl);
		config.setConjurAuthnLogin(conjurAuthnLogin);
		config.setConjurApiKey(conjurApiKey);
		config.setConjurSslCertificate(conjurSslCertificate);
		config.setConjurCertFile(conjurCertFile);
		
		//ConjurConnection conjurConn = new ConjurConnection();
		
		String accessToken = ConjurConnection.getConnection(config);
		
		ConjurService conjurService = new ConjurServiceImpl();
		
		String[] keys = key.split(",");
		try {
			if(accessToken != null) {
				if(keys.length >1) {
		
			conjurService.getSecerts(key,conjurAccount);
			
				}
		else {
				conjurService.getSecret(conjurAccount, "variable",key);
		}
		}
		}
			catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

		return new ConjurMuleConnection(requiredParameter + ":" + optionalParameter + ":" + conjurAccount + ":"
				+ conjurApplianceUrl + ":" + conjurAuthnLogin + ":" + conjurApiKey);
	}

	@Override
	public void disconnect(ConjurMuleConnection connection) {
		try {
			connection.invalidate();
		} catch (Exception e) {
			LOGGER.error("Error while disconnecting [" + connection.getId() + "]: " + e.getMessage(), e);
		}
	}

	@Override
	public ConnectionValidationResult validate(ConjurMuleConnection connection) {
		return ConnectionValidationResult.success();
	}

	private AccessToken getJwtAccessToken(ApiClient conjurClient, String jwtTokenPath, String authenticatorId)
			throws IOException {
		AccessToken accessToken = null;
		AuthenticationApi apiInstance = new AuthenticationApi(conjurClient);
		String xRequestId = UUID.randomUUID().toString();
		String jwt = new String(Files.readAllBytes(Paths.get(jwtTokenPath)));
		try {
			String accessTokenStr = apiInstance.getAccessTokenViaJWT(conjurClient.getAccount(), authenticatorId,
					xRequestId, jwt);
			accessToken = AccessToken.fromEncodedToken(
					Base64.getEncoder().encodeToString(accessTokenStr.getBytes(StandardCharsets.UTF_8)));
		} catch (ApiException e) {
			System.out.println("Exception getting token" + e.getMessage());
		}
		return accessToken;
	}

}
