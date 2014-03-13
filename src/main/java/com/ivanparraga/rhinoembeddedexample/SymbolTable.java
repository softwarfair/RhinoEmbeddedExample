package com.ivanparraga.rhinoembeddedexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SymbolTable {
	private final Map<String, EcmaValue> symbols;

	public SymbolTable() {
		symbols = new HashMap<String, EcmaValue>();
	}

	public EcmaValue getSymbol(String name) {
		return symbols.get(name);
	}

	public void putSymbol(String name, EcmaValue value) {
		symbols.put(name, value);
	}

	public List<EcmaVariable> getVariables() {
		List<EcmaVariable> variables = new ArrayList<>(symbols.size());

		for (Entry<String, EcmaValue> entry : symbols.entrySet()) {
			String varName = entry.getKey();
			EcmaValue varValue = entry.getValue();
			EcmaVariable variable = new EcmaVariable(varName, varValue);
			variables.add(variable);
		}

		return variables;
	}
}
