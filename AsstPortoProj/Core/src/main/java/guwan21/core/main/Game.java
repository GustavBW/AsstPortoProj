package guwan21.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import guwan21.common.data.*;
import guwan21.common.data.entities.Entity;
import guwan21.common.factories.ITimeBasedEntityFactory;
import guwan21.common.services.IEntityPostProcessingService;
import guwan21.common.services.IEntityPreProcessingService;
import guwan21.common.services.IEntityProcessingService;
import guwan21.common.util.SPILocator;
import guwan21.core.components.IEntityPostProcessingServicesRunner;
import guwan21.core.components.IEntityPreProcessingServicesRunner;
import guwan21.core.components.IEntityProcessingServicesRunner;
import guwan21.core.components.ITimeBasedFactoriesProcessingService;
import guwan21.core.managers.IPluginManagementService;
import guwan21.core.managers.GameInputProcessor;
import guwan21.core.spring.SpringBeansManager;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Game implements ApplicationListener {

    private final LinkedHashMap<Class<?>, SpringBeansManager.VoidFunction<?>> onUpdateRun = new LinkedHashMap<>();
    private final AnnotationConfigApplicationContext cachedOnUpdateContext = SpringBeansManager.getContextFor("guwan21.core.components");

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData data = new GameData();
    private final World world = new World();

    public Game() {}

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
        System.out.println("[GAME] Locating implementations of IEntityPreProcessingService, found: ...");
        for(IEntityPreProcessingService proc : SPILocator.getBeans(IEntityPreProcessingService.class)){
            System.out.println("   |- "+proc.getClass());
        }
        System.out.println("[GAME] Locating implementations of IEntityProcessingService, found: ...");
        for(IEntityProcessingService proc : SPILocator.getBeans(IEntityProcessingService.class)){
            System.out.println("   |- "+proc.getClass());
        }
        System.out.println("[GAME] Locating implementations of IEntityPostProcessingService, found: ...");
        for(IEntityPostProcessingService proc : SPILocator.getBeans(IEntityPostProcessingService.class)){
            System.out.println("   |- "+proc.getClass());
        }
        System.out.println("[GAME] Locating implementations of ITimeBasedEntityFactory, found ...");
        for(ITimeBasedEntityFactory factory : SPILocator.getBeans(ITimeBasedEntityFactory.class)){
            System.out.println("   |- "+factory.getClass());
        }
        System.out.println();

        SpringBeansManager.forAnyOf(IPluginManagementService.class,
                loader -> {
                    System.out.println("[GAME] Initializing plugins using: " + loader.getClass());
                    loader.startPlugins(data, world);
            }
        );
        //Executed in order of declaration, see SpringBeansManager.forAnyOfEither
        onUpdateRun.put(
                IEntityPreProcessingServicesRunner.class,
                (SpringBeansManager.VoidFunction<IEntityPreProcessingServicesRunner>) r -> r.process(data,world)
        );
        onUpdateRun.put(
                IEntityProcessingServicesRunner.class,
                (SpringBeansManager.VoidFunction<IEntityProcessingServicesRunner>) r -> r.process(data,world)
        );
        onUpdateRun.put(
                IEntityPostProcessingServicesRunner.class,
                (SpringBeansManager.VoidFunction<IEntityPostProcessingServicesRunner>) r -> r.process(data,world)
        );
        onUpdateRun.put(
                ITimeBasedFactoriesProcessingService.class,
                (SpringBeansManager.VoidFunction<ITimeBasedFactoriesProcessingService>) r -> r.process(data,world)
        );
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update();

        draw();
    }

    private void updateCam(int width, int height) {
        data.setDisplayWidth(width);
        data.setDisplayHeight(height);

        cam = new OrthographicCamera(data.getDisplayWidth(), data.getDisplayHeight());
        cam.translate((float) data.getDisplayWidth() * 0.5f, (float) data.getDisplayHeight() * 0.5f);
        cam.update();
    }

    private void update() {
        final float delta = Gdx.graphics.getDeltaTime();
        data.setDelta(delta);
        data.setMsFromGameStart(data.getMsFromGameStart() + delta);
        data.getKeys().update();

        SpringBeansManager.forAnyOfEither(cachedOnUpdateContext, onUpdateRun);

        //Instant exit
        if(data.getKeys().isDown(GameKeys.ESCAPE)) dispose();
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
        System.out.println("[GAME] exiting...");
        SpringBeansManager.forAnyOf(IPluginManagementService.class, loader ->
        {
            System.out.println("[GAME] Disposing plugins using: " + loader.getClass());
            loader.stopPlugins(data, world);
        });
        Gdx.app.exit();
    }

}
