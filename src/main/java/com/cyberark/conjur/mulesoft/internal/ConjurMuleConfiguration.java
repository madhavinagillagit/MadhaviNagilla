package com.cyberark.conjur.mulesoft.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;

/**
 * 
 * Class will hold the conjur authentication parameters as global configuration.
 *
 */
@Operations(ConjurMuleOperations.class)
@ConnectionProviders(ConjurMuleConnectionProvider.class)
public class ConjurMuleConfiguration {
	private String configId;

	public String getConfigId() {
		return configId;
	}

}
