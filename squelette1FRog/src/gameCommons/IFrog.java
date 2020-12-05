package gameCommons;

import util.Case;
import util.Direction;

public interface IFrog {

    /**
     * Donne la position actuelle de la grenouille
     *
     * @return La position de la grenouille
     */
    public Case getPosition();

    /**
     * Donne la position de la grenouille par rapport à la zone visible
     *
     * @return La position de la grenouille par rapport à la fenêtre
     */
    public Case getRelativePosition();

    /**
     * Donne la hauteur initiale de la grenouille
     *
     * @return La hauteur à laquelle démarre la grenouille
     */
    public int getInitialHeight();

    /**
     * Donne la direction de la grenouille, c'est à dire de son dernier mouvement
     *
     * @return La direction de cette grenouille
     */
    public Direction getDirection();

    /**
     * Renvoie le score (hauteur maximale atteinte par la grenouille)
     *
     * @return Le score de cette grenouille
     */
    public int getScore();

    /**
     * Déplace la grenouille dans une direction donnée et teste la fin de partie
     *
     * @param key La direction du déplacement
     */
    public void move(Direction key);

}
