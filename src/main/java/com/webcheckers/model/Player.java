package com.webcheckers.model;

public class Player {
	
	private final String name;

	private boolean game = false;
	
	public Player(String name) {
		this.name = name;
	}

	public boolean hasGame() { return game; }

	public boolean giveGame() { return game = true;}

	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Player)) return false;
		Player player = (Player) o;
		return name.equals(player.name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
