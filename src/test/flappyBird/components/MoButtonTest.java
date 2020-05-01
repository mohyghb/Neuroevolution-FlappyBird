package flappyBird.components;

import flappybird.MoButton;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class MoButtonTest {

    @Test
    public void constructorTest() {
        MoButton button = new MoButton("okay",2,2,2,2, Color.black,Color.white);
    }

}
