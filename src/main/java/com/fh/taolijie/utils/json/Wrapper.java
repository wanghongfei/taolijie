package com.fh.taolijie.utils.json;

public abstract class Wrapper {
	protected boolean result;

	protected String jsonString;


	public boolean isSucceeded() {
		return this.result;
	}
}
