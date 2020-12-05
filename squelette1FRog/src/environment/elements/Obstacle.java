package environment.elements;

import gameCommons.Game;
import graphicalElements.Element;
import util.Case;

import java.awt.*;

public abstract class Obstacle {

    protected Game game;
    protected Case position; // position de la dernière case de l'obstacle (case la plus à gauche si il va de gauche à droite, à droite sinon)
    protected boolean leftToRight;
    protected int length;

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

        if (this.leftToRight) this.position = new Case(-length, ord);
        else this.position = new Case(game.width + length - 1, ord);
    }

    /**
     * Déplace l'obstacle d'une case dans son sens de circulation
     */
    public void move() {
        if (leftToRight) position = new Case(position.absc + 1, position.ord);
        else position = new Case(position.absc - 1, position.ord);
    }

    /**
     * Indique si l'obstacle est sorti de la zone de jeu (si tous ses blocs
     * ne sont plus visibles)
     *
     * @return true si tous les blocs composants la voiture sont hors de l'écran, false sinon
     */
    public boolean isOut() {
        if (leftToRight) return position.absc >= game.width;
        return position.absc + length - 1 < 0;
    }

    /**
     * Indique si l'obstacle est sur une case (on suppose que la case
     * a la même ordonnée que l'obstacle)
     *
     * @param c La case à tester
     * @return true si l'obstacle est sur c, false sinon
     */
    public boolean overlaps(Case c) {
        if (leftToRight) return c.absc >= position.absc && c.absc < position.absc + length;
        else return c.absc <= position.absc && c.absc > position.absc - length;
    }

    /**
     * Ajoute cet obstacle à l'interface graphique associée à 'game'
     */
    public abstract void display();

    /**
     * Ajoute cet obstacle à l'interface graphique avec une couleur donnée
      * @param color La couleur de l'obstacle
     */
    protected void addToGraphics(Color color) {
        int dir = leftToRight ? 1 : -1;

        for (int i = 0; i < length; i++) {
            game.getGraphic().add(new Element(position.absc + dir * i, position.ord, color));
        }
    }

    /**
     * Change l'ordonnée de l'obstacle
     * @param ord La nouvelle ordonnée
     */
    public void setOrd(int ord){
        position = new Case(position.absc, ord);
    }

}
