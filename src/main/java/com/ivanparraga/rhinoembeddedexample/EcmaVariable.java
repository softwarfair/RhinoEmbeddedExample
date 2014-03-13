package com.ivanparraga.rhinoembeddedexample;

public class EcmaVariable {
	private final String name;
	private final EcmaValue value;

	public EcmaVariable(String name, EcmaValue value) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public EcmaValue getValue() {
		return value;
	}
}
