package com.cyberark.conjur.authentication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberark.conjur.mulesoft.internal.ConjurMuleConnection;
import com.cyberark.conjur.mulesoft.internal.ConjurMuleConnectionProvider;
import com.cyberark.conjur.sdk.AccessToken;
import com.cyberark.conjur.sdk.ApiClient;
import com.cyberark.conjur.sdk.ApiException;
import com.cyberark.conjur.sdk.Configuration;
import com.cyberark.conjur.sdk.endpoint.SecretsApi;

public class AuthenticationServiceImpl implements AuthenticationService {

	private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);


	
	@Override
	public AccessToken apiKeyAuthentication() {


		System.out.println("Calling Demo Connection Provider connect()");

		AccessToken accessToken = null;
		Object secrets = null;

		ApiClient client = Configuration.getDefaultApiClient();
		SecretsApi secretsApi = new SecretsApi();

		client.setAccount(conjurAccount);
		client.setBasePath(conjurApplianceUrl);
		client.setUsername(conjurAuthnLogin);
		client.setApiKey(conjurApiKey);

		InputStream sslInputStream = null;
		String sslCertificate = conjurSslCertificate;
		// String certFile = conjurCertFile;
		String secretValue = "";

		System.out.println("API Key >>>>" + conjurApiKey);

		try {
			if (StringUtils.isNotEmpty(conjurSslCertificate)) {
				System.out.println("SSL Certificate >>>>" + sslCertificate);
				sslInputStream = new FileInputStream(sslCertificate);
			} /*
				 * else { if (StringUtils.isNotEmpty(certFile)) sslInputStream = new
				 * FileInputStream(certFile); }
				 */

			if (sslInputStream != null) {
				client.setSslCaCert(sslInputStream);
				sslInputStream.close();
			}
			System.out.println("SSL Certificate data >>>>" + sslInputStream.toString());

			if (conjurApiKey != null) {
				System.out.println("API Key >>>>" + conjurApiKey);
				accessToken = client.getNewAccessToken();
				String token = accessToken.getHeaderValue();
				client.setAccessToken(token);
				Configuration.setDefaultApiClient(client);
			}

			client = secretsApi.getApiClient();
			String account = client.getAccount();

			System.out.println("Client after setting token" + accessToken);

			String[] keys = key.split(",");
			StringBuilder encodeKey = new StringBuilder();

			for (String k : keys) {
				if (encodeKey.length() == 0) {

					encodeKey.append(account + ":");
					encodeKey.append("variable:");
					// encodeKey.append(URLEncoder.encode(k, StandardCharsets.UTF_8.toString()));
					encodeKey.append(k);

				} else {
					encodeKey.append(",");
					encodeKey.append(account + ":");
					encodeKey.append("variable:");
					// encodeKey.append(URLEncoder.encode(k, StandardCharsets.UTF_8.toString()));
					encodeKey.append(k);
				}
			}

			// System.out.println("key to fetch" + keys.length);

			if (keys.length > 1) {
				System.out.println("key to fetch bulk secrets" + encodeKey);
				secrets = secretsApi.getSecrets(encodeKey.toString());
				System.out.println("key to fetch bulk secrets" + secrets);

			} else {
				System.out.println("key to fetch single secrets" + key);
				secretValue = secretsApi.getSecret(account, "variable", key);
				System.out.println("Secrets retrieved from conjur " + secretValue);

			}

		} catch (IOException ex) {
			ex.getMessage();
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			System.out.println(
					"Code >>" + e.getCode() + "Message >>" + e.getMessage() + "response>>" + e.getResponseBody());
		}

		return accessToken;
	}

	@Override
	public AccessToken jwtAuthentication() {
		// TODO Auto-generated method stub
		return null;
	}

}
