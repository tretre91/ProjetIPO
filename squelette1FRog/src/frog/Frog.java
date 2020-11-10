package frog;

import gameCommons.Game;
import gameCommons.IFrog;
import util.Case;
import util.Direction;

public class Frog implements IFrog {
	
	private Game game;
	private int x;
	private int y;
	private Direction direction;

	public Frog(Game game){
		this.game = game;
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
				if(x < game.width) x++;
			}
		}
		this.direction = key;
		game.testWin();
		game.testLose();
	}


}
