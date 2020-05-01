package flappybird;

import components.network.MoNeuralNetwork;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class MoBird extends MoAnimatableObject implements MoFile {


    public static final double ANIMATION_TIME_MS = 200;
    public static final int INITIAL_X_POSITION = 130;
    public static final int SIZE = (MoConstants.WINDOW_HEIGHT + MoConstants.WINDOW_WIDTH) / 100;


    private int score;
    private int distance;
    private double fitness;
    private MoNeuralNetwork moNeuralNetwork;
    private float[] lastOutPut;
    private HashSet<Integer> hashSetWalls;

    public MoBird(int x, int y, int w, int h) {
        super(x,y,w,h,ANIMATION_TIME_MS);
        this.moNeuralNetwork = new MoNeuralNetwork("[16,17,2]");
        this.lastOutPut = new float[2];
        this.hashSetWalls = new HashSet<>();
        this.fitness = 0;
        this.distance = 0;
        this.score = 0;
    }


    public MoBird(int x, int y, int w, int h,int xv,int yv) {
        super(x,y,w,h,xv,yv,ANIMATION_TIME_MS);
        this.moNeuralNetwork = new MoNeuralNetwork("[16,17,2]");
        this.lastOutPut = new float[2];
        this.hashSetWalls = new HashSet<>();
        this.fitness = 0;
        this.distance = 0;
        this.score = 0;
    }





    public void moveY() {
        super.check();
        if (super.isInAnimation()) {
            // we move the bird up
            super.moveY(MoConstants.SPEED_Y_BIRD_JUMP + MoConstants.GRAVITY);
        } else {
            // we move it down
            super.moveY(MoConstants.SPEED_Y_BIRD_FALL);
        }
    }


    // EFFECTS: updates the position of the bird
    //          if the space bar is pressed
    public void update() {
        this.startAnimation();
        this.moveY();
    }

    public void checkCollision(ArrayList<MoWall> walls) {
        if (this.isActive()) {
            for (MoWall wall: walls) {
                for (MoAnimatableObject w: wall.getWalls()) {
                    if (super.hits(w)) {
                      //  System.out.println("hit detected ... " + wall.getXPosition());
                        super.setActive(false);
                        // make a training data for it
                        //this.makeTrainingData(walls,true);
                    }
                }
                this.checkScore(wall.getSafeGap());
            }

        }
    }


    private void checkScore(MoAnimatableObject safeGap) {
        if (this.isActive() && super.hits(safeGap) && !this.hashSetWalls.contains(safeGap.hashCode())) {
            this.score++;
            // to make sure that this wall is counted once only
            this.hashSetWalls.add(safeGap.hashCode());
        }
    }


    private float[] getInputs(ArrayList<MoWall> moWalls) {
        float[] inputs = new float[16];
//        if (moWalls.size() == 2) {
//
//        }
        MoWall moWall = moWalls.get(moWalls.size() - 1);
        MoAnimatableObject moAnimatableObject = moWall.getSafeGap();
        float[][] bird = this.getCorners();
        //float[][] wallPre = moWalls.size() == 1 ?new float[4][2]:moWalls.get(0).getSafeGap().getCorners();
        float[][] wall = moAnimatableObject.getCorners();
        int index = 0;
        for (int i = 0; i < bird.length; i++) {
            inputs[index] = bird[i][0];
            inputs[++index] = bird[i][1];
//            inputs[++index] = wallPre[i][1];
//            inputs[++index] = wallPre[i][1];
            inputs[++index] = wall[i][0];
            inputs[++index] = wall[i][1];
            index++;
        }

//        float[] inputs = new float[6];
//        MoAnimatableObject moAnimatableObject = moWalls.get(moWalls.size() - 1).getSafeGap();
//        inputs[0] = this.getPositionY();
//        inputs[1] = moAnimatableObject.getPositionX();
//        inputs[2] = moAnimatableObject.getPositionY();
//        inputs[3] = moAnimatableObject.getPositionY() + moAnimatableObject.getHeight();
//        inputs[4] = this.getVelocityY();
//        inputs[5] = moAnimatableObject.getWidth();


//        float[] inputs = new float[6];
////        if (moWalls.size() == 2) {
////
////        }
//        MoWall moWall = moWalls.get(moWalls.size() - 1);
//        MoAnimatableObject moAnimatableObject = moWall.getSafeGap();
//        inputs[0] = this.getPositionY();
//        inputs[1] = moWall.getXPosition();
//        inputs[2] = moAnimatableObject.getPositionY();
//        inputs[3] = moAnimatableObject.getPositionY() + moAnimatableObject.getHeight();
//        inputs[4] = this.getVelocityY();
//        inputs[5] = moWall.getWalls().get(0).getWidth();

        return inputs;
    }



    public void decide(ArrayList<MoWall> moWalls) {
        moNeuralNetwork.calculate(this.getInputs(moWalls));
        this.lastOutPut = moNeuralNetwork.getOutputs();
        if (this.lastOutPut[0] > this.lastOutPut[1]) {
            // jump
            this.update();
            //System.out.print("jump");
        }
    }


    public void draw(Graphics2D g) {
        if (this.isActive()) {
            this.distance++;
            g.setColor(Color.cyan);
            g.setStroke(new BasicStroke(3));
            g.drawRect(
                    super.getPositionX(),
                    super.getPositionY(),
                    super.getWidth(),
                    super.getHeight()
            );
        }

    }


    public void reset() {
        this.setActive(true);
        this.score = 0;
        this.fitness = 0;
        this.distance = 0;
    }

   // public ArrayList<MoTrainingData> getMoTrainingData() {
     //   return moTrainingData;
    //}

    public int getScore() {
        return score;
    }

    public int getDistance() {
        return distance;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void setMoNeuralNetwork(MoNeuralNetwork moNeuralNetwork) {
        this.moNeuralNetwork = moNeuralNetwork;
    }

    public MoNeuralNetwork getMoNeuralNetwork() {
        return moNeuralNetwork;
    }




    public void mutate() {
        this.moNeuralNetwork.mutate(0.1f);
    }


    @Override
    public boolean save(String path) {
        try {
            this.moNeuralNetwork.save(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void load(String path) {
        try {
            this.moNeuralNetwork.upload(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
