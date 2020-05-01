package flappybird;

import javax.swing.*;
import java.awt.*;

public class MoButton extends JButton {




    public MoButton(String text, int x, int y, int width, int height, Color backgroundColor,Color foregroundColor) {
        super(text);
        super.setBounds(x,y,width,height);
        super.setFocusPainted(false);
        super.setBorderPainted(false);
        super.setBackground(backgroundColor);
        super.setForeground(foregroundColor);
        //super.setContentAreaFilled(false);

    }





}
