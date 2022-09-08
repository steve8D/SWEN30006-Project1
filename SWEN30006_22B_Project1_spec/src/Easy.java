package src;

import java.util.Properties;

public class Easy extends Levels{

    public Easy(TetrisGameCallback gameCallback, Properties properties, UIController uiController) {
        super(gameCallback, properties, uiController);
    }

    @Override
    protected int generateRandomBlockId() {
        return random.nextInt(7);
    }

    @Override
    protected double getSpeedMultiplier() {
        return 1;
    }
}
