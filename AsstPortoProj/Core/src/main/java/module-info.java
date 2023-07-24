import guwan21.common.services.IEntityProcessingService;
import guwan21.common.services.IGamePluginService;
import guwan21.common.services.IEntityPostProcessingService;

module Core {
    requires Common;
    requires java.desktop;
    requires com.badlogic.gdx;
    requires spring.context;

    uses IGamePluginService;
    uses IEntityProcessingService;
    uses IEntityPostProcessingService;

    exports guwan21.components;
    exports guwan21.main;
}