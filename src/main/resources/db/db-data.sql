delete from games;
delete from user_authorities;
delete from users;

insert into users(username, password, email, account_expired, account_locked, credentials_expired, account_enabled)
			values('pawel', '1234567890', 'pawel@email.com', false, false, false, true);

insert into user_authorities(user_id, authority)
			values((select id from users where username = 'pawel'), 'USER');
			
insert into games(player_one_id, player_two_id, name, password)
			values((select id from users where username = 'pawel'), null, 'sample game', null);