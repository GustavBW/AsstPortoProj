module Core {
    requires Common;

    requires java.desktop;
    requires com.badlogic.gdx;
    requires spring.context;

    exports guwan21.core.main to spring.beans;
    exports guwan21.core.components to spring.beans;
    exports guwan21.core.managers to spring.beans;
}