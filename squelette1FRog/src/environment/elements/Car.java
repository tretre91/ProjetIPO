package environment.elements;

import java.awt.Color;

import util.Case;
import gameCommons.Game;

public class Car extends Obstacle {

    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;

    /**
     * Crée une nouvelle voiture positionnée avant le début d'une voie
     *
     * @param game        La partie de jeu liée à l'environnement de la voiture
     * @param ord         L'ordonnée de la voie sur laquelle se situera la voiture
     * @param leftToRight Le sens de circulation (true si la voiture va de gauche à droite, false sinon)
     */
    public Car(Game game, int ord, boolean leftToRight) {
        super(game, ord, leftToRight, 1, 3);
    }

    /**
     * Affiche une voiture à l'écran
     */
    public void display() {
        if (leftToRight) super.addToGraphics(colorLtR);
        else super.addToGraphics(colorRtL);
    }

}
