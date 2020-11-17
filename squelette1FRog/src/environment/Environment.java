package environment;

import java.util.ArrayList;

import util.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;

public class Environment implements IEnvironment {

    private Game game;
    private ArrayList<Lane> lanes;

    public Environment(Game game) {
        this.game = game;
        this.lanes = new ArrayList<>();
        boolean leftToRight = false;
        for (int i = 1; i < game.height - 1; i += 2) {
            lanes.add(new Lane(game, i, leftToRight));
            lanes.add(new Lane(game, i + 1, leftToRight));
            leftToRight = !leftToRight;
        }
    }

    public boolean isSafe(Case c) {
        // on considere les cases hors de la grille comme safe (la grenouille ne pourra dans tous les cas pas s'y déplacer
        if (c.ord < 1 || c.ord >= game.height - 1 || c.absc < 0 || c.absc >= game.width) return true;
        else return lanes.get(c.ord - 1).isSafe(c);
    }

    public boolean isWinningPosition(Case c) {
        return c.ord == game.height - 1;
    }

    public void update() {
        for (Lane l : lanes) l.update();
    }

}
