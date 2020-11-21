package environment;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;
import util.Direction;

import java.util.ArrayList;

public class EnvInf implements IEnvironment {

    private Game game;
    private int frogHeight;
    private final int initialFrogHeight;
    private ArrayList<Lane> lanes = new ArrayList<>();
    private boolean leftToRight;
    private int nbOfSameWayLanes;

    public EnvInf(Game game){
        this.game = game;
        this.initialFrogHeight = 2;
        this.frogHeight = initialFrogHeight;
        this.leftToRight = false;
        this.nbOfSameWayLanes = 0;

        for (int i = initialFrogHeight + 1; i < 2 * game.height; i++) {
            if(nbOfSameWayLanes == 0) {
                genNbOfSameWayLanes();
                leftToRight = !leftToRight;
            }
            lanes.add(new Lane(game, i, leftToRight));
            nbOfSameWayLanes--;
        }
    }

    private void genNbOfSameWayLanes() {
        double proba = game.randomGen.nextDouble();
        if (proba < 0.25) nbOfSameWayLanes = 1;
        else if(proba < 0.75) nbOfSameWayLanes = 2;
        else nbOfSameWayLanes = 3;
    }

    public boolean isSafe(Case c) {
        // on considere les cases hors de la grille comme safe (la grenouille ne pourra dans tous les cas pas s'y déplacer
        if (frogHeight <= initialFrogHeight || c.absc < 0 || c.absc >= game.width) return true;
        else return lanes.get(frogHeight - (initialFrogHeight + 1)).isSafe(c);
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
            for (Lane l: lanes) l.setOrd(-1);
            while (frogHeight > lanes.size() - game.height) {
                if(nbOfSameWayLanes == 0) {
                    genNbOfSameWayLanes();
                    leftToRight = !leftToRight;
                }
                lanes.add(new Lane(game, lanes.get(lanes.size() - 1).getOrd() + 1, leftToRight));
                nbOfSameWayLanes--;
            }
        } else if (d == Direction.down && frogHeight > 2) {
            frogHeight--;
            for (Lane l: lanes) l.setOrd(1);
        }
    }
}
