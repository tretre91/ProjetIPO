package environment;

import java.util.ArrayList;

import util.Case;
import gameCommons.Game;

public class Lane {
    private Game game;
    private final int yCoord;
    private final int speed;
    private int countdown;
    private final boolean leftToRight;
    private ArrayList<Car> cars = new ArrayList<>();
    private final double density;


    public Lane(Game game, int yCoord, boolean leftToRight) {
        this.game = game;
        this.yCoord = yCoord;
        this.leftToRight = leftToRight;
        this.speed = game.randomGen.nextInt(5) + game.minSpeedInTimerLoops;
        this.countdown = speed;
        this.density = (game.randomGen.nextInt(70) + 5) / 100.0f;
    }

    public void update() {
        // Toutes les voitures se d�placent d'une case au bout d'un nombre "tic
        // d'horloge" �gal � leur vitesse
        // Notez que cette m�thode est appel�e � chaque tic d'horloge

        // Les voitures doivent etre ajoutes a l interface graphique meme quand
        // elle ne bougent pas

        // A chaque tic d'horloge, une voiture peut �tre ajout�e
        int out = -1;
        countdown--;

        if (countdown == 0) {
            for (int i = 0; i < cars.size(); i++) {
                cars.get(i).move();
                if (cars.get(i).isOut()) out = i;
            }

            if(out != -1) cars.remove(out); // si une voiture est sortie de la zone de jeu, on la retire de 'cars'
            countdown = speed;
            mayAddCar();
        }

        for (Car c : cars) c.display();
    }

    public boolean isSafe(Case c) {
        for (Car car : cars){
            if (car.overlaps(c)) return false;
        }
        return true;
    }


    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Ajoute une voiture au d�but de la voie avec probabilit� �gale � la
     * densit�, si la premi�re case de la voie est vide
     */
    private void mayAddCar() {
        if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
            if (game.randomGen.nextDouble() < density) {
                cars.add(new Car(game, yCoord, leftToRight));
            }
        }
    }

    private Case getFirstCase() {
        if (leftToRight) {
            return new Case(0, yCoord);
        } else
            return new Case(game.width - 1, yCoord);
    }

    private Case getBeforeFirstCase() {
        if (leftToRight) {
            return new Case(-1, yCoord);
        } else {
            return new Case(game.width, yCoord);
        }
    }

}
