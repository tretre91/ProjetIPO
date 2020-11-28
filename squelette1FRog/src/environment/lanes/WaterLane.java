package environment.lanes;

import environment.elements.Log;
import environment.elements.Obstacle;
import gameCommons.Game;
import util.Case;

import java.awt.*;
import java.util.BitSet;

public class WaterLane extends Lane {

    public WaterLane(Game game, int ord, boolean leftToRight) {
        super(game, ord, new Color(27, 176, 217));
        this.leftToRight = leftToRight;
        this.speed = game.randomGen.nextInt(7) + game.minSpeedInTimerLoops;
        this.countdown = speed;
        this.density = (game.randomGen.nextInt(15) + 10) / 100.0f;

        initialize();
    }

    @Override
    public BitSet isSafe(Case c) {
        BitSet res = super.isSafe(c);
        res.set(0);
        for (Obstacle obs: obstacles) {
            if (obs.overlaps(c)) {
                res.flip(0);
                break;
            }
        }
        return res;
    }

    @Override
    protected void mayAddObstacle() {
        if (isSafe(getFirstCase()).get(0) && isSafe(getBeforeFirstCase()).get(0)) {
            if (game.randomGen.nextDouble() < density) {
                obstacles.add(new Log(game, ord, leftToRight));
            }
        }
    }

}
