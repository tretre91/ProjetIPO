package gameCommons;

import util.Case;
import util.Direction;

import java.util.BitSet;

public interface IEnvironment {

    /**
     * Indique l'état d'une case (si elle est safe et si c'est une case spéciale)
     *
     * @param c La case à tester
     * @return Un BitSet contenant l'état de la case, ses bits correspondent dans
     * l'ordre aux états suivants : pas safe, piège, glace, mur, bonus
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
     *
     * @param d La direction du déplacement (left et right n'ont aucun effet)
     */
    public void move(Direction d);

}
