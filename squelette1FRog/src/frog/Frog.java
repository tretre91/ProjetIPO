package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {
	
	private Game game;
	private boolean cantMove;
	private int x;
	private int y;
	private Direction direction;

	public Frog(Game game){
		this.game = game;
		this.cantMove = false;
		this.x = game.width / 2;
		this.y = 0;
		this.direction = Direction.up;
	}

	public Case getPosition(){
		return new Case(x, y);
	}

	public Direction getDirection() {
		return direction;
	}

	public void move(Direction key){
		if(cantMove) return;

		switch (key) {
			case up -> {
				if (y < game.height) y++;
			}
			case down -> {
				if(y > 0) y--;
			}
			case left -> {
				if(x > 0) x--;
			}
			case right -> {
				if(x < game.width - 1) x++;
			}
		}
		this.direction = key;

		if (game.testLose()) cantMove = true;
		else if(game.testWin()) cantMove = true;
	}


}
