package flappyBird.components;

import flappybird.MoBird;
import flappybird.MoObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoObjectTest {



    MoObject object;

    @BeforeEach
    public void init() {
        this.object = new MoBird(2,20,10,22);
        this.object = new MoBird(2,20,10,22,21,11);
    }


    @Test
    public void moveAxisTest() {
        this.object.moveY();
        assertEquals(31,this.object.getPositionY());

        this.object.moveX();
        assertEquals(23,this.object.getPositionX());
    }

    @Test
    public void isInBoundsTest() {
        assertTrue(this.object.isInHorizontalBoundaries());
        assertTrue(this.object.isInVerticalBoundaries());
        this.object.setPositionY(10002);
        assertFalse(this.object.isInVerticalBoundaries());
        this.object.setPositionX(-20002);
        this.object.setPositionY(-10002);
        assertFalse(this.object.isInHorizontalBoundaries());
        assertFalse(this.object.isInVerticalBoundaries());
    }



    @Test
    public void test() {
        this.object.setVelocityY(2);
        assertEquals(2, this.object.getVelocityY());

        this.object.setVelocityX(1);
        assertEquals(1, this.object.getVelocityX());

        assertEquals(10,this.object.getWidth());
        assertEquals(22 ,this.object.getHeight());
    }


    @Test
    public void hitTest() {
        assertTrue(this.object.hits(this.object));
        this.object.setActive(false);
        assertFalse(this.object.hits(this.object));
        assertFalse(this.object.isActive());
    }


    @Test
    public void moveTest() {
        this.object.move();
        assertEquals(23,this.object.getPositionX());
        assertEquals(31,this.object.getPositionY());
    }



}
