package com.cyberark.conjur.mulesoft.internal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ConjurMuleOperationsTest {

	public ConjurMuleConfiguration configuration;

	public ConjurMuleConnection connection;

	String conjurAccount = System.getenv().getOrDefault("CONJUR_ACCOUNT", null);

	@Test
	public void callRetrieveSecret() throws Exception {

		try (MockedStatic<ConjurMuleOperations> callRetrieveMockedStatic = mockStatic(ConjurMuleOperations.class)) {
			mock(ConjurMuleConnection.class);
			mock(ConjurMuleConfiguration.class);
			ConjurMuleOperations conjurMuleOperations = mock(ConjurMuleOperations.class);
			callRetrieveMockedStatic.when(() -> conjurMuleOperations.retrieveSecret(configuration, connection))
					.thenReturn("Using Account[" + conjurAccount + "] with Connection id[" + "1" + "]");

			assertEquals("Using Account[" + conjurAccount + "] with Connection id[" + "1" + "]",
					conjurMuleOperations.retrieveSecret(configuration, connection));
		}

	}
}