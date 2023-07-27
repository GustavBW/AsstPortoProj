package guwan21.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import guwan21.managers.SpringBeansManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.awt.*;


public class Main {

	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("AsstPortoProj");
		int width = 1800;
		int height = 1200;
		cfg.setWindowSizeLimits(width, height, width, height);
		cfg.setWindowedMode(width, height);
		cfg.setResizable(false);
		cfg.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());

		ApplicationListener game = SpringBeansManager.getBean(Game.class);
		new Lwjgl3Application(game, cfg);
	}

}
