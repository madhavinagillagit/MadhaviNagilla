package com.cyberark.conjur.mulesoft.internal;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;


/**
 * This class is a container for operations, every public method in this class will be taken as an extension operation.
 */
public class ConjurMuleOperations {

	/**
	   * Example of an operation that uses the configuration and a connection instance to perform some action.
	   */
	  @MediaType(value = ANY, strict = false)
	  public String retrieveInfo(@Config ConjurMuleConfiguration configuration, @Connection ConjurMuleConnection connection){
	    return "Using Configuration [" + configuration.getConfigId() + "] with Connection id [" + connection.getId() + "]";
	  }

	  @MediaType(value= ANY,strict= false)
	  public String retrieveAccessToken(@Config ConjurMuleConfiguration configuration, @Connection ConjurMuleConnection connection)
	  {
		  System.out.println("Calling Demo Operations>>");
		  return "Using Account["+configuration.getConjurAccount()+"] with Connection id[" +  connection.getId() + "]";
	  }
	  /**
	   * Example of a simple operation that receives a string parameter and returns a new string message that will be set on the payload.
	   */
	  @MediaType(value = ANY, strict = false)
	  public String sayHi(String person) {
	    return buildHelloMessage(person);
	  }

	  /**
	   * Private Methods are not exposed as operations
	   */
	  private String buildHelloMessage(String person) {
	    return "Hello " + person + "!!!";
	  }
}
