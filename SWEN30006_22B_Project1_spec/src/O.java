// O.java
package src;
import ch.aplu.jgamegrid.*;


class O extends TetrisPiece
{
  private static final int BLOCK_ID = 3;
  private static final String BLOCK_NAME = "O";

  O(Tetris tetris)
  {
    super(tetris, BLOCK_NAME, BLOCK_ID);
  }

  @Override
  protected Location[][] updateRotationId() {

    Location[][] r = new Location[4][4];
    // rotId 0
    r[0][0] = new Location(new Location(0, 0));
    r[1][0] = new Location(new Location(1, 0));
    r[2][0] = new Location(new Location(1, 1));
    r[3][0] = new Location(new Location(0, 1));
    // rotId 1
    r[0][1] = new Location(new Location(0, 0));
    r[1][1] = new Location(new Location(1, 0));
    r[2][1] = new Location(new Location(1, 1));
    r[3][1] = new Location(new Location(0, 1));
    // rotId 2
    r[0][2] = new Location(new Location(0, 0));
    r[1][2] = new Location(new Location(1, 0));
    r[2][2] = new Location(new Location(1, 1));
    r[3][2] = new Location(new Location(0, 1));
    // rotId 3
    r[0][3] = new Location(new Location(0, 0));
    r[1][3] = new Location(new Location(1, 0));
    r[2][3] = new Location(new Location(1, 1));
    r[3][3] = new Location(new Location(0, 1));
    return r;

  }
}
