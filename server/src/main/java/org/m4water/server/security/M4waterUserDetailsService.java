package org.m4water.server.security;


import org.m4water.server.admin.model.User;
import org.springframework.security.userdetails.UserDetailsService;

public interface M4waterUserDetailsService extends UserDetailsService {

	M4waterUserDetails getUserDetailsForUser(User user);

}
