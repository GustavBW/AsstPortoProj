package guwan21.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import guwan21.common.data.Color;
import guwan21.common.services.IPostEntityProcessingService;
import guwan21.common.util.SPILocator;
import guwan21.components.IProcessor;
import guwan21.components.PluginInjection;
import guwan21.managers.GameInputProcessor;
import guwan21.common.data.Entity;
import guwan21.common.data.GameData;
import guwan21.common.data.World;
import guwan21.common.services.IEntityProcessingService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component("GameInstance")
public class Game
        implements ApplicationListener {

    private AnnotationConfigApplicationContext components;

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData data = new GameData();
    private World world = new World();

    public Game() {
        this.components = new AnnotationConfigApplicationContext();
        this.components.scan("guwan21.components");
        this.components.refresh();
    }

    @Override
    public void create() {
        sr = new ShapeRenderer();

        if (
                data.getDisplayWidth() != Gdx.graphics.getWidth()
                || data.getDisplayHeight() != Gdx.graphics.getHeight()
        ) {
            this.updateCam(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        Gdx.input.setInputProcessor(
            new GameInputProcessor(data)
        );

        ((PluginInjection) components.getBean("MainInjector")).startPlugins(data, world);
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        data.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        data.getKeys().update();
    }

    private void updateCam(int width, int height) {
        data.setDisplayWidth(width);
        data.setDisplayHeight(height);

        cam = new OrthographicCamera(data.getDisplayWidth(), data.getDisplayHeight());
        cam.translate((float) data.getDisplayWidth() / 2, (float) data.getDisplayHeight() / 2);
        cam.update();
    }

    private void update() {
        ((IProcessor) components.getBean("SubProcessInjector")).process(data, world);
        ((IProcessor) components.getBean("PostProcessInjector")).process(data, world);
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {

            Color color = entity.getColor();
            sr.setColor(color.getR(), color.getG(), color.getB(), color.getA());

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
        this.updateCam(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }
}
