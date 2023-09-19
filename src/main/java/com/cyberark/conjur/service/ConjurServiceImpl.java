package com.cyberark.conjur.service;

import com.cyberark.conjur.sdk.ApiException;
import com.cyberark.conjur.sdk.endpoint.SecretsApi;

public class ConjurServiceImpl implements ConjurService{
	
	SecretsApi secretsApi = new SecretsApi();
	Object secrets = null;
	
	@Override
	public String getSecret(String variableId, String account, String variable_const) throws ApiException {
		
		
		String secrets = secretsApi.getSecret(account, "variable", variableId);
		return secrets;
	}

	@Override
	public Object getSecerts(String variableIds, String account) throws ApiException {
		
		String[] keys = variableIds.split(",");
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
		
		secrets = secretsApi.getSecrets(encodeKey.toString());
		
		return secrets;
	}

}
