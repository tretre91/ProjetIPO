package environment.elements;

import gameCommons.Game;

import java.awt.*;

public class Log extends Obstacle {

    private final Color color = new Color(123, 70, 34);

    /**
     * Crée nouveau rondin positionnée avant le début d'une voie
     *
     * @param game        La partie de jeu liée à l'environnement de la voiture
     * @param ord         L'ordonnée de la voie sur laquelle se situera le rondin
     * @param leftToRight Le sens de circulation (true si le rondin va de gauche à droite, false sinon)
     */
    public Log(Game game, int ord, boolean leftToRight) {
        super(game, ord, leftToRight, 2, 5);
    }

    /**
     * Affiche un rondin à l'écran
     */
    public void display() {
        super.addToGraphics(color);
    }
}
