package flappybird;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MoConstants {

    public static int WINDOW_WIDTH = 1800;
    public static int WINDOW_HEIGHT = 800;
//    public static final double WIDTH_CORRECTION = 17;
//    public static final double HEIGHT_CORRECTION = 155;
//
//    public static double BOTTOM_SCREEN = WINDOW_HEIGHT - HEIGHT_CORRECTION;
//    public static double TOP_SCREEN = 0;
//    public static double LEFT_SCREEN = 0;
//    public static double RIGHT_SCREEN = WINDOW_WIDTH - WIDTH_CORRECTION;

    public static final int MID_POINT_Y = WINDOW_HEIGHT / 2;
    public static final int MID_POINT_X = WINDOW_WIDTH / 2;

    public static final int FPS = 14;

    public static final int SPEED_Y_BIRD_JUMP = -4;
    public static final int SPEED_Y_BIRD_FALL = 4;
    public static final int GRAVITY = 1;


    public static final int HEIGHT_GRASS = 70;
    public static final int GROUND_LEVEL_Y = WINDOW_HEIGHT - (5 * HEIGHT_GRASS / 3);




    public static void addKeyBinding(JComponent comp, int keycode, String id, ActionListener lam, boolean key) {
        InputMap im = comp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = comp.getActionMap();

        im.put(KeyStroke.getKeyStroke(keycode,0,key), id);
        am.put(id, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                lam.actionPerformed(e);
            }
        });
    }




}
