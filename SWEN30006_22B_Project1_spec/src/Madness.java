package src;

import java.util.Properties;

public class Madness extends Levels{
    public Madness(TetrisGameCallback gameCallback, Properties properties, UIController uiController) {
        super(gameCallback, properties, uiController);
    }

    @Override
    protected int generateRandomBlockId() {
        return random.nextInt(10);
    }

    @Override
    public double getSpeedMultiplier() {
        double randomMultiplier = Math.random()+1; //random number from 1 to 2

        return 1/randomMultiplier;
    }

    @Override
    public void setCurrentTetrisBlock(TetrisPiece t) {
        super.setCurrentTetrisBlock(t);
        uiController.newFallSpeed();

    }
}
