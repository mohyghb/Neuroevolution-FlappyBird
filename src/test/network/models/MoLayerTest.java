package network.models;

import components.network.models.MoLayer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoLayerTest {


    MoLayer moLayer;


    @BeforeEach
    public  void init() {
        this.moLayer = new MoLayer();
    }


    @Test
    public void hiddenOrOutputLayerTest() {

        moLayer = new MoLayer(2,3);
        assertEquals(3,moLayer.getNeurons().length);
        assertEquals(2,moLayer.getNeurons()[0].getWeights().length);

        moLayer = new MoLayer(0,5);
        assertEquals(5,moLayer.getNeurons().length);
        assertEquals(0,moLayer.getNeurons()[0].getWeights().length);
    }

    @Test
    public void inputLayerTest() {
        moLayer = new MoLayer(new float[]{2,3,4});
        assertEquals(3, moLayer.getNeurons().length);
        assertEquals(2,moLayer.getNeurons()[0].getValue());
        assertEquals(3,moLayer.getNeurons()[1].getValue());
        assertEquals(4,moLayer.getNeurons()[2].getValue());
    }

    @Test
    public void readLayerTest() {
        this.moLayer.readLayer("[2,3,5,4,1]");
        assertEquals(1,moLayer.getNeurons().length);
        assertEquals(2,moLayer.getNeurons()[0].getValue());
        assertEquals(3,moLayer.getNeurons()[0].getGradient());
        assertEquals(5,moLayer.getNeurons()[0].getBias());
        assertEquals(4,moLayer.getNeurons()[0].getWeights()[0]);
        assertEquals(1,moLayer.getNeurons()[0].getWeights()[1]);

        this.moLayer.readLayer("\n[2,3,5,4,1]\n[2,31,51,41,31]");
    }


    @Test
    public void downloadLayerTest() {
        this.moLayer = new MoLayer("[5,-1,5,2.2,1]");
        assertEquals("[5.0,-1.0,5.0,2.2,1.0]" + MoLayer.SEP_KEY,this.moLayer.downloadLayer());
        this.moLayer = new MoLayer("[5,-1,5,2.2,1]\n[5,-1,5,2.4,3]");
        assertEquals("[5.0,-1.0,5.0,2.2,1.0]\n[5.0,-1.0,5.0,2.4,3.0]" + MoLayer.SEP_KEY,this.moLayer.downloadLayer());
    }

    @Test
    public void getCombinationTest() {
        MoLayer l1 = new MoLayer(2,3);
        MoLayer l2 = new MoLayer(2,3);
        MoLayer.getCombination(l1,l2);
    }






}
