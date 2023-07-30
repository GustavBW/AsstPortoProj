package guwan21.common.bootloader;

/**
 * Allows registry or SPI services to pre-compute values
 */
public interface Initializable {

    /**
     * Pre-condition: The Program has just started and some registries or services should like a pre-compute step before LWJGL runtime
     * Post-condition: Init step done
     */
    void init();

}
