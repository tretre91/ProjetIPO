package graphicalElements;

import gameCommons.IFrog;

public interface IFroggerGraphics {
	
	/**
	 * Ajoute un élément aux éléments à afficher
	 * @param e L'élément à ajouter
	 */
    public void add(Element e);
    
    /**
     * Enlève tous les éléments actuellement affichés
     */
    public void clear();
    
    /***
     * Actualise l'affichage
     */
    public void repaint();
    
    /**
     * Lie une grenouille à l'environnement graphique
     * @param frog La grenouille à lier
     */
    public void setFrog(IFrog frog);
    
    /**
     * Lance un écran de fin de partie
     * @param message le texte à afficher
     */
    public void endGameScreen(String message);
}
