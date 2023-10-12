package com.cyberark.conjur.error;

import java.util.HashSet;
import java.util.Set;

import org.mule.runtime.extension.api.annotation.error.ErrorTypeProvider;
import org.mule.runtime.extension.api.error.ErrorTypeDefinition;

/** 
 * Provides set of errors to handled for this Custom Connector when integrated with the flow
 * @
 *
 */
public class ConjurErrorProvider implements ErrorTypeProvider{

	@SuppressWarnings("rawtypes")
	@Override
	public Set<ErrorTypeDefinition> getErrorTypes() {
		// TODO Auto-generated method stub
		HashSet<ErrorTypeDefinition> errors = new HashSet<ErrorTypeDefinition>();
		errors.add(ConjurErrorTypes.CODE_200);
		errors.add(ConjurErrorTypes.CODE_401);
		errors.add(ConjurErrorTypes.CODE_403);
		errors.add(ConjurErrorTypes.CODE_404);
		errors.add(ConjurErrorTypes.CODE_422	);
		return errors;
	}

}
