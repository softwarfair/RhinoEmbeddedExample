package com.ivanparraga.rhinoembeddedexample;

import java.util.Collections;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RhinoEcmaEvaluator {
	public static EcmaValue evaluate(String expression, SymbolTable table) {
		List<String> varsAtExpression = getVariablesAtExpression(expression);
		List<EcmaVariable> variables = getVariablesFromSymbolTable(varsAtExpression, table);
		return evaluateExpression(expression, variables);
	}

	private static List<String> getVariablesAtExpression(String expression) {
		return RhinoEcmaVariableFinder.getVariables(expression);
	}

	private static List<EcmaVariable> getVariablesFromSymbolTable(
			List<String> varNames, SymbolTable table) {
		if (!table.containsAll(varNames)) {
			throw new IllegalArgumentException("Some variable not found at symbol table");
		}

		return Collections.<EcmaVariable>emptyList();
	}

	private static EcmaValue evaluateExpression(String expression,
			List<EcmaVariable> variables) {

		try {
			Context context = ContextFactory.getGlobal().enterContext();
			Scriptable scope = getScope(context, variables);
			Object result = context.evaluateString(scope, expression, "", 1, null);
			return EcmaValue.create(result);
		} finally {
			Context.exit();
		}
	}

	private static Scriptable getScope(Context context, List<EcmaVariable> variables) {
		Scriptable scope = context.initStandardObjects();
		initVariables(scope, variables);
		return scope;
	}

	private static void initVariables(Scriptable scope, List<EcmaVariable> variables) {
		for (EcmaVariable variable : variables) {
			initVariable(scope, variable);
		}
	}

	private static void initVariable(Scriptable scope, EcmaVariable variable) {
		String varName = variable.getName();
		Object value = variable.getValue().getValue();

		Object wrappedValue = Context.javaToJS(value, scope);
		ScriptableObject.putProperty(scope, varName, wrappedValue);
	}
}
