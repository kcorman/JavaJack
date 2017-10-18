package corm.kenny.jack.main;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import corm.kenny.jack.dao.InvalidMatchException;
import corm.kenny.jack.dao.MatchupDao;
import corm.kenny.jack.types.Matchup;

@Controller
@RequestMapping("/matchup")
@Component
@CrossOrigin(origins = "*")
public class MatchupController {
	@Autowired
	private MatchupDao matchupDao;
	
    @RequestMapping(value="", method=RequestMethod.GET)
    @ResponseBody
    List<Matchup> listMatches() {
    	return matchupDao.listMatchups();
    }
    
    @RequestMapping(value="{id}", method=RequestMethod.GET)
    @ResponseBody
    Matchup getMatch(@PathVariable("id") String matchId) {
    	return matchupDao.getMatchupById(matchId);
    }
    
    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteMatch(@PathVariable("id") String matchId) {
    	matchupDao.deleteMatchup(matchId);
    }
    
    @RequestMapping(value="", method=RequestMethod.POST)
    @ResponseBody
    Matchup openMatch(@RequestBody Matchup match) {
    	if(match.getPlayers() == null || match.getWaitingSince() != null) {
    		throw new InvalidMatchException();
    	}
    	match.setWaitingSince(new Date());
    	match.setId(UUID.randomUUID().toString());
    	matchupDao.saveMatchup(match);
    	return match;
    }
    
    @RequestMapping(value="{id}/join/{playerId}", method=RequestMethod.PUT)
    @ResponseBody
    Matchup joinMatch(@PathVariable("id") String matchId, @PathVariable("playerId") String playerId) {
    	Matchup match = matchupDao.getMatchupById(matchId);
    	if(null == match) {
    		throw new InvalidMatchException("No match found");
    	}
    	if(!match.getPlayers().contains(playerId)) {
        	match.getPlayers().add(playerId);
        	matchupDao.saveMatchup(match);
    	}
    	return match;
    }
    
}
