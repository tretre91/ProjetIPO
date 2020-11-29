package gameCommons;

import java.awt.Color;
import java.util.BitSet;
import java.util.Random;
import java.lang.System;

import graphicalElements.Element;
import graphicalElements.IFroggerGraphics;
import util.Direction;

public class Game {

    public final Random randomGen = new Random();

    // Caractéristique de la partie
    public final int width;
    public final int height;
    public final int minSpeedInTimerLoops;
    public final double defaultDensity;
    private boolean isOver;
    private long playTime;
    private boolean started;
    private int bonusScore;

    // Lien aux objets utilises
    private IEnvironment environment;
    private IFrog frog;
    private IFroggerGraphics graphic;

    /**
     * @param graphic             l'interface graphique
     * @param width               largeur en cases
     * @param height              hauteur en cases
     * @param minSpeedInTimerLoop Vitesse minimale, en nombre de tour de timer avant déplacement
     * @param defaultDensity      densité de voiture utilisée par défaut pour les routes
     */
    public Game(IFroggerGraphics graphic, int width, int height, int minSpeedInTimerLoop, double defaultDensity) {
        super();
        this.graphic = graphic;
        this.width = width;
        this.height = height;
        this.minSpeedInTimerLoops = minSpeedInTimerLoop;
        this.defaultDensity = defaultDensity;
        this.isOver = false;
        this.started = false;
        this.bonusScore = 0;
    }

    /**
     * Lie l'objet frog à la partie
     *
     * @param frog
     */
    public void setFrog(IFrog frog) {
        this.frog = frog;
    }

    public IFrog getFrog() {
        return frog;
    }

    /**
     * Lie l'objet environment à la partie
     *
     * @param environment
     */
    public void setEnvironment(IEnvironment environment) {
        this.environment = environment;
    }

    /**
     * @return l'interface graphique
     */
    public IFroggerGraphics getGraphic() {
        return graphic;
    }

    /**
     * Teste si la partie est perdue et lance un écran de fin approprié si tel
     * est le cas, s'occupe également de l'interaction avec les cases spéciales
     *
     * @return true si le partie est perdue
     */
    public boolean testLose() {
        BitSet caseStatus = environment.isSafe(frog.getPosition());
        if (caseStatus.get(0) || caseStatus.get(1)) {
            String time = "temps: " + ((System.nanoTime() - playTime) / (long) 1e9) + "s";
            if (frog.getScore() == -1) graphic.endGameScreen("YOU DIED, " + time);
            else graphic.endGameScreen("YOU DIED, score: " + frog.getScore() + "+" + bonusScore + ", " + time);
            isOver = true;
            return true;
        } else if (caseStatus.cardinality() > 0) {
            switch (caseStatus.nextSetBit(2)) {
                case 2:
                    if (!((frog.getDirection() == Direction.left && frog.getPosition().absc == 0)
                            || (frog.getDirection() == Direction.right && frog.getPosition().absc == width - 1))) {
                        frog.move(frog.getDirection());
                    }
                    break;
                case 3:
                    switch (frog.getDirection()) {
                        case up -> frog.move(Direction.down);
                        case down -> frog.move(Direction.up);
                        case left -> frog.move(Direction.right);
                        case right -> frog.move(Direction.left);
                    }
                    break;
                case 4:
                    bonusScore++;
                    break;
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * Teste si la partie est gagnée et lance un écran de fin approprié si tel
     * est le cas
     *
     * @return true si la partie est gagnée
     */
    public boolean testWin() {
        if (environment.isWinningPosition(frog.getPosition())) {
            graphic.endGameScreen("Vous avez traversé la rue en " + ((System.nanoTime() - playTime) / (long) 1e9) + "s");
            isOver = true;
            return true;
        }
        return false;
    }

    /**
     * Indique si la partie est terminée
     *
     * @return true si la partie est terminée (victoire ou défaite)
     */
    public boolean isOver() {
        return isOver;
    }

    /**
     * Actualise l'environnement, affiche la grenouille et vérifie la fin de
     * partie.
     */
    public void update() {
        graphic.clear();
        environment.update();
        this.graphic.add(new Element(frog.getRelativePosition(), Color.GREEN));
        if (!isOver) {
            if (!started) {
                this.playTime = System.nanoTime();
                started = true;
            }
            testLose();
            testWin();
        }
    }


    /* *********** Méthodes nécessaires à la version infinie du jeu *********** */

    /**
     * Déplace toutes les voies de l'environnement
     *
     * @param d La direction du déplacement (left et right n'ont pas d'effet)
     */
    public void moveLanes(Direction d) {
        this.environment.move(d);
    }

    /**
     * Indique l'ordonnée de la case de depart de la grenouille
     *
     * @return l'ordonnée initiale de la grenouille
     */
    public int getFrogInitialHeight() {
        return frog.getInitialHeight();
    }

}
