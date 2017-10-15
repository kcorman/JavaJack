import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import corm.kenny.jack.types.Board;

public class BoardTest {
	static final String p1 = "p1";
	static final String p2 = "p2";
	
	@Test
	public void gameNotFinishedByDefaultTest() {
		Board b = createBoard();
		b.checkForWin();
		assertNull(b.getWinner());
	}
	
	@Test
	public void fiveInARowWinsTest() {
		Board b = createBoard();
		
		for(int i = 0;i<5;i++) {
			b.takeTurn(i, 0, p1);
			if(i != 4) 
				b.takeTurn(i, 1, p2);
		}
		assertEquals(p1, b.getWinner());
	}
	
	@Test
	public void fiveDiagonalWins() {
		Board b = createBoard();
		
		for(int i = 0;i<5;i++) {
			b.takeTurn(i, i, p1);
			if(i != 4) 
				b.takeTurn(i + 2, i, p2);
		}
		assertEquals(p1, b.getWinner());
	}
	
	@Test
	public void p1LeavesGapsP2Doesnt() {
		Board b = createBoard();
		b.takeTurn(0, 0, p1);
		b.takeTurn(0, 1, p2);
		b.takeTurn(1, 0, p1);
		b.takeTurn(1, 1, p2);
		b.takeTurn(2, 0, p1);
		b.takeTurn(2, 1, p2);
		b.takeTurn(4, 0, p1);
		b.takeTurn(3, 1, p2);
		b.takeTurn(5, 0, p1);
		assertNull(b.getWinner());
		b.takeTurn(4, 1, p2);
		assertEquals(p2, b.getWinner());
	}
	
	@Test
	public void p2ReverseDiagonal() {
		Board b = createBoard();
		b.takeTurn(0, 0, p1);
		b.takeTurn(0, 9, p2);
		b.takeTurn(1, 0, p1);
		b.takeTurn(1, 8, p2);
		b.takeTurn(2, 0, p1);
		b.takeTurn(2, 7, p2);
		b.takeTurn(4, 0, p1);
		b.takeTurn(3, 6, p2);
		b.takeTurn(5, 0, p1);
		assertNull(b.getWinner());
		b.takeTurn(4, 5, p2);
		assertEquals(p2, b.getWinner());
	}
	
	@Test
	public void p1Diagonal() {
		Board b = createBoard();
		b.takeTurn(5, 5, p1);
		b.takeTurn(0, 0, p2);
		b.takeTurn(6, 6, p1);
		b.takeTurn(1, 0, p2);
		b.takeTurn(7, 7, p1);
		b.takeTurn(2, 0, p2);
		b.takeTurn(8, 8, p1);
		b.takeTurn(3, 0, p2);
		b.takeTurn(9, 9, p1);
		assertEquals(p1, b.getWinner());
	}
	
	private static Board createBoard() {
		Board b = new Board(10);
		b.setPlayerIds(Arrays.asList(p1, p2));
		b.setCurrentTurn(p1);
		return b;
	}
}
