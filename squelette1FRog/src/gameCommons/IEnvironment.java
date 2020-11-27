package gameCommons;

import util.Case;
import util.Direction;

import java.util.BitSet;

public interface IEnvironment {

	/**
	 * Teste si une case est sure, c'est � dire que la grenouille peut s'y poser
	 * sans mourir
	 * 
	 * @param c
	 *            la case � tester
	 * @return vrai s'il n'y a pas danger
	 */
	public BitSet isSafe(Case c);

	/**
	 * Teste si la case est une case d'arrivee
	 * 
	 * @param c
	 * @return vrai si la case est une case de victoire
	 */
	public boolean isWinningPosition(Case c);

	/**
	 * Effectue une �tape d'actualisation de l'environnement
	 */
	public void update();

	/**
	 * Méthode nécessaire à la version infinie
	 * Déplace les voies de l'environnement vers le haut ou vers le bas
	 * @param d La direction du déplacement (up ou down)
	 */
	public void move(Direction d);

}
