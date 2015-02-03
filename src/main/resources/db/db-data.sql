insert into players(name, password_hash, hash_seed)
			values('pawel', '1234567890', '0000011111');

insert into games(player_one_id, player_two_id, name)
			values((select id from players where name = 'pawel'), null, 'sample game');