package gameCommons;

import util.Case;
import util.Direction;

import java.util.BitSet;

public interface IEnvironment {

	/**
	 * Teste si une case est sure, c'est à dire que la grenouille peut s'y poser
	 * sans mourir
	 * 
	 * @param c La case à tester
	 * @return Un BitSet contenant l'état de la case, ses bits correspondent dans
	 * l'ordre aux états suivants : pas safe, piege, glace, mur, bonus
	 */
	public BitSet isSafe(Case c);

	/**
	 * Teste si la case est une case d'arrivée
	 * 
	 * @param c La case à tester
	 * @return vrai si la case est une case de victoire
	 */
	public boolean isWinningPosition(Case c);

	/**
	 * Effectue une étape d'actualisation de l'environnement
	 */
	public void update();

	/**
	 * Méthode nécessaire à la version infinie
	 * Déplace les voies de l'environnement vers le haut ou vers le bas
	 * @param d La direction du déplacement (up ou down)
	 */
	public void move(Direction d);

}
