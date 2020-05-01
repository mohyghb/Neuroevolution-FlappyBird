package flappyBird.ui;

import ui.GameScreen;
import ui.MoGameType;
import ui.UI;
import org.junit.jupiter.api.Test;

public class GameScreenTest {

    @Test
    public void test() {
        GameScreen gameScreen = new GameScreen(MoGameType.AI);
        new UI();
        gameScreen.onSave();
        gameScreen.onLoad();
    }
}
