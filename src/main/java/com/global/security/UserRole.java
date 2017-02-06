package com.global.security;


import com.global.util.User;


public class UserRole {
	

	private String Id;
	
	
	private User user;	
	
	private Role role;
	
	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}
	
	public UserRole() {}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	
}
