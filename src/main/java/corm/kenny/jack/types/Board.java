package corm.kenny.jack.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import corm.kenny.jack.dao.InvalidMoveException;

public class Board {
	// ID of the board
	private String id;
	// the name of the board 
	private String name;
	
	// The size of the board (squared)
	private int boardSize;
	// Player ID of the player that won this game (null if game is in progress)
	private String winner;
	// Player ID of the player whose turn it is
	private String currentTurn;

	private List<String> playerIds = new ArrayList<>();
	private List<List<Cell>> gameCells;

	private Board() {
		
	}
	
	public Board(int size) {
		this.boardSize = size;
    	List<List<Cell>> cells = new ArrayList<>();
    	for(int i = 0;i<size;i++) {
    		List<Cell> row = new ArrayList<>();
    		for(int k = 0;k<size;k++) {
    			row.add(new Cell());
    		}
    		cells.add(row);
    	}
    	this.gameCells = cells;
	}
	
	public void takeTurn(int row, int col, String playerId) {
		if(!currentTurn.equals(playerId)) {
			throw new InvalidMoveException("It is not " + playerId + "'s turn!");
		}
		if(row < 0 || col < 0 || row >= boardSize || col >= boardSize) {
			throw new InvalidMoveException("Move outside of board bounds");
		}
		Cell cell = gameCells.get(row).get(col);
		if(cell.getOwner() != null) {
			throw new InvalidMoveException("Cell is already occupied");
		}
		cell.setOwner(playerId);
		currentTurn = playerIds.stream().filter(s -> !s.equals(playerId)).findFirst().get();
		checkForWin();
	}
	
	/**
	 * Checks to see if the game is now in a 'win' state, and populates the winner field if it is.
	 */
	public void checkForWin() {
		class Tally {
			int[] counts = new int[4]; //top-right, top, top-left, left
		}
		for(String player : getPlayerIds()) {
			Map<Cell, Tally> tallies = new HashMap<>();
			for(int i = 0;i<boardSize;i++) {
				for(int k = 0;k<boardSize;k++) {
					Cell cell = gameCells.get(i).get(k);
					Tally t = new Tally();
					tallies.put(cell, t);
					int spaceVal = player.equals(cell.getOwner()) ? 1 : 0;
					if(spaceVal == 1) {
						t.counts[0] = spaceVal + 
								(i == 0 || k == boardSize-1 ? 
										0 : tallies.get(gameCells.get(i-1).get(k+1)).counts[0]);
						t.counts[1] = spaceVal + 
								(i == 0 ? 
										0 : tallies.get(gameCells.get(i-1).get(k)).counts[1]);
						t.counts[2] = spaceVal + 
								(i == 0 || k == 0 ? 
										0 : tallies.get(gameCells.get(i-1).get(k-1)).counts[2]);
						t.counts[3] = spaceVal + 
								(k == 0 ? 
										0 : tallies.get(gameCells.get(i).get(k-1)).counts[3]);
					} // else leave counts as 0

					for(int check = 0;check<4;check++) {
						if(t.counts[check] >= 5) {
							winner = player;
							System.out.println("Found winner: " + player);
							
						}
					}
				}
			}
		}
	}

	
	public int getBoardSize() {
		return boardSize;
	}
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

	public List<String> getPlayerIds() {
		return playerIds;
	}

	public void setPlayerIds(List<String> playerIds) {
		this.playerIds = playerIds;
	}

	public List<List<Cell>> getGameCells() {
		return gameCells;
	}

	public void setGameCells(List<List<Cell>> gameCells) {
		this.gameCells = gameCells;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(String currentTurn) {
		this.currentTurn = currentTurn;
	}
}
