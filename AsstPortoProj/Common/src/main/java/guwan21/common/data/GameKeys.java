package guwan21.common.data;

public class GameKeys {

    private static boolean[] keys;
    private static boolean[] pkeys;

    //As found in the library: https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/Input.java#L68
    public static final int UP = 51; //W
    public static final int LEFT = 29; //A
    public static final int DOWN = 47; //S
    public static final int RIGHT = 32; //D
    public static final int ENTER = 66;
    public static final int ESCAPE = 111;
    public static final int SPACE = 62;
    public static final int SHIFT = 59;

    public GameKeys() {
        keys = new boolean[164]; //The highest keycode of LibGDX is 163
        pkeys = new boolean[164];

    }

    public void update() {
        System.arraycopy(keys, 0, pkeys, 0, keys.length);
    }

    public void setKey(int k, boolean b) {
        keys[k] = b;
    }

    public boolean isDown(int k) {
        return keys[k];
    }

    public boolean isPressed(int k) {
        return keys[k] && !pkeys[k];
    }

}
