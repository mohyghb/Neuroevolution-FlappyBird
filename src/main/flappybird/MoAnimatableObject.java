package flappybird;

import java.awt.*;

public class MoAnimatableObject extends MoObject implements MovementInterface {



    private boolean isInAnimation;
    private long beginOfAnim;
    private double animTime;

    public MoAnimatableObject(int x, int y, int w, int h, double at) {
        super(x, y, w, h);
        this.isInAnimation = false;
        super.setVelocityY(0);
        super.setVelocityX(0);
        this.animTime = at;
    }

    public MoAnimatableObject(int x, int y, int w, int h, int xv, int yv, double at) {
        super(x, y, w, h,xv,yv);
        this.isInAnimation = false;
        this.animTime = at;
    }



    @Override
    public void moveX(int amount) {
        super.setVelocityX(amount);
        super.moveX();
    }

    @Override
    public void moveY(int amount) {
        super.setVelocityY(amount);
        super.moveY();
    }

    @Override
    public void draw(Graphics2D g) {

    }

    public float[][] getCorners(){
        float[][] array = new float[4][2];
       // g.fillRect(safeGap.getPositionX(),safeGap.getPositionY(),scale,scale);
//        g.fillRect(safeGap.getPositionX()  +safeGap.getWidth(),safeGap.getPositionY(),scale,scale);
//        g.fillRect(safeGap.getPositionX(),safeGap.getPositionY() + safeGap.getHeight(),scale,scale);
//        g.fillRect(safeGap.getPositionX()  + safeGap.getWidth(),safeGap.getPositionY()  +safeGap.getHeight(),scale,scale);
        array[0][0] = getPositionX();
        array[0][1] = getPositionY();

        array[1][0] = getPositionX() +  getWidth();
        array[1][1] = getPositionY();

        array[2][0] = getPositionX();
        array[2][1] = getPositionY() + getHeight();

        array[3][0] = getPositionX() + getWidth();
        array[3][1] = getPositionY() + getHeight();



        return array;
    }



    public boolean isInAnimation() {
        return isInAnimation;
    }

    public void startAnimation() {
        this.isInAnimation = true;
        this.beginOfAnim = System.currentTimeMillis();
    }

    public void check() {
        if (System.currentTimeMillis() - this.beginOfAnim >= this.animTime) {
            stopAnimation();
        }
    }

    private void stopAnimation() {
        this.isInAnimation = false;
    }


}
