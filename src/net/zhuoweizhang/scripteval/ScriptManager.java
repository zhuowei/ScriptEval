package net.zhuoweizhang.scripteval;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import org.mozilla.javascript.*;
import org.mozilla.javascript.annotations.*;

public class ScriptManager {

	public static ConsoleListener listener;

	public static interface ConsoleListener {
		public void print(String s);
	}

	public static void runScript(String script) {
		try {
			Context ctx = Context.enter();
			ctx.setOptimizationLevel(-1);
			Scriptable scope = ctx.initStandardObjects(new SimpleHostObject());
			// there's gotta be an easier way. Taken from BlockLauncher.
			String[] names = getAllJsFunctions(SimpleHostObject.class);
			((ScriptableObject) scope).defineFunctionProperties(names, SimpleHostObject.class,
				ScriptableObject.DONTENUM);

			ctx.evaluateString(scope, script, "input.js", 1, null);
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			listener.print(sw.toString());
		} finally {
			Context.exit();
		}
	}

	private static String[] getAllJsFunctions(Class<? extends ScriptableObject> clazz) {
		List<String> allList = new ArrayList<String>();
		for (Method met : clazz.getMethods()) {
			if (met.getAnnotation(JSFunction.class) != null) {
				allList.add(met.getName());
			}
		}
		return allList.toArray(new String[0]);
	}

	private static class SimpleHostObject extends TopLevel {
		public SimpleHostObject() {
		}
		@JSFunction
		public void print(String s) {
			listener.print(s);
		}
		@Override
		public String getClassName() {
			return "SimpleHostObject";
		}
	}

}