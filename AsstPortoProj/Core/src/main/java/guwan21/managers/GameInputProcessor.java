package guwan21.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import guwan21.common.data.GameData;
import guwan21.common.data.GameKeys;

public class GameInputProcessor extends InputAdapter {

    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

	public boolean keyDown(int k) {
		gameData.getKeys().setKey(k,true);
		return true;
	}
	
	public boolean keyUp(int k) {
		gameData.getKeys().setKey(k,false);
		return true;
	}
	
}








