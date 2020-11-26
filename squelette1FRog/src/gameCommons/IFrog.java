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
	 * Donne la direction de la grenouille, c'est � dire de son dernier mouvement 
	 * @return
	 */
	public Direction getDirection();

	public int getScore();
	
	/**
	 * D�place la grenouille dans la direction donn�e et teste la fin de partie
	 * @param key
	 */
	public void move(Direction key);

}
