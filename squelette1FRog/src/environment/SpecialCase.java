package environment;

import java.awt.*;

public class SpecialCase {
    private final int absc;
    private final CaseType type;
    private Color color;

    /**
     * Crée une case spéciale
     * @param type Le type (effet) que possède cette case
     * @param absc L'abscisse de la case (l'ordonnée n'est pas nécessaire car les cases
     *             spéciales sont associées à une voie)
     */
    public SpecialCase(CaseType type, int absc) {
        this.absc = absc;
        this.type = type;
        switch (this.type) {
            case ice -> color = new Color(157, 225,250);
            case trap -> color = Color.red;
            case wall -> color = new Color(61, 61, 61);
            case bonus -> color = Color.orange;
        }
    }

    /**
     * Indique le type d'effet de cette case
     * @return Le type de cette case
     */
    public CaseType getType() {
        return type;
    }

    /**
     * Renvoie la couleur associée à cette case
     * @return La couleur de cette case
     */
    public Color getColor() {
        return color;
    }

    /**
     * renvoie l'abscisse de cette case
     * @return L'abscisse de cette case
     */
    public int getAbsc() {
        return absc;
    }
}
