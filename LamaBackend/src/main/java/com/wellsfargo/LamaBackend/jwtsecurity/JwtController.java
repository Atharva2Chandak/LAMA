package com.wellsfargo.LamaBackend.jwtsecurity;


import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wellsfargo.LamaBackend.entities.ERole;
import com.wellsfargo.LamaBackend.entities.Role;
import com.wellsfargo.LamaBackend.entities.User;
import com.wellsfargo.LamaBackend.jwtsecurity.JwtTokenRequest;
//import com.wellsfargo.LamaBackend.jwtsecurity.payload.request.JwtSignupRequest;
import com.wellsfargo.LamaBackend.jwtsecurity.JwtTokenResponse;
import com.wellsfargo.LamaBackend.jwtsecurity.JwtMessageResponse;
import com.wellsfargo.LamaBackend.jpaRepos.RoleRepository;
import com.wellsfargo.LamaBackend.jpaRepos.UserRepository;
import com.wellsfargo.LamaBackend.jwtsecurity.JwtTokenUtil;
import com.wellsfargo.LamaBackend.jwtsecurity.JwtUserDetails;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class JwtController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtTokenUtil jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody JwtTokenRequest loginRequest) {
		
		System.out.println(loginRequest.getUsername() + " " + loginRequest.getPassword());
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtTokenResponse(jwt, userDetails.getId(), userDetails.getUsername(),roles));
	}

//	@PostMapping("/signup")
//	public ResponseEntity<?> registerUser(@Valid @RequestBody JwtSignupRequest JwtSignupRequest) {
//		if (userRepository.existsByUsername(JwtSignupRequest.getUsername())) {
//			return ResponseEntity.badRequest().body(new JwtMessageResponse("Error: Username is already taken!"));
//		}
//
//		
//
//		// Create new user's account
//		User user = new User(JwtSignupRequest.getUsername(),
//				encoder.encode(JwtSignupRequest.getPassword()));
//
//		Set<String> strRoles = JwtSignupRequest.getRole();
//		Set<Role> roles = new HashSet<>();
//
//		if (strRoles == null) {
//			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//			roles.add(userRole);
//		} else {
//			strRoles.forEach(role -> {
//				switch (role) {
//				case "admin":
//					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(adminRole);
//					break;
//				default:
//					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
//							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//					roles.add(userRole);
//				}
//			});
//		}
//
//		user.setRoles(roles);
//		userRepository.save(user);
//
//		return ResponseEntity.ok(new JwtMessageResponse("User registered successfully!"));
//	}
}