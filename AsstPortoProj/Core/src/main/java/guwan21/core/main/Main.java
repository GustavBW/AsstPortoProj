package guwan21.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import guwan21.core.managers.IBootLoader;
import guwan21.core.managers.SpringBeansManager;


public class Main {

	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("AsstPortoProj");
		int height = 1200;
		int width = (int) (height * (16/9f));

		cfg.setWindowSizeLimits(width, height, width, height);
		cfg.setWindowedMode(width, height);
		cfg.setResizable(false);

		IBootLoader bootLoader = SpringBeansManager.getBean("guwan21.core", IBootLoader.class);
		if(bootLoader == null) {
			System.err.println("No Boot Loader found.");
		}else{
			bootLoader.run(args);
		}

		new Lwjgl3Application(
				//Locate any present implementation of the ApplicationListener interface in this package.
				SpringBeansManager.getBean("guwan21.core",ApplicationListener.class),
				cfg
		);
	}

}
