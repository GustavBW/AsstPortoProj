module Core {
    requires Common;
    requires java.desktop;
    requires com.badlogic.gdx;
    requires spring.context;

    exports guwan21.main to spring.beans;
    exports guwan21.components to spring.beans;
    exports guwan21.managers to spring.beans;
}