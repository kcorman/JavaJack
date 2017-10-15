package corm.kenny.jack.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import corm.kenny.jack.dao.GameDao;
import corm.kenny.jack.dao.GameNotFoundException;
import corm.kenny.jack.dao.InvalidBoardException;
import corm.kenny.jack.types.Board;
import corm.kenny.jack.types.Cell;
import corm.kenny.jack.types.CreateBoard;

@Controller
@RequestMapping("/games")
@Component
public class GameController {
	
	@Autowired
	private GameDao gameDao;
	
    @RequestMapping(value="{gameId}", method=RequestMethod.GET)
    @ResponseBody
    Board getBoard(@PathVariable("gameId") String boardId) {
    	Board b = gameDao.getBoard(boardId);
    	if(null == b) {
    		throw new GameNotFoundException();
    	}
    	return b;
    }
    
    @RequestMapping(method=RequestMethod.POST)
    @ResponseBody
    Board createBoard(@RequestBody CreateBoard createBoard) {
    	if(null == createBoard) {
    		throw new InvalidBoardException();
    	}
    	if(createBoard.getBoardSize() < 3 || createBoard.getBoardSize() > 100) {
    		throw new InvalidBoardException();
    	}
    	if(createBoard.getPlayers().size() != 2) {
    		throw new InvalidBoardException();
    	}
    	if(createBoard.getPlayers().stream().distinct().count() != 2) {
    		throw new InvalidBoardException();
    	}
    	Board b = new Board(createBoard.getBoardSize());
    	b.setPlayerIds(createBoard.getPlayers());
    	b.setCurrentTurn(createBoard.getPlayers().get(0));

    	b.setId(UUID.randomUUID().toString());
    	gameDao.saveBoard(b);
    	return b;
    }
    
    @RequestMapping(value="{gameId}/rows/{row}/cols/{col}/playerId/{player}", method=RequestMethod.PUT)
    @ResponseBody
    Board takeTurn(
    		@PathVariable("gameId") String boardId, 
    		@PathVariable("row") int row, 
    		@PathVariable("col") int col, 
    		@PathVariable("player") String playerId) {
    	Board b = gameDao.getBoard(boardId);
    	if(null == b) {
    		throw new GameNotFoundException();
    	}
    	b.takeTurn(row, col, playerId);
    	gameDao.saveBoard(b);
    	return b;
    }
    
    
}
