package environment;

import java.awt.Color;

import util.Case;
import gameCommons.Game;
import graphicalElements.Element;

public class Car {
    private Game game;
    private Case leftPosition; // la position de l'extremite gauche de la voiture
    private boolean leftToRight; // son sens de déplacement
    private final int length; // la longueur de la voiture
    private final Color colorLtR = Color.BLACK;
    private final Color colorRtL = Color.BLUE;

    /**
     * Crée une nouvelle voiture positionnée avant le début d'une voie
     *
     * @param game        La partie de jeu liée à l'nvironnement de la voiture
     * @param yCoord      L'ordonnée de la voie sur laquelle se situera la voiture
     * @param leftToRight Le sens de circulation (true si la voiture va de gauche à droite, false sinon)
     */
    public Car(Game game, int yCoord, boolean leftToRight) {
        this.game = game;
        this.length = game.randomGen.nextInt(3) + 1;
        this.leftToRight = leftToRight;

        if (this.leftToRight) this.leftPosition = new Case(-length, yCoord);
        else this.leftPosition = new Case(game.width + length - 1, yCoord);
    }

    /**
     * Crée une nouvelle voiture à une position arbitraire
     *
     * @param game         La partie de jeu liée à l'environnement de la voiture
     * @param leftPosition La case qui contiendra l'extremité gauche de la voiture
     * @param leftToRight  Le sens de ciirculation (true si la voiture va de gauche à droite, false sinon)
     */
    public Car(Game game, Case leftPosition, boolean leftToRight) {
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;
        this.length = game.randomGen.nextInt(3) + 1;
    }

    /**
     * Déplace une voiture d'une case dans son sens de circulation
     */
    public void move() {
        if (leftToRight) leftPosition = new Case(leftPosition.absc + 1, leftPosition.ord);
        else leftPosition = new Case(leftPosition.absc - 1, leftPosition.ord);
    }

    /**
     * Indique si une voiture est sortie de la zone de jeu (si tous ses blocs ne sont plus visibles)
     *
     * @return true si tous les blocs composants la voiture sont hors de l'écran, false sinon
     */
    public boolean isOut() {
        if (leftToRight) return leftPosition.absc >= game.width;
        return leftPosition.absc + length - 1 < 0;
    }

    /**
     * Indique si une voiture est sur une case (on suppose que la case a la même ordonnée que la voiture)
     *
     * @param c La case à tester
     * @return true si la voiture est sur c, false sinon
     */
    public boolean overlaps(Case c) {
        if (leftToRight) return c.absc >= leftPosition.absc && c.absc < leftPosition.absc + length;
        else return c.absc <= leftPosition.absc && c.absc > leftPosition.absc - length;
    }

    /**
     * Affiche une voiture à l'écran
     */
    public void display() {
        addToGraphics();
    }


    /* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
    private void addToGraphics() {
        Color color = leftToRight ? colorLtR : colorRtL;
        int dir = leftToRight ? 1 : -1;

        for (int i = 0; i < length; i++) {
            game.getGraphic().add(new Element(leftPosition.absc + dir * i, leftPosition.ord, color));
        }
    }

}
