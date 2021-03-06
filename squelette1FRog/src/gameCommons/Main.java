package gameCommons;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import environment.env.*;
import frog.*;
import graphicalElements.FroggerGraphic;
import graphicalElements.IFroggerGraphics;
import graphicalElements.MyFroggerGraphic;

public class Main {

	public static void main(String[] args) {

		//Caractéristiques du jeu
		int width = 26;
		int height = 20;
		int tempo = 100;
		int minSpeedInTimerLoops = 3;
		double defaultDensity = 0.2;
		
		//Création de l'interface graphique
		IFroggerGraphics graphic = new MyFroggerGraphic(width, height);
		//Création de la partie
		Game game = new Game(graphic, width, height, minSpeedInTimerLoops, defaultDensity);
		//Création et liaison de la grenouille
		IFrog frog = new FrogInf(game);
		game.setFrog(frog);
		graphic.setFrog(frog);
		//Création et liaison de l'environnement
		IEnvironment env = new EnvInf(game);
		game.setEnvironment(env);
				
		//Boucle principale : l'environnement s'actualise tous les tempo millisecondes
		Timer timer = new Timer(tempo, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.update();
				graphic.repaint();
			}
		});
		timer.setInitialDelay(0);
		timer.start();
	}
}
