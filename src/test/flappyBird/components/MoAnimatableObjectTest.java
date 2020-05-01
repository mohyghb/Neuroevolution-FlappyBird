package flappyBird.components;

import flappybird.MoAnimatableObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoAnimatableObjectTest {

    MoAnimatableObject object;

    @BeforeEach
    public void init(){
        object = new MoAnimatableObject(3,4,5,6,0);
        object = new MoAnimatableObject(3,4,5,6,0,-1,-1);
    }

    @Test
    public void moveYTest() {
        object.moveY(2);
        assertEquals(6, this.object.getPositionY());
    }

    @Test
    public void checkTest() {
        this.object.check();
        assertFalse(this.object.isInAnimation());
    }

    @Test
    public void moveXTest() {
        this.object.moveX(2);
        assertEquals(5,this.object.getPositionX());
    }


    @Test
    public void animationTest() {
        assertFalse(this.object.isInAnimation());
        this.object.startAnimation();
        assertTrue(this.object.isInAnimation());
    }




}
