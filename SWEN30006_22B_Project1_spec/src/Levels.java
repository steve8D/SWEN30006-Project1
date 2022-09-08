package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public abstract class Levels {
    private TetrisPiece currentBlock = null;  // Currently active block
    public TetrisPiece getCurrentBlock() {
        return currentBlock;
    }
    private int score = 0;
    private int slowDown = 5;
    private int rounds = 0;
    private boolean isAuto = false;
    protected Random random = new Random(0);
    private int seed = 30006;
    // For testing mode, the block will be moved automatically based on the blockActions.
    // L is for Left, R is for Right, T is for turning (rotating), and D for down
    private String [] blockActions = new String[10];
    private int blockActionIndex = 0;
    private TetrisGameCallback gameCallback;
    private UIController uiController;
    private ch.aplu.jgamegrid.GameGrid gameGrid1;
    public Levels(TetrisGameCallback gameCallback, Properties properties, UIController uiController) {
        initWithProperties(properties);
        this.gameCallback = gameCallback;
        this.uiController = uiController;
        this.gameGrid1 = uiController.gameGrid1;
        this.blockActionIndex = 0;
        this.score = 0;
        this.rounds = 1;
        this.slowDown = 5;
    }

    // Initialise object
    private void initWithProperties(Properties properties) {
        this.seed = Integer.parseInt(properties.getProperty("seed", "30006"));
        random = new Random(seed);
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));
        String blockActionProperty = properties.getProperty("autoBlockActions", "");
        blockActions = blockActionProperty.split(",");
    }

    // create a block and assign to a preview mode
    public TetrisPiece createRandomTetrisBlock() {
        // If the game is in auto test mode, then the block will be moved according to the blockActions
        String currentBlockMove = "";
        if (blockActions.length > blockActionIndex) {
            currentBlockMove = blockActions[blockActionIndex];
        }

        blockActionIndex++;
        TetrisPiece t = null;
        TetrisPiece preview = null;
        int rnd = generateRandomBlockId();

        switch (rnd) {
            case 0:
                t = new I(this);
                preview = new I(this);
                break;
            case 1:
                t = new J(this);
                preview = new J(this);
                break;
            case 2:
                t = new L(this);
                preview = new L(this);
                break;
            case 3:
                t = new O(this);
                preview = new O(this);
                break;
            case 4:
                t = new S(this);
                preview = new S(this);
                break;
            case 5:
                t = new T(this);
                preview = new T(this);
                break;
            case 6:
                t = new Z(this);
                preview = new Z(this);
                break;
            case 7:
                t = new Plus(this);
                preview = new Plus(this);
                break;
            case 8:
                t = new P(this);
                preview = new P(this);
                break;
            case 9:
                t = new Q(this);
                preview = new Q(this);
                break;
        }
        if (isAuto) {
            t.setAutoBlockMove(currentBlockMove);
        }
        uiController.showBlockPreview(preview);

        // Show preview tetrisBlock
        t.setSlowDown(slowDown);
        return t;
    }
    public void setCurrentTetrisBlock(TetrisPiece t) {
        gameCallback.changeOfBlock(currentBlock);
        currentBlock = t;
    }

    private void setSlowDown(int slowDown) {
        this.slowDown = slowDown;
    }

    public void removeFilledLine() {
        for (int y = 0; y < gameGrid1.nbVertCells; y++) {
            boolean isLineComplete = true;
            TetroBlock[] blocks = new TetroBlock[gameGrid1.nbHorzCells];   // One line
            // Calculate if a line is complete
            for (int x = 0; x < gameGrid1.nbHorzCells; x++) {
                blocks[x] =
                        (TetroBlock) gameGrid1.getOneActorAt(new Location(x, y), TetroBlock.class);
                if (blocks[x] == null) {
                    isLineComplete = false;
                    break;
                }
            }
            if (isLineComplete) {
                // If a line is complete, we remove the component block of the shape that belongs to that line
                for (int x = 0; x < gameGrid1.nbHorzCells; x++)
                    gameGrid1.removeActor(blocks[x]);
                ArrayList<Actor> allBlocks = gameGrid1.getActors(TetroBlock.class);
                for (Actor a : allBlocks) {
                    int z = a.getY();
                    if (z < y)
                        a.setY(z + 1);
                }
                gameGrid1.refresh();
                score++;
                gameCallback.changeOfScore(score);
                uiController.showScore(score);
                // Set speed of tetrisBlocks
                if (score > 10)
                    slowDown = 4;
                if (score > 20)
                    slowDown = 3;
                if (score > 30)
                    slowDown = 2;
                if (score > 40)
                    slowDown = 1;
                if (score > 50)
                    slowDown = 0;
            }
        }
    }
    public void gameOver() {
        currentBlock = null;
        uiController.showGameOver();

        if (isAuto) {
            System.exit(0);
        }
    }
    /*helper function for returning a block Id out of the valid blocks*/
    abstract protected int generateRandomBlockId();
    abstract protected double getSpeedMultiplier();

    public void incrementRoundCount() {
        this.rounds++;
    }
}
