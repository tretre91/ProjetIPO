package environment;

import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;
import util.Direction;

import java.util.ArrayList;

public class EnvInfOpt implements IEnvironment {

    private Game game;
    private int frogHeight;
    private final int initialFrogHeight;
    private ArrayList<Lane> lanes = new ArrayList<>();
    private final int maxBufferedLanes;
    private boolean leftToRight;

    public EnvInfOpt(Game game) {
        this.game = game;
        this.initialFrogHeight = 2;
        this.frogHeight = initialFrogHeight;
        this.maxBufferedLanes = 5 * game.height;
        this.leftToRight = false;

        lanes.add(new Lane(game, initialFrogHeight, leftToRight));
        while (lanes.size() < game.height) {
            pushBackLanes();
        }
        lanes.remove(0);
    }

    /**
     * Ajoute entre 1 et 3 voies à la fin de 'lanes' et retire ce même nombre de voies
     * au début si après l'ajout lanes.size() > maxBufferedLanes
     */
    private void pushBackLanes() {
        int nbOfLanes;
        double proba = game.randomGen.nextDouble();
        if (proba < 0.25) nbOfLanes = 1;
        else if (proba < 0.75) nbOfLanes = 2;
        else nbOfLanes = 3;

        int y = lanes.get(lanes.size() - 1).getOrd() + 1;
        for (int i = 0; i < nbOfLanes; i++) lanes.add(new Lane(game, y + i, leftToRight));
        leftToRight = !leftToRight;

        if (lanes.size() > maxBufferedLanes) {
            lanes.subList(0, nbOfLanes).clear();
        }
    }

    /**
     * Ajoute entre 1 et 3 voies au début de 'lanes' et retire ce même nombre de voies
     * à la fin si après l'ajout lanes.size() > maxBufferedLanes
     */
    private void pushFrontLanes() {
        int nbOfLanes;
        double proba = game.randomGen.nextDouble();
        if (proba < 0.25) nbOfLanes = 1;
        else if (proba < 0.75) nbOfLanes = 2;
        else nbOfLanes = 3;

        final int y = lanes.get(0).getOrd() - 1;
        final int delta = frogHeight - initialFrogHeight; // la 1ère ligne vérifie l0.getOrd + delta = initialFrogHeight
        for (int i = 0; i < nbOfLanes && y - i + delta > initialFrogHeight; i++) {
            lanes.add(0, new Lane(game, y - i, leftToRight));
        }
        leftToRight = !leftToRight;

        if (lanes.size() > maxBufferedLanes) {
            lanes.subList(lanes.size() - nbOfLanes, lanes.size()).clear();
        }
    }

    public boolean isSafe(Case c) {
        // on considere les cases hors de la grille comme safe (la grenouille ne pourra dans tous les cas pas s'y déplacer
        if (c.ord < lanes.get(0).getOrd() || c.ord > lanes.get(lanes.size() - 1).getOrd() || c.absc < 0 || c.absc >= game.width)
            return true;
        else return lanes.get(c.ord - lanes.get(0).getOrd()).isSafe(c);
    }

    public boolean isWinningPosition(Case c) {
        return false; // on n'utilise pas cette méthode dans la version infinie, inclue pour des raisons de compatibilité
    }

    public void update() {
        for (Lane l : lanes) l.update();
    }

    public void move(Direction d) {
        if (d == Direction.up) {
            frogHeight++;
            for (Lane l : lanes) l.setOrd(-1);
            while (initialFrogHeight > lanes.get(lanes.size() - 1).getOrd() - game.height) {
                pushBackLanes();
            }
        } else if (d == Direction.down && frogHeight > initialFrogHeight) {
            frogHeight--;
            for (Lane l : lanes) l.setOrd(1);
            if (frogHeight < game.height) {
                pushFrontLanes();
            }
        }
    }

}
