package com.cyberark.conjur.mulesoft.internal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.UnknownHostException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberark.conjur.constant.ConjurConstant;
import com.cyberark.conjur.core.ConjurConnection;
import com.cyberark.conjur.domain.ConjurConfiguration;
import com.cyberark.conjur.sdk.ApiClient;
import com.cyberark.conjur.sdk.ApiException;
import com.cyberark.conjur.sdk.endpoint.SecretsApi;

import com.cyberark.conjur.service.ConjurServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ConjurMuleConnectionProviderTest {

	@InjectMocks
	public ConjurMuleConnectionProvider connectionProvider;

	public SecretsApi secretsApi;

	public ApiClient apiClient;

	public ConjurServiceImpl conjurService;

	@InjectMocks
	public ConjurConfiguration conjurConfig;

	String key = System.getenv().getOrDefault("KEY_VARIABLES","");
 

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws UnknownHostException {
		java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
		mock(ConjurConfiguration.class);
		MockitoAnnotations.initMocks(this);
		apiClient = mock(ApiClient.class);
		conjurService = mock(ConjurServiceImpl.class);
		secretsApi = mock(SecretsApi.class);
		conjurConfig.setConjurAccount(System.getenv().getOrDefault("CONJUR_ACCOUNT", null));
		conjurConfig.setConjurApplianceUrl(System.getenv().getOrDefault("CONJUR_APPLIANCE_URL", null));
		conjurConfig.setConjurAuthnLogin(
				System.getenv().getOrDefault("CONJUR_AUTHN_LOGIN", null) + localMachine.getHostName());
		conjurConfig.setConjurApiKey(System.getenv().getOrDefault("CONJUR_AUTHN_API_KEY", null));
		conjurConfig.setConjurSslCertificate(System.getenv().getOrDefault("CONJUR_SSL_CERTIFICATE", null));
		conjurConfig.setConjurCertFile(System.getenv().getOrDefault("CONJUR_CERT_FILE", null));

	}

	@Test
	public void conjurConnection() throws ApiException {

		try (MockedStatic<ConjurConnection> getConnectionMockStatic = mockStatic(ConjurConnection.class)) {

			getConnectionMockStatic.when(() -> ConjurConnection.getConnection(conjurConfig)).thenReturn(apiClient);

			assertEquals(apiClient, ConjurConnection.getConnection(conjurConfig));
		}

	}

	@Test
	public void checkConjurAccount() {
		conjurConfig = mock(ConjurConfiguration.class);
		when(conjurConfig.getConjurAccount()).thenReturn("myConjurAccount");
		assertEquals("myConjurAccount", conjurConfig.getConjurAccount());

	}

	@Test
	public void getAccount() {

		when(secretsApi.getApiClient()).thenReturn(apiClient);

		when(apiClient.getAccount()).thenReturn("myConjurAccount");

		String result = ConjurConnection.getAccount(secretsApi);

		verify(secretsApi, times(1)).getApiClient();

		verify(apiClient, times(1)).getAccount();

		assertEquals("myConjurAccount", result);

	}

	@Test
	public void getSecretVal() throws ApiException {

		String[] keys = key.split(",");

		if (keys.length > 1) {

			try (MockedStatic<Object> getBatchSecretsMockStatic = mockStatic(Object.class)) {

				Object batchSecrets = conjurService.getBatchSecerts(key, conjurConfig.getConjurAccount());

				mock(ConjurServiceImpl.class);

				getBatchSecretsMockStatic
						.when(() -> conjurService.getBatchSecerts(key, conjurConfig.getConjurAccount()))
						.thenReturn(batchSecrets);
				assertEquals(conjurService.getBatchSecerts(key, conjurConfig.getConjurAccount()), batchSecrets);
			}

		} else {

			try (MockedStatic<Object> getSecretValMockStatic = mockStatic(Object.class)) {

				Object secretValue = conjurService.getSecret(conjurConfig.getConjurAccount(),
						ConjurConstant.CONJUR_KIND, key);

				mock(ConjurServiceImpl.class);

				getSecretValMockStatic.when(
						() -> conjurService.getSecret(conjurConfig.getConjurAccount(), ConjurConstant.CONJUR_KIND, key))
						.thenReturn(secretValue);

				assertEquals(conjurService.getSecret(conjurConfig.getConjurAccount(), ConjurConstant.CONJUR_KIND, key),
						secretValue);
			}

		}

	}

	@Test
	public void conjurDisconnect() {

		ConjurMuleConnection connection = mock(ConjurMuleConnection.class);

		connectionProvider.disconnect(connection);

		verify(connection, times(1)).invalidate();
	}

}