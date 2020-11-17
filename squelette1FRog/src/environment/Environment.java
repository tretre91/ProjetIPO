package environment;

import java.util.ArrayList;

import util.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;

public class Environment implements IEnvironment {

    private Game game;
    private ArrayList<Lane> lanes;

    /**
     * Crée un environnement
     *
     * @param game Une partie de jeu
     */
    public Environment(Game game) {
        this.game = game;
        this.lanes = new ArrayList<>();
        boolean leftToRight = false;

        int counter = 1;
        while(counter < game.height-1){
            double proba = game.randomGen.nextDouble();
            int nbOfLanes;
            if (proba < 0.25) nbOfLanes = 1;
            else if(proba < 0.75) nbOfLanes = 2;
            else nbOfLanes = 3;

            for(int i = 0; i < nbOfLanes; i++){
                lanes.add(new Lane(game, counter, leftToRight));
                counter++;
                if(counter == game.height - 1) break;
            }
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
