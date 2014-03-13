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
		initVariables(scope, variables);
		return scope;
	}

	private static EcmaValue handleException(EcmaError error, List<EcmaVariable> variables) {
		if (REFERENCE_ERROR.endsWith(error.getName())) {
			throw new IllegalArgumentException("I couldn't resolve some "
				+ "variable on expression with vars "
				+ Arrays.toString(variables.toArray()), error);
		}
		throw error;
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
