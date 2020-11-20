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


    /**
     * Crée une voie
     *
     * @param game        La partie de jeu liée à l'environnement de la voie
     * @param yCoord      L'ordonnée de la voie
     * @param leftToRight Le sens de circualtion (true si gauche-droite, false sinon)
     */
    public Lane(Game game, int yCoord, boolean leftToRight) {
        this.game = game;
        this.yCoord = yCoord;
        this.leftToRight = leftToRight;
        this.speed = game.randomGen.nextInt(7) + game.minSpeedInTimerLoops;
        this.countdown = speed;
        this.density = (game.randomGen.nextInt(35) + 10) / 100.0f;

        initialize();
    }

    /**
     * Ajoute des voitures selon la densité de la voie et sans les afficher
     */
    private void initialize() {
        for(int i = 0; i < game.width; i++){
            for (Car c: cars) c.move();
            mayAddCar();
        }
    }

    /**
     * Mets à jour une voie (déplace les voitures, en ajoute éventuellement une, et affiche les voitures de la voie)
     */
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

            if (out != -1) cars.remove(out); // si une voiture est sortie de la zone de jeu, on la retire de 'cars'
            countdown = speed;
            mayAddCar();
        }

        for (Car c : cars) c.display();
    }

    /**
     * Indique si une case de la voie est sans danger (càd qu'elle ne contient aucun obstacle)
     *
     * @param c La case à tester (elle est supposée de même ordonnée que la voie)
     * @return true si la case contient un obstacle, false sinon
     */
    public boolean isSafe(Case c) {
        for (Car car : cars) {
            if (car.overlaps(c)) return false;
        }
        return true;
    }


    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Ajoute une voiture au début de la voie avec probabilité égale à la
     * densité, si la première case de la voie est vide
     */
    private void mayAddCar() {
        if (isSafe(getFirstCase()) && isSafe(getBeforeFirstCase())) {
            if (game.randomGen.nextDouble() < density) {
                cars.add(new Car(game, yCoord, leftToRight));
            }
        }
    }

    /**
     * Donne la première case de la voie
     *
     * @return la première case de la voie
     */
    private Case getFirstCase() {
        if (leftToRight) return new Case(0, yCoord);
        else return new Case(game.width - 1, yCoord);
    }

    /**
     * Donne la case avant la première case de la voie
     *
     * @return la case précédant la première case de la voie
     */
    private Case getBeforeFirstCase() {
        if (leftToRight) return new Case(-1, yCoord);
        else return new Case(game.width, yCoord);
    }

}
