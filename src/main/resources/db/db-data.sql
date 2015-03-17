delete from games;
delete from players;

insert into users(username, password, email, account_expired, account_locked, credentials_expired, account_enabled)
			values('pawel', '1234567890', 'pawel@email.com', false, false, false, true);

insert into games(player_one_id, player_two_id, name, password)
			values((select id from players where name = 'pawel'), null, 'sample game', null);