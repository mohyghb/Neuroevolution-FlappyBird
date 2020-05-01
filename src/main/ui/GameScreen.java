package ui;

import components.network.MoNeuralNetwork;
import constants.MoFunctions;
import flappybird.MoBird;
import flappybird.MoButton;
import flappybird.MoConstants;
import flappybird.MoWall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

public class GameScreen extends JPanel implements ActionListener {


    private static final int INITIAL_NUMBER_WALLS = 14;
    private static final int INITIAL_NUMBER_BIRDS = 500;
    private static final int THRESHOLD_USE_OLD = 5;


    private ArrayList<MoBird> previousBirds;
    private ArrayList<MoBird> birds;
    private ArrayList<MoWall> walls;
    private MoBird bestBird;

    private Timer timer;
    private MoGameState gameState;
    private MoGameType gameType;
    private int generation;
    private int highScore;
    private int currentScore;


    private MoButton saveButton;
    private MoButton loadButton;



    public GameScreen(MoGameType gameType) {
        this.gameType = gameType;
        this.highScore = 0;
        this.generation = 1;
        initClass();
    }

    private void initClass() {
        super.setLayout(null);
        this.currentScore = 0;
        this.gameState = MoGameState.PLAYING;
        this.initSaveButton();
        this.initLoadButton();
        this.initBirds();
        this.initWalls();
        this.initTimer();
        this.initKeyForJump();
    }


    private void initSaveButton() {
        this.saveButton = new MoButton("Save",800,MoConstants.WINDOW_HEIGHT - 100,100,40,Color.darkGray,Color.white);
        this.saveButton.addActionListener(e -> {
            this.onSave();
        });
        super.add(this.saveButton);
    }


