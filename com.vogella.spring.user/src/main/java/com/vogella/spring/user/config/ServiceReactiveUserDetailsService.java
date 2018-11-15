package com.vogella.spring.user.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.vogella.spring.user.domain.User;
import com.vogella.spring.user.service.UserService;

import reactor.core.publisher.Mono;

@Component
public class ServiceReactiveUserDetailsService implements ReactiveUserDetailsService {

	private UserService userService;

	public ServiceReactiveUserDetailsService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userService.findUserByEmail(username).map(CustomUserDetails::new);
	}

	static class CustomUserDetails extends User implements UserDetails {

		private static final long serialVersionUID = -2466968593900571404L;

		public CustomUserDetails(User user) {
			super(user);
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		}

		@Override
		public String getUsername() {
			return getEmail();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return super.isEnabled();
		}
	}

}
