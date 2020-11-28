package environment.elements;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public abstract class Obstacle {

    protected Game game;
    protected Case leftPosition; // la position de l'extrémité gauche de l'obstacle
    protected boolean leftToRight; // son sens de déplacement
    protected int length; // la longueur de la voiture

    /**
     * Constructeur utilisé pour la création d'un obstacle, les champs leftPosition
     * et length doivent être initialisés dans les constructeurs des classes filles
     *
     * @param game Une partie de jeu
     * @param leftToRight Le sens de circulation de l'obstacle (true si il va
     *                    de gauche à droite, false sinon)
     */
    public Obstacle(Game game, int ord, boolean leftToRight, int minLength, int maxLength) {
        this.game = game;
        this.leftToRight = leftToRight;
        this.length = game.randomGen.nextInt(maxLength) + minLength;

        if (this.leftToRight) this.leftPosition = new Case(-length, ord);
        else this.leftPosition = new Case(game.width + length - 1, ord);
    }

    /**
     * Déplace l'obstacle d'une case dans son sens de circulation
     */
    public void move() {
        if (leftToRight) leftPosition = new Case(leftPosition.absc + 1, leftPosition.ord);
        else leftPosition = new Case(leftPosition.absc - 1, leftPosition.ord);
    }

    /**
     * Indique si l'obstacle est sorti de la zone de jeu (si tous ses blocs
     * ne sont plus visibles)
     *
     * @return true si tous les blocs composants la voiture sont hors de l'écran, false sinon
     */
    public boolean isOut() {
        if (leftToRight) return leftPosition.absc >= game.width;
        return leftPosition.absc + length - 1 < 0;
    }

    /**
     * Indique si l'obstacle est sur une case (on suppose que la case
     * a la même ordonnée que l'obstacle)
     *
     * @param c La case à tester
     * @return true si l'obstacle est sur c, false sinon
     */
    public boolean overlaps(Case c) {
        if (leftToRight) return c.absc >= leftPosition.absc && c.absc < leftPosition.absc + length;
        else return c.absc <= leftPosition.absc && c.absc > leftPosition.absc - length;
    }

    public abstract void display();

    protected void addToGraphics(Color color) {
        int dir = leftToRight ? 1 : -1;

        for (int i = 0; i < length; i++) {
            game.getGraphic().add(new Element(leftPosition.absc + dir * i, leftPosition.ord, color));
        }
    }

    /**
     * Change l'ordonnée de l'obstacle
     * @param ord La nouvelle ordonnée
     */
    public void setOrd(int ord){
        leftPosition = new Case(leftPosition.absc, ord);
    }

}
