package frog;

import gameCommons.Game;
import util.Case;
import util.Direction;

public class FrogInf extends Frog { // implements IFrog (from Frog)

    private int maxScore;

    public FrogInf(Game game) {
        super(game);
        this.initialHeight = 2; // L'ordonnée de la grenouille par rapport à la fenêtre reste constante, ce sont les voies qui se déplacent
        this.position = new Case(position.absc, initialHeight);
        this.maxScore = 0;
    }

    @Override
    public Case getRelativePosition() {
        return new Case(position.absc, initialHeight);
    }

    public int getScore() {
        return maxScore;
    }

    @Override
    public void move(Direction key) {
        if (game.isOver()) return;

        switch (key) {
            case up:
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
        game.moveLanes(key);

        game.testLose();
        if (position.ord - initialHeight > maxScore) maxScore = position.ord - initialHeight;
    }
}
