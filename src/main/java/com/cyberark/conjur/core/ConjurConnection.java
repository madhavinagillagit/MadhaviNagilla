package com.cyberark.conjur.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

import com.cyberark.conjur.domain.ConjurConfiguration;
import com.cyberark.conjur.sdk.AccessToken;
import com.cyberark.conjur.sdk.ApiClient;
import com.cyberark.conjur.sdk.ApiException;
import com.cyberark.conjur.sdk.Configuration;
import com.cyberark.conjur.sdk.endpoint.SecretsApi;

public class ConjurConnection {
	
	public static String getConnection(ConjurConfiguration config)
	{
		AccessToken accessToken = null;
		Object secrets = null;

		ApiClient client = Configuration.getDefaultApiClient();
		SecretsApi secretsApi = new SecretsApi();

		client.setAccount(config.getConjurAccount());
		client.setBasePath(config.getConjurApplianceUrl());
		client.setUsername(config.getConjurAuthnLogin());
		client.setApiKey(config.getConjurApiKey());

		InputStream sslInputStream = null;
		String sslCertificate = config.getConjurSslCertificate();
		// String certFile = conjurCertFile;
		String secretValue = "";
		String token = "";

		System.out.println("API Keya >>>>" + config.getConjurApiKey());

		try {
			if (StringUtils.isNotEmpty(config.getConjurSslCertificate())) {
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

			if (config.getConjurApiKey() != null) {
				System.out.println("API Key >>>>" + config.getConjurApiKey());
				accessToken = client.getNewAccessToken();
				token = accessToken.getHeaderValue();
				client.setAccessToken(token);
				Configuration.setDefaultApiClient(client);
			}
		}
		catch (IOException ex) {
			ex.getMessage();
		} 
			
		return token;
	}
	public static String getAccount() {
		return null;
	}

}
