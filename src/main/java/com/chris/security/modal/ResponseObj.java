package com.chris.security.modal;

import java.io.Serializable;

public class ResponseObj implements Serializable {
	int statusCode;
	String message;
	Object data;

	public ResponseObj() {
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
