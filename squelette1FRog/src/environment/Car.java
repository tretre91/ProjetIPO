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


    public Car(Game game, int yCoord, boolean leftToRight){
        this.game = game;
        this.length = game.randomGen.nextInt(4) + 1;
        this.leftToRight = leftToRight;

        if(this.leftToRight) this.leftPosition = new Case(-length, yCoord);
        else this.leftPosition = new Case(game.width + length - 1, yCoord);
    }

    public Car(Game game, Case leftPosition, boolean leftToRight) {
        this.game = game;
        this.leftPosition = leftPosition;
        this.leftToRight = leftToRight;
        this.length = game.randomGen.nextInt(4) + 1;
    }

    
    public void move() {
        if (leftToRight) leftPosition = new Case(leftPosition.absc + 1, leftPosition.ord);
        else leftPosition = new Case(leftPosition.absc - 1, leftPosition.ord);
    }

    public boolean isOut() {
        if (leftToRight) return leftPosition.absc >= game.width;
        return leftPosition.absc + length - 1 < 0;
    }

    public boolean overlaps(Case c) {
        if (leftToRight) return c.absc >= leftPosition.absc && c.absc < leftPosition.absc + length;
        else return c.absc <= leftPosition.absc && c.absc > leftPosition.absc - length;
    }

    public void display() {
        addToGraphics();
    }


    /* Fourni : addToGraphics() permettant d'ajouter un element graphique correspondant a la voiture*/
    private void addToGraphics() {
        Color color;
        int dir;
        if (this.leftToRight) {
            color = colorLtR;
            dir = 1;
        } else {
            color = colorRtL;
            dir = -1;
        }

        for (int i = 0; i < length; i++) {
            game.getGraphic().add(new Element(leftPosition.absc + dir * i, leftPosition.ord, color));
        }
    }

}
