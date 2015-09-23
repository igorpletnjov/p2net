package ee.ttu.util;

public abstract class Log {
	
	// Niisama huvi jaoks, primitiivne logimine
	
	public static void info(String msg) {
		String log = "INFO: " + sun.reflect.Reflection.getCallerClass(2).getSimpleName() + " - " + msg;
		System.out.println(log);
	}
	
	public static void debug(String msg) {
		String log = "DEBUG: " + sun.reflect.Reflection.getCallerClass(2).getSimpleName() + " - " + msg;
		System.out.println(log);
	}
	
	public static void error(String msg) {
		String log = "ERROR: " + sun.reflect.Reflection.getCallerClass(2).getSimpleName() + " - " + msg;
		System.err.println(log);
	}
	
	public static void warn(String msg) {
		String log = "WARN: " + sun.reflect.Reflection.getCallerClass(2).getSimpleName() + " - " + msg;
		System.err.println(log);
	}

}
