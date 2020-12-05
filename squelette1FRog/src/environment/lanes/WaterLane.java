package environment.lanes;

import environment.CaseType;
import environment.SpecialCase;
import environment.elements.Log;
import environment.elements.Obstacle;
import gameCommons.Game;
import graphicalElements.Element;
import util.Case;
import util.Direction;

import java.util.BitSet;

import static gameCommons.Game.waterColor;

public class WaterLane extends Lane {

    /**
     * Crée une ligne d'eau avec des rondis comme comme obstacles
     *
     * @param game        La partie de jeu liée à l'environnement de la voie
     * @param ord         L'ordonnée de la voie
     * @param leftToRight Le sens de circulation des rondins (true = de gauche à droite)
     */
    public WaterLane(Game game, int ord, boolean leftToRight) {
        super(game, ord, waterColor);
        this.leftToRight = leftToRight;
        this.speed = game.randomGen.nextInt(7) + game.minSpeedInTimerLoops;
        this.countdown = speed;
        this.density = (game.randomGen.nextInt(3) + 2) / 100.0f;

        // On retire toutes les cases spéciales à part les bonus
        specialCases.removeIf(s -> s.getType() != CaseType.bonus);
        initialize();
    }

    @Override
    public void update() {
        super.update();
        if (ord == game.getFrog().getRelativePosition().ord && countdown == speed) {
            if (leftToRight) game.getFrog().move(Direction.right);
            else game.getFrog().move(Direction.left);
        }

        for (SpecialCase s : specialCases) game.getGraphic().add(new Element(s.getAbsc(), ord, s.getColor()));
    }

    @Override
    public BitSet isSafe(Case c) {
        BitSet res = super.isSafe(c);
        res.set(0);
        for (Obstacle obs : obstacles) {
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
