package com.cyberark.conjur.mulesoft.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.util.Arrays;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.extension.api.annotation.error.Throws;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.exception.ModuleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cyberark.conjur.constant.ConjurConstant;
import com.cyberark.conjur.error.ConjurErrorProvider;
import com.cyberark.conjur.error.ConjurErrorTypes;
import com.cyberark.conjur.sdk.ApiException;

/**
 * This class is a container for operations, every public method in this class
 * will be taken as an extension operation.
 */
@MediaType(value = ANY, strict = false)
@Throws(ConjurErrorProvider.class)
public class ConjurMuleOperations {
	private final Logger LOGGER = LoggerFactory.getLogger(ConjurMuleOperations.class);

	@SuppressWarnings("unlikely-arg-type")
	@MediaType(value = ANY, strict = false)
	public String retrieveSecret(@Config ConjurMuleConfiguration configuration,
			@Connection ConjurMuleConnection connection) {
		LOGGER.info("Inside Retrieve Secrets call");
		Object secretVal = null;
		try {

			secretVal = connection.getValue();

		} catch (Exception ex) {

			String errorCode= String.valueOf(connection.getErrorCode());
			String errorsMsg =connection.getErrorMsg();
			
			LOGGER.info("ERror size" + errorCode);
			LOGGER.info("ERror size" + errorsMsg);
			/*if (errorCode.equals(ConjurErrorTypes.CODE_401)) {
				throw new ModuleException(errorsMsg, ConjurErrorTypes.CODE_401);
				//return "Status Code:"+errorCode+"Message:"+errorsMsg;
			} else if (errorCode.equals(ConjurErrorTypes.CODE_403)) {
				//throw new ModuleException(errorsMsg,ConjurErrorTypes.CODE_403);
				return "Status Code:"+errorCode+"Message:"+errorsMsg;
			} else if (errorCode.equals(ConjurErrorTypes.CODE_404)) {
				//throw new ModuleException(errorsMsg,ConjurErrorTypes.CODE_404);
				return "Status Code:"+errorCode+"Message:"+errorsMsg;
			} else if (errorCode.equals(ConjurErrorTypes.CODE_422)) {
				throw new ModuleException(errorsMsg,ConjurErrorTypes.CODE_422);
			}*/
			return "Status Code:"+errorCode+"Message:"+errorsMsg;

			
		}
		return "Using  Connection id[" + connection.getId() + "secret" + secretVal.toString() + "]";
	}

}
