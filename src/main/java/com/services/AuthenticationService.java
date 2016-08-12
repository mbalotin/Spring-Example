package com.services;

import com.repositories.RoleRepository;
import com.repositories.UserRepository;
import com.models.AuthUser;
import com.models.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 NICE TUTORIAL: http://kielczewski.eu/2014/12/spring-boot-security-application/
 */
@Service
public class AuthenticationService implements UserDetailsService {

	@Value("${com.admin.username}")
	private String adminUsername;

	@Value("${com.admin.password}")
	private String adminPassword;

	@Value("${com.admin.email}")
	private String adminEmail;

	@Value("#{'${com.admin.roles}'.split(',')}")
	private Collection<String> adminRoles;

	@Value("#{'${com.user.roles}'.split(',')}")
	private Collection<String> userRoles;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	public void createAdmin() {
		String encryptedPassword = AuthUser.encryptPassword(adminPassword);
		AuthUser admin = Optional.ofNullable(userRepository.findByUsername(adminUsername)).orElse(new AuthUser());

		//Add admin roles
		adminRoles.forEach((role) -> admin.addRole(getRoleByName(role)));

		//Add user roles
		userRoles.forEach((role) -> admin.addRole(getRoleByName(role)));

		admin.setUsername(adminUsername);
		admin.setPassword(encryptedPassword);
		admin.setEmail(adminEmail);
		userRepository.save(admin);
	}

	public AuthUser getAuthenticatedUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return getAuthenticatedUserByName(username);
	}

	public AuthUser getAuthenticatedUserByName(String username) throws UsernameNotFoundException {
		return Optional.ofNullable(userRepository.findByUsername(username)).orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
	}

	public Set<Role> getUserRoles() {
		Set<Role> roles = new HashSet<>();
		userRoles.forEach((role) -> roles.add(getRoleByName(role)));
		return roles;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		AuthUser domainUser = getAuthenticatedUserByName(username);

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;

		return new User(
						domainUser.getUsername(),
						domainUser.getPassword(),
						enabled,
						accountNonExpired,
						credentialsNonExpired,
						accountNonLocked,
						getAuthorities(domainUser.getRoles()));

	}

	private List<GrantedAuthority> getAuthorities(Collection<Role> roles) {
		final List<GrantedAuthority> authList = new ArrayList<>();
		roles.forEach((role) -> authList.add(new SimpleGrantedAuthority(standardizeRoleName(role.getRolename()))));
		return authList;
	}

	private Role getRoleByName(String rolename) {
		String finalName = standardizeRoleName(rolename);
		return Optional.ofNullable(roleRepository.findByRolename(finalName)).orElse(new Role(finalName));
	}

	/*
   The prefix ROLES_ is, unfortunately, required by Spring Security.
   This method makes it somewhat transparent to the rest of the code.
	 */
	private String standardizeRoleName(String rolename) {
		String finalName = rolename.trim().toUpperCase();
		if (!rolename.startsWith("ROLE_")) {
			finalName = "ROLE_" + finalName;
		}
		return finalName;
	}

}
