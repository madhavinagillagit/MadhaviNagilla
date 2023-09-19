package com.cyberark.conjur.service;

import com.cyberark.conjur.sdk.ApiException;

public interface ConjurService {
	
	String getSecret(String variableId,String account,String variable_const) throws ApiException;
	Object getSecerts(String variableIds,String account) throws ApiException;

}
