package environment.lanes;

import environment.CaseType;
import environment.SpecialCase;
import environment.elements.Car;
import environment.elements.Obstacle;
import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.util.BitSet;

public class CarLane extends Lane {

    /**
     * Crée une ligne de route avec des voitures comme obstacles
     *
     * @param game        La partie de jeu liée à l'environnement de la voie
     * @param ord         L'ordonnée de la voie
     * @param leftToRight Le sens de circulation des voitures (true = de gauche à droite)
     */
    public CarLane(Game game, int ord, boolean leftToRight) {
        super(game, ord);
        this.leftToRight = leftToRight;
        this.speed = game.randomGen.nextInt(7) + 2;
        this.countdown = speed;
        this.density = (game.randomGen.nextInt(2) + 3) / 100.0f;

        initialize();
    }

    @Override
    public void update() {
        super.update();
        for (SpecialCase s : specialCases)
            if (s.getType() == CaseType.wall) game.getGraphic().add(new Element(s.getAbsc(), ord, s.getColor()));
    }

    @Override
    public BitSet isSafe(Case c) {
        BitSet res = super.isSafe(c);
        for (Obstacle obs : obstacles) {
            if (obs.overlaps(c)) {
                res.set(0);
                break;
            }
        }
        return res;
    }

    @Override
    protected void mayAddObstacle() {
        if (!isSafe(getFirstCase()).get(0) && !isSafe(getBeforeFirstCase()).get(0)) {
            if (game.randomGen.nextDouble() < density) {
                obstacles.add(new Car(game, ord, leftToRight));
            }
        }
    }

}


