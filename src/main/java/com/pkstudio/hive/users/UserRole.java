package com.pkstudio.hive.users;


public enum UserRole {
	USER, ADMIN;

	public UserAuthority asAuthorityFor(final User user) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority(toString());
		authority.setUser(user);
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		switch (authority.getAuthority()) {
		case "USER":
			return USER;
		case "ADMIN":
			return ADMIN;
		}
		throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
	}
}
