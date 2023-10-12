package com.cyberark.conjur.error;

import org.mule.runtime.extension.api.error.ErrorTypeDefinition;
/**
 * Defines the error type for the Conjur authentication and secret retrieval
 * 
 *
 */
public enum ConjurErrorTypes implements ErrorTypeDefinition<ConjurErrorTypes> {
	CODE_200, CODE_401, CODE_403, CODE_404, CODE_422

}
