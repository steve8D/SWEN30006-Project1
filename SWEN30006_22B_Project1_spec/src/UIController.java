package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;

public class UIController {
    protected ch.aplu.jgamegrid.GameGrid gameGrid1;
    private final ch.aplu.jgamegrid.GameGrid gameGrid2;
    private final javax.swing.JTextField scoreText;
    private TetrisPiece blockPreview = null;   // block in preview window
    private Levels levels;
    private int score = 0;

    private boolean isAuto = false;

    public UIController(TetrisGameCallback gameCallback, Properties properties, Tetris tetris) {
        gameGrid1 = tetris.gameGrid1;
        gameGrid2 = tetris.gameGrid2;
        scoreText = tetris.scoreText;

        String difficulty = properties.getProperty("difficulty");
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));

        if (difficulty.equals("easy")) {
            levels = new Easy(gameCallback, properties, this);
        } else if (difficulty.equals("medium")) {
            levels = new Medium(gameCallback, properties, this);
        } else if (difficulty.equals("madness")) {
            levels = new Madness(gameCallback, properties, this);
        }

        score = 0;
        showScore(score);
    }

    //display the block on the preview window
    void showBlockPreview(TetrisPiece previewBlock) {
        if (blockPreview != null)
            blockPreview.removeSelf();
        previewBlock.display(gameGrid2, new Location(2, 1));
        blockPreview = previewBlock;
    }

    //show the game over screen
    void showGameOver() {
        gameGrid1.addActor(new Actor("sprites/gameover.gif"), new Location(5, 5));
        gameGrid1.doPause();
    }

    // Handle user input to move block. Arrow left to move left, Arrow right to move right, Arrow up to rotate and
    // Arrow down for going down
    private void moveBlock(int keyEvent) {
        if (levels.getCurrentBlock() != null) {
            TetrisPiece currentBlock = levels.getCurrentBlock();
            switch (keyEvent) {
                case KeyEvent.VK_UP:
                    if (levels.canRotate()) {
                        currentBlock.rotate();
                    }
                    break;
                case KeyEvent.VK_LEFT:
                    currentBlock.left();
                    break;
                case KeyEvent.VK_RIGHT:
                    currentBlock.right();
                    break;
                case KeyEvent.VK_DOWN:
                    currentBlock.drop();
                    break;
                default:
            }
        }

    }

    // show the score on the text box
    public void showScore(final int score) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                scoreText.setText(score + " points");
            }
        });
    }

    //  called when a round starts
    public void start() {
        levels.incrementRoundCount();
        TetrisPiece currentBlock = levels.createRandomTetrisBlock();
        levels.setCurrentTetrisBlock(currentBlock);
        gameGrid1.addActor(currentBlock, new Location(6, 0));

        //begin the fall speed
        newFallSpeed();
    }

    public void newFallSpeed() {
        gameGrid1.setSimulationPeriod(getSimulationTime());
    }


    public void act() {
        moveBlock(gameGrid1.getKeyCode());
        levels.removeFilledLine();
    }

    public Levels getLevels() {
        return levels;
    }

    // Different speed for manual and auto mode
    private int getSimulationTime() {
        int simulationTime;
        if (isAuto) {
            simulationTime = 10;
        } else {
            simulationTime = 100;
        }
        // simulation time is multiplied by difficulty speed boost
        simulationTime = (int) (simulationTime * levels.getSpeedMultiplier());

        return (simulationTime);
    }
}
