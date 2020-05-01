package flappybird;

import java.awt.*;

public abstract class MoObject {


    private int positionX;
    private int positionY;
    private int width;
    private int height;
    private int velocityX;
    private int velocityY;
    private boolean isActive;



    public MoObject(int x, int y, int w, int h) {
        this.positionX = x;
        this.positionY = y;
        this.width = w;
        this.height = h;
        this.velocityX = 0;
        this.velocityY = 0;
        this.isActive = true;

    }


    public MoObject(int x, int y, int w, int h, int vx, int vy) {
        this.positionX = x;
        this.positionY = y;
        this.width = w;
        this.height = h;
        this.velocityX = vx;
        this.velocityY = vy;
        this.isActive = true;
    }



    public abstract void draw(Graphics2D g);



    // retruns true if this object is colliding with another
    // object
    public boolean hits(MoObject moObject) {
        if (moObject.isActive) {
            Rectangle rec1 = new Rectangle(this.positionX,this.positionY,this.width,this.height);
            Rectangle rec2 = new Rectangle(moObject.positionX,moObject.positionY,moObject.width,moObject.height);
            return rec1.intersects(rec2);
        }
        return false;
    }




    public void move() {
        this.moveX();
        this.moveY();
    }


    public void moveY() {
        this.positionY += this.isInVerticalBoundaries() ? this.velocityY : 0;
    }

    public void moveX() {
        this.positionX += this.isInHorizontalBoundaries() ? this.velocityX : 0;
    }


    public boolean isInVerticalBoundaries() {
        double nextYPosition = this.positionY + this.velocityY + (this.velocityY > 0 ? this.height : 0);
        if (nextYPosition <= 0 || nextYPosition >= MoConstants.GROUND_LEVEL_Y) {
            return false;
        }
        return true;
    }

    public boolean isInHorizontalBoundaries() {
        double nextYPosition = this.positionX + this.velocityX + (this.velocityX > 0 ? this.width : 0);
        if (nextYPosition <= 0 - this.width) {
            this.isActive = false;
            return false;
        }
        return true;
    }





    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getWidth() {
        return width;
    }

//    public void setWidth(int width) {
//        this.width = width;
//    }

    public int getHeight() {
        return height;
    }

//    public void setHeight(int height) {
//        this.height = height;
//    }


    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
