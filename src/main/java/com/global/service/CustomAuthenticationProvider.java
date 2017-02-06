package com.global.service;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.global.manager.OperadorManager;
import com.global.security.Role;
import com.global.security.UserRole;
import com.global.util.User;

import br.com.gc.cobranca.Operador;

public class CustomAuthenticationProvider implements AuthenticationProvider {
	
	private static final String SALT = "salt"; // Salt should be protected
	// carefully
	
	private static final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	private User user;		
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		  OperadorManager daoOp = new OperadorManager();	
		  String name = authentication.getName();
		  Operador oper = null;
	        // You can get the password here
	        String password = authentication.getCredentials().toString();

	        // Your custom authentication logic here
	        if (name != null && !name.equals("") && password != null && !password.equals("")) {
	        	
	        	try {
					oper = daoOp.findByUsernamePassword(name,password);					
					
					if (oper != null){	
						
						
						user = new User();
						Role role = new Role();
						role.setId("1");
						role.setName("USER");
						
						Set<UserRole> setRole  = new HashSet<UserRole>();			
						UserRole userRole = new UserRole();		
						
						
						String id = String.valueOf(oper.getId());
						System.out.println(id);
						passwordEncoder = new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
						user.setId(id);
						user.setUsername(oper.getNOM_OPERADOR());
						String encryptedPassword = passwordEncoder.encode(oper.getSEN_OPERADOR());
						user.setPassword(encryptedPassword);	
						user.setEmail(oper.getEMA_OPERADOR());
						
						userRole.setUser(user);
						userRole.setRole(role);
						setRole.add(userRole);
						user.setUserRoles(setRole);	
						
						
						
						Authentication auth = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
						 SecurityContextHolder.getContext().setAuthentication(authentication);
					 
					 return auth;
					}else{
						throw new BadCredentialsException("Usuário e senha.");				
					}

				} catch (Exception e) {
					LOG.warn("Usuário" + name + "não encontrado");
					throw new BadCredentialsException("Usuário e senha.");
				}
	        }
	        throw new BadCredentialsException("Usuário e senha.");	        	
	       
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		 return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
