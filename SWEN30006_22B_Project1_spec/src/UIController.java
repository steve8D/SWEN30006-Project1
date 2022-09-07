package src;

import ch.aplu.jgamegrid.Actor;
import ch.aplu.jgamegrid.Location;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Properties;

public class UIController {
    private TetrisPiece currentBlock = null;  // Currently active block
    private TetrisPiece blockPreview = null;   // block in preview window
    private Levels levels;
    private TetrisInitialize tetrisInitialize;
    private int score = 0;
    public UIController(TetrisGameCallback gameCallback, Properties properties, TetrisInitialize tetrisInitialize) {
        this.tetrisInitialize = tetrisInitialize;
        gameGrid1 = tetrisInitialize.gameGrid1;
        gameGrid2 = tetrisInitialize.gameGrid2;
        scoreText = tetrisInitialize.scoreText;
        levels = new Levels(gameCallback, properties, this);
        score = 0;
        showScore(score);
    }
    void showBlockPreview(TetrisPiece previewBlock) {
        if (blockPreview != null)
            blockPreview.removeSelf();
        previewBlock.display(gameGrid2, new Location(2, 1));
        blockPreview = previewBlock;
        currentBlock = levels.getCurrentBlock();
    }
    void showGameOver() {
        gameGrid1.addActor(new Actor("sprites/gameover.gif"), new Location(5, 5));
    }

    // Handle user input to move block. Arrow left to move left, Arrow right to move right, Arrow up to rotate and
    // Arrow down for going down
    private void moveBlock(int keyEvent) {
        switch (keyEvent) {
            case KeyEvent.VK_UP:
                currentBlock.rotate();
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
                return;
        }
    }

    private void showScore(final int score) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                scoreText.setText(score + " points");
            }
        });
    }
    public TetrisPiece createRandomTetrisBlock() {
        return levels.createRandomTetrisBlock();
    }

    public ch.aplu.jgamegrid.GameGrid gameGrid1;
    public ch.aplu.jgamegrid.GameGrid gameGrid2;
    public javax.swing.JTextField scoreText;

    public void act() {
        moveBlock(gameGrid1.getKeyCode());
        levels.removeFilledLine();
    }
}
