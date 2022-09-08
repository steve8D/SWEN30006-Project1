package src;

import java.util.Properties;

public class Madness extends Levels{
    public Madness(TetrisGameCallback gameCallback, Properties properties, UIController uiController) {
        super(gameCallback, properties, uiController);
    }

    @Override
    protected int generateRandomBlockId() {
        // allows for 3 extra pieces
        return random.nextInt(10);
    }

    @Override
    public double getSpeedMultiplier() {
        // increases the speed by between 1 and 2 times the speed
        double randomMultiplier = Math.random()+1; //random number from 1 to 2

        return 1/randomMultiplier;
    }

    @Override
    public void setCurrentTetrisBlock(TetrisPiece t) {
        super.setCurrentTetrisBlock(t);
        // madness needs to generate a new fall speed for each new piece
        uiController.newFallSpeed();

    }

    @Override
    public boolean canRotate() {

        return false;
    }
}
