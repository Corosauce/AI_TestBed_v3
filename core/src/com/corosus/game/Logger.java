package com.corosus.game;

public class Logger {
	
	public static void dbg(Object obj) {
		System.out.println("dbg: " + obj);
	}

	public static void info(Object obj) {
		System.out.println("info: " + obj);
	}
	
	public static void warn(Object obj) {
		System.out.println("warn: " + obj);
	}
	
	public static void err(Object obj) {
		System.out.println("err: " + obj);
	}
	
}
