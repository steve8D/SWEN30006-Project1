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
    private int score = 0;
    public UIController(TetrisGameCallback gameCallback, Properties properties, Tetris tetris) {
        gameGrid1 = tetris.gameGrid1;
        gameGrid2 = tetris.gameGrid2;
        scoreText = tetris.scoreText;
        levels = new Levels(gameCallback, properties, this);
        score = 0;
        showScore(score);
    }
    void showBlockPreview(TetrisPiece previewBlock) {
        if (blockPreview != null)
            blockPreview.removeSelf();
        previewBlock.display(gameGrid2, new Location(2, 1));
        blockPreview = previewBlock;
    }
    void showGameOver() {
        gameGrid1.addActor(new Actor("sprites/gameover.gif"), new Location(5, 5));
    }

    // Handle user input to move block. Arrow left to move left, Arrow right to move right, Arrow up to rotate and
    // Arrow down for going down
    private void moveBlock(int keyEvent) {
        if (levels.getCurrentBlock() != null) {
            currentBlock = levels.getCurrentBlock();
        }
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

    public void showScore(final int score) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                scoreText.setText(score + " points");
            }
        });
    }
    public TetrisPiece createFirstTetrisBlock() {
        levels.incrementRoundCount();
        currentBlock = levels.createRandomTetrisBlock();
        return currentBlock;
    }

    public void act() {
        moveBlock(gameGrid1.getKeyCode());
        levels.removeFilledLine();
    }

    protected ch.aplu.jgamegrid.GameGrid gameGrid1;
    private ch.aplu.jgamegrid.GameGrid gameGrid2;
    private javax.swing.JTextField scoreText;
}
