package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {

    protected Game game;
    protected Case position;
    protected int initialHeight;
    protected Direction direction;

    /**
     * Crée une grenouille qui doit être utilisée avec un environnement classique
     *
     * @param game La partie de jeu qui utilisera cette grenouille
     */
    public Frog(Game game) {
        this.game = game;
        this.initialHeight = 0;
        this.position = new Case(game.width / 2, initialHeight);
        this.direction = Direction.up;
    }

    public Case getPosition() {
        return position;
    }

    public Case getRelativePosition() {
        return position;
    }

    public int getInitialHeight() {
        return initialHeight;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getScore() {
        return -1;
    }

    public void move(Direction key) {
        if (game.isOver()) return;

        switch (key) {
            case up:
                if (position.ord < game.height)
                    position = new Case(position.absc, position.ord + 1);
                break;
            case down:
                if (position.ord > initialHeight)
                    position = new Case(position.absc, position.ord - 1);
                break;
            case left:
                if (position.absc > 0)
                    position = new Case(position.absc - 1, position.ord);
                break;
            case right:
                if (position.absc < game.width - 1)
                    position = new Case(position.absc + 1, position.ord);
                break;
        }
        this.direction = key;

        game.testLose();
        game.testWin();
    }


}
