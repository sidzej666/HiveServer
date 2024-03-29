package com.pkstudio.hive.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pkstudio.generic.dao.GenericId;
import com.sun.istack.internal.NotNull;

@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends GenericId implements UserDetails {

	public static final int MAX_USERNAME_LENGTH = 20;
	public static final int MIN_PASSWORD_LENGTH = 5;
	public static final int MAX_PASSWORD_LENGTH = 50;
	public static final int MAX_EMAIL_LENGTH = 90;
	
	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public User(String username, Date expires) {
		this.username = username;
		this.expires = expires.getTime();
	}
	
	public User(int id, String username, Date expires) {
		this.setId(id);
		this.username = username;
		this.expires = expires.getTime();
	}

	@NotNull
	private String username;
	
	@NotNull
	private String email;

	@NotNull
	private String password;
	
	@Transient
	private long expires;

	@NotNull
	@Column(name = "account_expired")
	private boolean accountExpired;

	@NotNull
	@Column(name = "account_locked")
	private boolean accountLocked;

	@NotNull
	@Column(name = "credentials_expired")
	private boolean credentialsExpired;

	@NotNull
	@Column(name = "account_enabled")
	private boolean accountEnabled;

	@Transient
	private String newPassword;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = false)
	@Cascade(CascadeType.SAVE_UPDATE)
	private Set<UserAuthority> authorities;

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	@JsonIgnore
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Set<UserAuthority> authorities) {
		this.authorities = authorities;
	}

	// Use Roles as external API
	public Set<UserRole> getRoles() {
		Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
		if (authorities != null) {
			for (UserAuthority authority : authorities) {
				roles.add(UserRole.valueOf(authority));
			}
		}
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		for (UserRole role : roles) {
			grantRole(role);
		}
	}

	public void grantRole(UserRole role) {
		if (authorities == null) {
			authorities = new HashSet<UserAuthority>();
		}
		authorities.add(role.asAuthorityFor(this));
	}

	public void revokeRole(UserRole role) {
		if (authorities != null) {
			authorities.remove(role.asAuthorityFor(this));
		}
	}

	public boolean hasRole(UserRole role) {
		return authorities.contains(role.asAuthorityFor(this));
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return accountEnabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.accountEnabled = enabled;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getUsername();
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public List<String> getRolesInStringFormat() {
		List<String> roles = new ArrayList<String>();
		for (UserAuthority userAuthority: authorities) {
			roles.add(userAuthority.getAuthority());
		}
		return roles;
	}
}
