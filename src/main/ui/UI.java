package ui;

import flappybird.MoConstants;

import javax.swing.*;

public class UI extends JFrame {


    public UI() {
        super.setSize(MoConstants.WINDOW_WIDTH,MoConstants.WINDOW_HEIGHT);
        super.add(new GameScreen(MoGameType.AI));
        super.setVisible(true);
        super.setTitle("Neuroevolution (Moh Yaghoub)");
        super.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }






}
