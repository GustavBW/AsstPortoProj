package guwan21.core.managers;

public interface IBootLoader {

    /**
     * Pre-condition: Some services, registries locators or others should be cached pre-runtime
     * Post-condition: Pre-LWJGL-runtime initialization step complete
     */
    void run(String[] args);

}
