package com.cyberark.conjur.mulesoft.internal;

import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Optional;
import org.mule.runtime.extension.api.annotation.param.Parameter;

/**
 * This class represents an extension configuration, values set in this class are commonly used across multiple
 * operations since they represent something core from the extension.
 */
@Operations(ConjurMuleOperations.class)
@ConnectionProviders(ConjurMuleConnectionProvider.class)
public class ConjurMuleConfiguration {
	@Parameter
	private String configId;

	public String getConfigId() {
		return configId;
	}

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
	
	

	public String getConjurAccount() {
		return conjurAccount;
	}

	public String getConjurApplianceUrl() {
		return conjurApplianceUrl;
	}

	public String getConjurAuthnLogin() {
		return conjurAuthnLogin;
	}

	public String getConjurApiKey() {
		return conjurApiKey;
	}

	public String getConjurSslCertificate() {
		return conjurSslCertificate;
	}

	public String getConjurCertFile() {
		return conjurCertFile;
	}

	public String getConjurJwtTokenPath() {
		return conjurJwtTokenPath;
	}

	public String getConjurAuthenticatorId() {
		return conjurAuthenticatorId;
	}
	
	

}
