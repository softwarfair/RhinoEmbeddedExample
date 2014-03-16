package com.ivanparraga.rhinoembeddedexample;

import java.util.Arrays;
import java.util.List;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class RhinoEcmaEvaluator {
	private static final String REFERENCE_ERROR = "ReferenceError";

	public static EcmaValue evaluate(String expression, SymbolTable table) {
		List<EcmaVariable> variables = table.getVariables();
		return evaluateExpression(expression, variables);
	}

	private static EcmaValue evaluateExpression(String expression,
			List<EcmaVariable> variables) {

		try {
			Context context = ContextFactory.getGlobal().enterContext();
			Scriptable scope = getScope(context, variables);
			Object result = context.evaluateString(scope, expression, "", 1, null);
			return EcmaValue.create(result);
		} catch(EcmaError error) {
			return handleException(error, variables);
		} finally {
			Context.exit();
		}
	}

	private static Scriptable getScope(Context context, List<EcmaVariable> variables) {
		Scriptable scope = context.initStandardObjects();
		putJavaVariablesIntoEcmaScope(scope, variables);
		return scope;
	}

	private static void putJavaVariablesIntoEcmaScope(Scriptable scope,
			List<EcmaVariable> variables) {

		for (EcmaVariable variable : variables) {
			putJavaVariableIntoEcmaScope(scope, variable);
		}
	}

	private static void putJavaVariableIntoEcmaScope(Scriptable scope,
			EcmaVariable variable) {

		String variableName = variable.getName();
		EcmaValue ecmaValue = variable.getValue();
		Object javaValue = ecmaValue.getValue();

		Object wrappedValue = Context.javaToJS(javaValue, scope);
		ScriptableObject.putProperty(scope, variableName, wrappedValue);
	}

	private static EcmaValue handleException(EcmaError error,
			List<EcmaVariable> variables) {

		if (REFERENCE_ERROR.equals(error.getName())) {
			throw new IllegalArgumentException("I couldn't resolve some "
				+ "variable on expression with vars "
				+ Arrays.toString(variables.toArray()), error);
		}
		throw error;
	}
}
