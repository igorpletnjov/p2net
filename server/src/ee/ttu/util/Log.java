package ee.ttu.util;

public abstract class Log {
	
	// Niisama huvi jaoks, primitiivne logimine
	
	public static void info(String msg) {
		String log = "[ " + Thread.currentThread().getName() + " ] " + "INFO: " + sun.reflect.Reflection.getCallerClass(2).getSimpleName() + " - " + msg;
		System.out.println(log);
	}
	
	public static void debug(String msg) {
		String log = "[ " + Thread.currentThread().getName() + " ] " + "DEBUG: " + sun.reflect.Reflection.getCallerClass(2).getSimpleName() + " - " + msg;
		System.out.println(log);
	}
	
	public static void error(String msg) {
		String log = "[ " + Thread.currentThread().getName() + " ] " + "ERROR: " + sun.reflect.Reflection.getCallerClass(2).getSimpleName() + " - " + msg;
		System.err.println(log);
	}
	
	public static void warn(String msg) {
		String log = "[ " + Thread.currentThread().getName() + " ] " + "WARN: " + sun.reflect.Reflection.getCallerClass(2).getSimpleName() + " - " + msg;
		System.err.println(log);
	}

}
