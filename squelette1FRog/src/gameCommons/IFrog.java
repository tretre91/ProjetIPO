package gameCommons;

import util.Case;
import util.Direction;

public interface IFrog {
	
	/**
	 * Donne la position actuelle de la grenouille
	 * @return
	 */
	public Case getPosition();

	/**
	 * Donne la position de la grenouille par rapport à la zone visible
	 * @return
	 */
	public Case getRelativePosition();

	/**
	 * Donne la hauteur initiale de la grenouille
	 * @return
	 */
	public int getInitialHeight();
	
	/**
	 * Donne la direction de la grenouille, c'est à dire de son dernier mouvement
	 * @return
	 */
	public Direction getDirection();

	/**
	 * Renvoie le score (hauteur maximale atteinte de la grenouille)
	 * @return
	 */
	public int getScore();
	
	/**
	 * Déplace la grenouille dans la direction donnée et teste la fin de partie
	 * @param key
	 */
	public void move(Direction key);

}
