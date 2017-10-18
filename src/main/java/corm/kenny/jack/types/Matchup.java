package corm.kenny.jack.types;

import java.util.Date;
import java.util.List;

public class Matchup {
	private String id;
	private List<String> players;
	private Date waitingSince;
	private boolean allowBots;
	private int boardSize;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<String> getPlayers() {
		return players;
	}
	public void setPlayers(List<String> waitingPlayers) {
		this.players = waitingPlayers;
	}
	public Date getWaitingSince() {
		return waitingSince;
	}
	public void setWaitingSince(Date waitingSince) {
		this.waitingSince = waitingSince;
	}
	public boolean isAllowBots() {
		return allowBots;
	}
	public void setAllowBots(boolean allowBots) {
		this.allowBots = allowBots;
	}
	public int getBoardSize() {
		return boardSize;
	}
	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}
}
