package guwan21.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import guwan21.core.spring.SpringBeansManager;


public class Main {

	public static void main(String[] args) {
		Lwjgl3ApplicationConfiguration cfg = new Lwjgl3ApplicationConfiguration();
		cfg.setTitle("AsstPortoProj");
		int height = 1200;
		int width = (int) (height * (16/9f));

		cfg.setWindowSizeLimits(width, height, width, height);
		cfg.setWindowedMode(width, height);
		cfg.setResizable(false);

		new Lwjgl3Application(
				//Locate any present implementation of the ApplicationListener interface in this package.
				SpringBeansManager.getBean("guwan21.core.main",ApplicationListener.class),
				cfg
		);
	}

}
