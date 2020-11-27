package environment;

import java.util.ArrayList;
import java.util.BitSet;

import graphicalElements.Element;
import util.Case;
import gameCommons.Game;

public class Lane {
    private Game game;
    private int yCoord;
    private final int speed;
    private int countdown;
    private final boolean leftToRight;
    private ArrayList<Car> cars = new ArrayList<>();
    private ArrayList<SpecialCase> specialCases = new ArrayList<>();
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
        this.density = (game.randomGen.nextInt(25) + 10) / 100.0f;

        for(int i = 0; i < game.width; i++){
            if(game.randomGen.nextDouble() * 100 < 3.0) {
                int type = game.randomGen.nextInt(10);
                if (type < 1) specialCases.add(new SpecialCase(CaseType.trap, i));
                else if (type < 4) specialCases.add(new SpecialCase(CaseType.ice, i));
                else if (type < 5) specialCases.add(new SpecialCase(CaseType.wall, i));
                else specialCases.add(new SpecialCase(CaseType.bonus, i));
            }
        }

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

        for (SpecialCase s: specialCases) game.getGraphic().add(new Element(s.getAbsc(), yCoord, s.getColor()));
        for (Car c : cars) c.display();
    }

    /**
     * Indique si une case de la voie est sans danger (càd qu'elle ne contient aucun obstacle)
     *
     * @param c La case à tester (elle est supposée de même ordonnée que la voie)
     * @return true si la case contient un obstacle, false sinon
     */
    public BitSet isSafe(Case c) {
        BitSet res = new BitSet(5);
        for (Car car : cars) {
            if (car.overlaps(c)) {
                res.set(0);
                break;
            }
        }

        SpecialCase toRemove = new SpecialCase(CaseType.bonus, -1);
        for (SpecialCase s: specialCases) {
            if (s.getAbsc() == c.absc) {
                switch (s.getType()) {
                    case trap:
                        res.set(1);
                        break;
                    case ice:
                        res.set(2);
                        break;
                    case wall:
                        res.set(3);
                        break;
                    case bonus:
                        res.set(4);
                        toRemove = s;
                        break;
                }
                break;
            }
        }
        if (toRemove.getAbsc() != -1) specialCases.remove(toRemove);

        return res;
    }

    public void setOrd(int ord){
        yCoord += ord;
        for (Car c: cars) c.setOrd(yCoord);
    }

    public int getOrd() {
        return yCoord;
    }

    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Ajoute une voiture au début de la voie avec probabilité égale à la
     * densité, si la première case de la voie est vide
     */
    private void mayAddCar() {
        if (!isSafe(getFirstCase()).get(0) && !isSafe(getBeforeFirstCase()).get(0)) {
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
