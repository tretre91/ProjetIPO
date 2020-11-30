package environment.lanes;

import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;

import environment.CaseType;
import environment.SpecialCase;
import environment.elements.Obstacle;
import graphicalElements.Element;
import util.Case;
import gameCommons.Game;

public class Lane {
    protected Game game;
    protected int ord;
    protected ArrayList<Obstacle> obstacles = new ArrayList<>();
    protected int speed;
    protected int countdown;
    protected boolean leftToRight;
    protected double density;
    protected ArrayList<SpecialCase> specialCases = new ArrayList<>();
    protected Color bacgroundColor = null;

    /**
     * Crée une voie
     *
     * @param game La partie de jeu liée à l'environnement de la voie
     * @param ord  L'ordonnée de la voie
     */
    public Lane(Game game, int ord) {
        this.game = game;
        this.ord = ord;

        for (int i = 0; i < game.width; i++) {
            if (game.randomGen.nextDouble() * 100 < 3.0) {
                int type = game.randomGen.nextInt(10);
                if (type < 1) specialCases.add(new SpecialCase(CaseType.trap, i));
                else if (type < 4) specialCases.add(new SpecialCase(CaseType.ice, i));
                else if (type < 5) specialCases.add(new SpecialCase(CaseType.wall, i));
                else specialCases.add(new SpecialCase(CaseType.bonus, i));
            }
        }
    }

    public Lane(Game game, int ord, Color bacgroundColor){
        this(game, ord);
        this.bacgroundColor = bacgroundColor;
    }

    /**
     * Ajoute des voitures selon la densité de la voie et sans les afficher
     */
    protected void initialize() {
        for (int i = 0; i < game.width; i++) {
            for (Obstacle o : obstacles) o.move();
            mayAddObstacle();
        }
    }

    /**
     * Affiche une voie
     */
    public void update() {
        // affichage de l'arrière plan
        if (bacgroundColor != null) {
            for (int i = 0; i < game.width; i++)
                game.getGraphic().add(new Element(i, ord, bacgroundColor));
        }

        // Toutes les voitures se déplacent d'une case au bout d'un nombre "tic
        // d'horloge" égal à leur vitesse
        // Notez que cette méthode est appelée à chaque tic d'horloge

        // Les voitures doivent être ajoutes a l interface graphique meme quand
        // elle ne bougent pas

        // A chaque tic d'horloge, une voiture peut être ajoutée
        int out = -1;
        countdown--;

        if (countdown == 0) {
            for (int i = 0; i < obstacles.size(); i++) {
                obstacles.get(i).move();
                if (obstacles.get(i).isOut()) out = i;
            }

            if (out != -1) obstacles.remove(out); // si un obstacle est sorti de la zone de jeu, on la retire de 'obstacles'
            countdown = speed;
        }
        mayAddObstacle();
        for (SpecialCase s : specialCases) game.getGraphic().add(new Element(s.getAbsc(), ord, s.getColor()));
        for (Obstacle o: obstacles) o.display();
    }

    /**
     * Indique si une case de la voie est sans danger (càd que la grenouille peut s'y poser
     * sans danger
     *
     * @param c La case à tester (elle est supposée de même ordonnée que la voie)
     * @return Un BitSet correspondant à l'état de la case (pas safe, piège, glace, mur et bonus)
     */
    public BitSet isSafe(Case c) {
        BitSet res = new BitSet(5);

        SpecialCase toRemove = new SpecialCase(CaseType.bonus, -1);
        for (SpecialCase s : specialCases) {
            if (s.getAbsc() == c.absc) {
                switch (s.getType()) {
                    case trap -> res.set(1);
                    case ice -> res.set(2);
                    case wall -> res.set(3);
                    case bonus -> {
                        res.set(4);
                        toRemove = s;
                    }
                }
                break;
            }
        }
        if (toRemove.getAbsc() != -1) specialCases.remove(toRemove);

        return res;
    }

    /**
     * Ajoute une certaine valuer à l'ordonnée de la voie
     *
     * @param ord La valeur à ajouter (ou à retirer si elle est négative)
     */
    public void addOrd(int ord) {
        this.ord += ord;
        for (Obstacle o: obstacles) o.setOrd(this.ord);
    }

    /**
     * Renvoie l'ordonnée (relative à la fenêtre) de cette voie
     *
     * @return
     */
    public int getOrd() {
        return ord;
    }

    /*
     * Fourni : mayAddCar(), getFirstCase() et getBeforeFirstCase()
     */

    /**
     * Ajoute un obstacle au début de la voie avec probabilité égale à la densité si la première case
     * de la voie est vide, cette méthode est redéfinie dans les classes filles (cela permet de créer des
     * voies vides grâce à la classe Lane)
     */
    protected void mayAddObstacle() { }

    /**
     * Donne la première case de la voie
     *
     * @return la première case de la voie
     */
    protected Case getFirstCase() {
        if (leftToRight) return new Case(0, ord);
        else return new Case(game.width - 1, ord);
    }

    /**
     * Donne la case avant la première case de la voie
     *
     * @return la case précédant la première case de la voie
     */
    protected Case getBeforeFirstCase() {
        if (leftToRight) return new Case(-1, ord);
        else return new Case(game.width, ord);
    }
}
