package corm.kenny.jack.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import corm.kenny.jack.types.Matchup;

@Component
public class MatchupDaoImpl implements MatchupDao {
	private Map<String, Matchup> matches = new ConcurrentHashMap<>();

	@Override
	public void saveMatchup(Matchup matchup) {
		matches.put(matchup.getId(), matchup);
	}

	@Override
	public void deleteMatchup(String id) {
		matches.remove(id);
	}

	@Override
	public List<Matchup> listMatchups() {
		return new ArrayList<>(matches.values());
	}

	@Override
	public Matchup getMatchupById(String id) {
		return matches.get(id);
	}
}
