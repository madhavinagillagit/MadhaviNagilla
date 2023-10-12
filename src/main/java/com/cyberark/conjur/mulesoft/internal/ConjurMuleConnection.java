package com.cyberark.conjur.mulesoft.internal;

/**
 * This class represents an extension connection just as example (there is no
 * real connection with anything here c:).
 */
public final class ConjurMuleConnection {

	private final String id;

	private final Object value;

	private int errorCode;
	private String errorMsg;

	public ConjurMuleConnection(String id, Object value, int errorCode, String errorMsg) {
		this.id = id;
		this.value = value;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getId() {
		return id;
	}

	public Object getValue() {
		return value;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void invalidate() {

	}
}