    public void onSave() {
        timer.stop();
        String name = JOptionPane.showInputDialog("Enter a name to save this");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Select a folder");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION && name != null && !name.isEmpty()) {
            boolean done = this.bestBird.save(chooser.getSelectedFile().toString() + "\\" + name + ".txt");
            if (done) {
                System.out.print("done saving");
            }
        }
        timer.start();
    }


    private void initLoadButton() {
        this.loadButton = new MoButton("Load...",this.saveButton.getX() + this.saveButton.getWidth() + 50,
                MoConstants.WINDOW_HEIGHT - 100,100,40,Color.darkGray,Color.white);
        this.loadButton.addActionListener(e -> {
            this.onLoad();
        });
        super.add(this.loadButton);
    }

    public void onLoad() {
        timer.stop();
        JFileChooser jfc = new JFileChooser();
        jfc.showDialog(null,"Select");
        jfc.setVisible(true);
        File filename = jfc.getSelectedFile();
        if (filename != null) {
            gameOverState();
            this.birds = new ArrayList<>();
            this.gameType = MoGameType.LOADED;
            int randYPos = MoFunctions.getRandom(MoConstants.GROUND_LEVEL_Y - MoBird.SIZE, 0);
            MoBird moBird = new MoBird(MoBird.INITIAL_X_POSITION, randYPos,MoBird.SIZE, MoBird.SIZE);
            moBird.load(filename.getPath());
            birds.add(moBird);
            timer.start();
        }

    }


    //Bird section

    private void initBirds() {
        this.birds = new ArrayList<>();
        for (int i = 0; i < INITIAL_NUMBER_BIRDS; i++) {
            int randYPos = MoFunctions.getRandom(MoConstants.GROUND_LEVEL_Y - MoBird.SIZE, 0);
            MoBird moBird = new MoBird(MoBird.INITIAL_X_POSITION, randYPos,MoBird.SIZE, MoBird.SIZE);
            this.birds.add(moBird);
        }
    }

    private void drawBirds(Graphics2D g) {
        for (MoBird moBird: this.birds) {
            moBird.draw(g);
            //break;
        }
    }

    private void initKeyForJump() {
        MoConstants.addKeyBinding(this, KeyEvent.VK_UP,"jump",(evt) -> {
            if (this.timer.isRunning()) {
                this.timer.stop();
            } else {
                this.timer.start();
            }
//            this.birds.get(0).update();
        },false);
    }


    private void checkCollisionBirds() {
        ArrayList<MoWall> nextWalls = this.getNextWalls();
        for (MoBird bird: this.birds) {
            bird.checkCollision(nextWalls);
        }
    }

    private void moveBirds() {
        for (MoBird bird: birds) {
            bird.moveY();
        }
    }

    private boolean allBirdsLost() {
        for (MoBird moBird: birds) {
            if (moBird.isActive()) {
                return false;
            }
        }
        return true;
    }


    private void decideBirds() {
        ArrayList<MoWall> moWalls = this.getNextWalls();
        for (MoBird bird: this.birds) {
            bird.decide(moWalls);
        }
    }


    private void findBestBird() {
        for (MoBird bird: this.birds) {
            if (this.bestBird == null) {
                this.bestBird = bird;
            } else if (bird.getDistance() > this.bestBird.getDistance()) {
                this.bestBird = bird;
            }
        }
    }

    // END of Bird section


    // wall section
    private void initWalls() {
        this.walls = new ArrayList<>();

        for (int i = 0; i < INITIAL_NUMBER_WALLS; i++) {
            boolean firstWall = i == 0;
            MoWall moWall = new MoWall(
                    firstWall ? null : walls.get(i - 1)
            );
            this.walls.add(moWall);
        }
    }

    private void drawWalls(Graphics g) {
        for (MoWall moWall: this.walls) {
            moWall.drawWall(g);
        }
    }

    private void moveWalls() {
        for (MoWall moWall : this.walls) {
            moWall.moveWalls();
        }
    }

    private void makeNewWalls() {
        int index = 0;
        for (MoWall wall: this.walls) {
            if (!wall.isActive()) {
                this.walls.add(new MoWall(walls.get(this.walls.size() - 1)));
                this.walls.remove(index);
                break;
            }
            index++;
        }
    }

    // produces [previous wall, next wall] and in between
    // are the birds;
    private ArrayList<MoWall> getNextWalls() {
        ArrayList<MoWall> nextWalls = new ArrayList<>();
        int currentBirdXPosition = (int) this.birds.get(0).getPositionX();
        int index = 0;
        for (MoWall wall: this.walls) {
            if (wall.getXPosition() < currentBirdXPosition) {
                nextWalls.add(wall);
                nextWalls.add(walls.get(index + 1));
                break;
            }
            index++;
        }
        if (nextWalls.isEmpty()) {
            nextWalls.add(walls.get(0));
        }
        return nextWalls;
    }

    // end of wall section



    //timer
    private void initTimer() {
        this.timer = new Timer(MoConstants.FPS,this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0, MoConstants.WINDOW_WIDTH,MoConstants.WINDOW_HEIGHT);
        this.drawBirds((Graphics2D) g);
        this.drawWalls(g);
        this.drawGrass(g);
        this.drawGeneration(g);
    }


    private void drawGrass(Graphics g) {
        g.setColor(Color.gray);
        g.fillRect(0,MoConstants.GROUND_LEVEL_Y,  MoConstants.WINDOW_WIDTH,  MoConstants.HEIGHT_GRASS);
    }

    private void drawGeneration(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
        g.drawString("Generation: " + this.generation, 100,MoConstants.WINDOW_HEIGHT - 70);
        g.drawString("Score: " + this.currentScore, 350, MoConstants.WINDOW_HEIGHT - 70);
        g.drawString("Highest Score: " + this.highScore, 550, MoConstants.WINDOW_HEIGHT - 70);
    }

    private void getScore() {
        for (MoBird bird: birds) {
            if (bird.isActive()) {
                this.currentScore = bird.getScore();
                break;
            }
        }
        if (this.currentScore > this.highScore) {
            this.highScore = this.currentScore;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (this.gameState) {
            case PLAYING:
                this.statePlaying();
                break;
            case GAME_OVER:
                this.decideGameOverState();
                break;
            default:
                break;
        }


        repaint();

    }

    private void statePlaying() {
        if (this.allBirdsLost()) {
            this.gameState = MoGameState.GAME_OVER;
            return;
        }
        this.getScore();
        this.decideBirds();
        this.moveBirds();
        this.findBestBird();
        this.moveWalls();
        this.makeNewWalls();
        this.checkCollisionBirds();
    }



    private void decideGameOverState() {
        System.out.printf("Best Bird : %d", this.bestBird.getScore());
        switch (this.gameType) {
            case AI:
                this.aiGameOverState();
                break;
            case PLAYER:
                this.playerGameOverState();
                break;
            case LOADED:
                this.gameOverState();
                this.birds.get(0).reset();
                break;
            default:
                break;
        }
    }

    private void gameOverState() {
        this.currentScore = 0;
        this.initWalls();
        this.gameState = MoGameState.PLAYING;
    }

    private void playerGameOverState() {
        this.gameOverState();
        this.initBirds();
        this.initKeyForJump();
    }

    private void aiGameOverState() {
        this.nextGeneration();
        this.gameOverState();
    }

    private void nextGeneration() {
        this.previousBirds = this.birds;
        this.calculateFitness();
        this.initNextPopulation();
        this.generation++;
    }

    private void calculateFitness() {
        double sum = 0;
        for (MoBird moBird: birds) {
            sum += moBird.getDistance();
        }
        for (MoBird moBird: birds) {
            moBird.setFitness(moBird.getDistance() / sum);
        }
    }

    private void initNextPopulation() {
        initBirds();
        if (this.highScore > THRESHOLD_USE_OLD) {
            for (MoBird moBird: birds) {
                MoBird parent1 = this.findParent();
                MoBird parent2 = this.findParent();
                moBird.setMoNeuralNetwork(MoNeuralNetwork.crossOver(
                        parent1.getMoNeuralNetwork(),
                        parent2.getMoNeuralNetwork()));
                moBird.mutate();
            }
        }

    }

    // look into the previous birds
    // select the best to breed
    private MoBird findParent() {
        double rand = MoFunctions.getRandom(1,0.001);
        int index = 0;
        while (rand > 0) {
            rand -= this.previousBirds.get(index).getFitness();
            index++;
        }
        index--;
        return this.previousBirds.get(index);
    }

//    private MoBird findParent() {
//        double rand = MoFunctions.getRandom(1,0.1);
//        for () {
//
//        }
//    }

}
