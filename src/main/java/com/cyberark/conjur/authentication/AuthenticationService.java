package com.cyberark.conjur.authentication;

import com.cyberark.conjur.sdk.AccessToken;

public interface AuthenticationService {
	
	public AccessToken apiKeyAuthentication();
	public AccessToken jwtAuthentication();

}
