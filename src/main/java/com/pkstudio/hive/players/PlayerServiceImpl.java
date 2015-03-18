package com.pkstudio.hive.players;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

	private PlayerDao playerDao;
	
	@Inject
	public PlayerServiceImpl(PlayerDao playerDao) {
		this.playerDao = playerDao;
	}
	
	@Override
	public PlayerDto createPlayer(PlayerDto playerDto) {
		playerDto.validate();
		Player player = new Player(playerDto);
		playerDao.save(player);
		return new PlayerDto();
	}

}
