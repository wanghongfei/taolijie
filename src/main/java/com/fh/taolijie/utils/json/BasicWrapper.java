package com.fh.taolijie.utils.json;

public class BasicWrapper extends Wrapper {
	protected String message;
	
	protected BasicWrapper() {
		
	}
	
	public BasicWrapper(boolean result) {
		this.result = result;
	}
	
	public BasicWrapper(boolean result, String message) {
		this.result = result;
		this.message = message;
	}


	public String getMessage() {
		return message;
	}
	
	
}
