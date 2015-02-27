package com.pkstudio.hive.games;

import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

public interface GameService {
	@Secured("ROLE_PLAYER")
	@PostFilter("filterObject.playerOne.name == principal.username or "
			+ "(filterObject.playerTwo != null and filterObject.playerTwo.name == principal.username)")
	public List<GameDto> getGamesList();
	
	@PreAuthorize("hasRole('ROLE_PLAYERSSSS')")
	@PostAuthorize("returnObject == null or (returnObject.playerOne.name == principal.username or "
			+ "(returnObject.playerTwo != null and returnObject.playerTwo.name == principal.username))")
	public GameDto getGameById(int id);
}
