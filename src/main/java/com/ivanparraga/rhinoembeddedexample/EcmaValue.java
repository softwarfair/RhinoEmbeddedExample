package com.ivanparraga.rhinoembeddedexample;


public class EcmaValue {
	private String valueStr;
	private Number valueNumber;

	public static EcmaValue create(Object value) {
		if (value instanceof String) {
			return new EcmaValue((String) value);
		}

		if (value instanceof Number) {
			return new EcmaValue((Number) value);
		}

		throw new IllegalArgumentException("Unexpected object class");
	}

	private EcmaValue(String value) {
		valueStr = value;
	}

	private EcmaValue(Number value) {
		valueNumber = value;
	}

	public String getString() {
		if (valueStr == null) {
			throw new IllegalStateException("This value is not an String");
		}

		return valueStr;
	}

	public boolean isString() {
		return valueStr != null;
	}

	public Number getNumber() {
		if (valueNumber == null) {
			throw new IllegalStateException("This value is not an Number");
		}

		return valueNumber;
	}

	public boolean isNumber() {
		return valueNumber != null;
	}

	public Object getValue() {
		if (isNumber()) {
			return valueNumber;
		} else if (isString()) {
			return valueStr;
		}

		throw new IllegalStateException();
	}
}