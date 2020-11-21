package frog;

import gameCommons.Game;
import util.Direction;

public class FrogInf extends Frog { // implements IFrog (from Frog)

    private int score;
    private int maxScore;

    public FrogInf(Game game) {
        super(game);
        this.y = 2;
        this.score = 0;
        this.maxScore = 0;
    }

    public int getScore() {
        return maxScore;
    }

    @Override
    public void move(Direction key) {
        if (game.isOver()) return;

        switch (key) {
            case up:
                score++;
                game.moveLanes(Direction.up);
                break;
            case down:
                score--;
                game.moveLanes(Direction.down);
                break;
            case left:
                if (x > 0) x--;
                break;
            case right:
                if (x < game.width - 1) x++;
                break;
        }
        this.direction = key;

        game.testLose();
        if (score > maxScore) maxScore = score;
    }
}
