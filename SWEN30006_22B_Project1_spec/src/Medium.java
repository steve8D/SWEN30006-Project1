package src;

import java.util.Properties;

public class Medium extends Levels{

    public Medium(TetrisGameCallback gameCallback, Properties properties, UIController uiController) {
        super(gameCallback, properties, uiController);
    }

    @Override
    protected int generateRandomBlockId() {
        return random.nextInt(10);
    }

    @Override
    protected double getSpeedMultiplier() {
        return 1/1.2;
    }
}
