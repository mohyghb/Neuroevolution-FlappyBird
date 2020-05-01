package flappyBird.components;

import flappybird.MoConstants;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class MoConstantsTest {


    @Test
    public void keyBindingTest() {
        MoConstants.addKeyBinding(new JComponent(){},2,"kl",(e ->{}),false);
    }



}
