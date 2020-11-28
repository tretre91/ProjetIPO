package environment.env;

import environment.lanes.*;
import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;
import util.Direction;

import java.util.ArrayList;
import java.util.BitSet;

public class EnvInf implements IEnvironment {

    private Game game;
    private ArrayList<Lane> lanes = new ArrayList<>();
    private int frogHeight;
    private boolean leftToRight;

    public EnvInf(Game game){
        this.game = game;
        this.leftToRight = false;
        this.frogHeight = game.getFrogInitialHeight();

        lanes.add(new CarLane(game, game.getFrogInitialHeight(), leftToRight));
        while (lanes.size() < 2 * game.height) {
            pushBackLanes();
        }
        lanes.remove(0);
    }

    /**
     * Ajoute entre 1 et 3 voies à la fin de 'lanes'
     */
    private void pushBackLanes() {
        int nbOfLanes;
        double proba = game.randomGen.nextDouble();
        if (proba < 0.25) nbOfLanes = 1;
        else if (proba < 0.75) nbOfLanes = 2;
        else nbOfLanes = 3;

        int y = lanes.get(lanes.size() - 1).getOrd() + 1;
        for (int i = 0; i < nbOfLanes; i++) lanes.add(new WaterLane(game, y + i, leftToRight));
        leftToRight = !leftToRight;
    }

    public BitSet isSafe(Case c) {
        // on considère les cases hors de la grille comme safe (la grenouille ne pourra dans tous les cas pas s'y déplacer
        if (c.ord <= game.getFrogInitialHeight() || c.absc < 0 || c.absc >= game.width) return new BitSet(5);
        else return lanes.get(c.ord - (game.getFrogInitialHeight() + 1)).isSafe(c);
    }

    public boolean isWinningPosition(Case c) {
        return false; // on n'utilise pas cette méthode dans la version infinie, inclue pour des raisons de compatibilité
    }

    public void update() {
        for (Lane l: lanes) l.update();
    }

    public void move(Direction d) {
        if(d == Direction.up) {
            frogHeight++;
            for (Lane l: lanes) l.addOrd(-1);
            while (frogHeight > lanes.size() - game.height) {
               pushBackLanes();
            }
        } else if (d == Direction.down && frogHeight > game.getFrogInitialHeight()) {
            frogHeight--;
            for (Lane l: lanes) l.addOrd(1);
        }
    }
}