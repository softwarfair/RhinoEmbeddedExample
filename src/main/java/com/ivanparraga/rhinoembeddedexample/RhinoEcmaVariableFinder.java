package com.ivanparraga.rhinoembeddedexample;

import java.util.LinkedList;
import java.util.List;

import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.NodeVisitor;

class RhinoEcmaVariableFinder {
	public static List<String> getVariables(String script) {
		AstNode node = new Parser().parse(script, "script", 1);
		Visitor visitor = new Visitor();
		node.visit(visitor);

		return visitor.names;
	}

	private static class Visitor implements NodeVisitor {
		List<String> names = new LinkedList<>();

		@Override
		public boolean visit(AstNode node) {
			if (node instanceof Name) {
				names.add(node.getString());
			}
			return true;
		}
	}
}