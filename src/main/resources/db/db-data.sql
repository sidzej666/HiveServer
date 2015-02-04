delete from games;
delete from players;

insert into players(name, email, password_hash, hash_seed)
			values('pawel', 'pawel@email.com', '1234567890', '0000011111');

insert into games(player_one_id, player_two_id, name, password)
			values((select id from players where name = 'pawel'), null, 'sample game', null);