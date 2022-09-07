package src;

import ch.aplu.jgamegrid.GGActListener;
import ch.aplu.jgamegrid.Location;

import javax.swing.*;
import java.util.Properties;

public class TetrisInitialize extends JFrame implements GGActListener {
    private TetrisPiece currentBlock = null;  // Currently active block
    private UIController uiController;
    private boolean isAuto = false;

    public ch.aplu.jgamegrid.GameGrid gameGrid1;
    public ch.aplu.jgamegrid.GameGrid gameGrid2;
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea jTextArea1;
    public javax.swing.JTextField scoreText;
    public javax.swing.JButton startBtn;
    private TetrisComponents tetrisComponents;

    public TetrisInitialize(TetrisGameCallback gameCallback, Properties properties) {
        // Initialise value
        initWithProperties(properties);

        // Set up the UI components. No need to modify the UI Components
        tetrisComponents = new TetrisComponents();
        tetrisComponents.initComponents(this);
        gameGrid1.addActListener(this);
        gameGrid1.setSimulationPeriod(getSimulationTime());

        // Add the first block to start
        uiController = new UIController(gameCallback, properties, this);
        currentBlock = uiController.createFirstTetrisBlock();
        gameGrid1.addActor(currentBlock, new Location(6, 0));
        gameGrid1.doRun();

        // Do not lose keyboard focus when clicking this window
        gameGrid2.setFocusable(false);
        setTitle("SWEN30006 Tetris Madness");
    }

    // Initialise object
    private void initWithProperties(Properties properties) {
        isAuto = Boolean.parseBoolean(properties.getProperty("isAuto"));
    }

    // Start a new game
    public void startBtnActionPerformed(java.awt.event.ActionEvent evt)
    {
        gameGrid1.doPause();
        gameGrid1.removeAllActors();
        gameGrid2.removeAllActors();
        gameGrid1.refresh();
        gameGrid2.refresh();
        gameGrid2.delay(getDelayTime());
        currentBlock = uiController.createFirstTetrisBlock();
        gameGrid1.addActor(currentBlock, new Location(6, 0));
        gameGrid1.doRun();
        gameGrid1.requestFocus();
    }

    // Different speed for manual and auto mode
    private int getSimulationTime() {
        if (isAuto) {
            return 10;
        } else {
            return 100;
        }
    }

    private int getDelayTime() {
        if (isAuto) {
            return 200;
        } else {
            return 2000;
        }
    }

    @Override
    public void act() {
        uiController.act();
    }
}
