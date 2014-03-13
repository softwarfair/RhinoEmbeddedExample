package com.ivanparraga.rhinoembeddedexample;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RhinoEcmaEvaluatorTest {
	@Test
	public void testEvaluateSimpleIntExpressionNoVariable() {
		int intValue = 3;
		String expression = "" + intValue;
		SymbolTable table = new SymbolTable();

		EcmaValue actualValue = RhinoEcmaEvaluator.evaluate(expression, table);

		assertEquals(intValue, actualValue.getValue());
	}

	@Test
	public void testEvaluateIntExpressionNoVariable() {
		String expression = "Math.pow(2,3)";
		SymbolTable table = new SymbolTable();

		EcmaValue actualValue = RhinoEcmaEvaluator.evaluate(expression, table);

		double expectedValue = Math.pow(2, 3);
		assertEquals(expectedValue, actualValue.getValue());
	}

	@Test
	public void testEvaluateSimpleStringExpressionNoVariable() {
		String strValue = "Hello world";
		String expression = "\"" + strValue + "\"";
		SymbolTable table = new SymbolTable();

		EcmaValue actualValue = RhinoEcmaEvaluator.evaluate(expression, table);

		assertEquals(strValue, actualValue.getValue());
	}

	@Test
	public void testEvaluateStringExpressionNoVariable() {
		String expression = "\"how many? \" + 10";
		SymbolTable table = new SymbolTable();

		EcmaValue actualValue = RhinoEcmaEvaluator.evaluate(expression, table);

		String expectedValue = "how many? 10";
		assertEquals(expectedValue, actualValue.getValue());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testEvaluateVariableNotInSymbolTable() {
		String expression = "i";
		SymbolTable table = new SymbolTable();

		RhinoEcmaEvaluator.evaluate(expression, table);
	}
}
