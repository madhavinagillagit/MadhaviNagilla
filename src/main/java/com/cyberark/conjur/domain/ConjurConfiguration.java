package com.cyberark.conjur.domain;


import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.Parameter;

import com.cyberark.conjur.mulesoft.internal.ConjurMuleOperations;


public class ConjurConfiguration {

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
	private String conjurAuthnLogin;
	@Parameter
	private String conjurApiKey;
	@Parameter
	private String conjurSslCertificate;
	@Parameter
	private String conjurCertFile;
	

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

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public void setConjurAccount(String conjurAccount) {
		this.conjurAccount = conjurAccount;
	}

	public void setConjurApplianceUrl(String conjurApplianceUrl) {
		this.conjurApplianceUrl = conjurApplianceUrl;
	}

	public void setConjurAuthnLogin(String conjurAuthnLogin) {
		this.conjurAuthnLogin = conjurAuthnLogin;
	}

	public void setConjurApiKey(String conjurApiKey) {
		this.conjurApiKey = conjurApiKey;
	}

	public void setConjurSslCertificate(String conjurSslCertificate) {
		this.conjurSslCertificate = conjurSslCertificate;
	}

	public void setConjurCertFile(String conjurCertFile) {
		this.conjurCertFile = conjurCertFile;
	}


}

