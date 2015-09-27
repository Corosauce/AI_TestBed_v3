package com.corosus.game;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "AI_TestBed";
		cfg.width = 960;
		cfg.height = 640;
		cfg.vSyncEnabled = false;
		//cfg.foregroundFPS = 0;
		new LwjglApplication(new Game_AI_TestBed(), cfg);
	}
}
