package com.example.demo.services;

import com.example.demo.domain.entities.Group;
import com.example.demo.domain.entities.User;
import com.example.demo.exception.UserNotActivatedException;
import com.example.demo.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
@AllArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        log.debug("Authenticating {}", login);
        if (new EmailValidator().isValid(login, null)) {
            return userRepository.findOneWithGroupsByEmailIgnoreCase(login)
                    .map(user -> createSpringSecurityUser(login, user)).orElseThrow(() -> new UsernameNotFoundException(
                            "User with email " + login + " was not found in the database"));
        }

        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        return userRepository.findOneWithGroupsByLogin(lowercaseLogin)
                .map(user -> createSpringSecurityUser(lowercaseLogin, user))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User " + lowercaseLogin + " was not found in the database"));
    }

    private UserDetails createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User: " + lowercaseLogin + " was not activated");
        }

        List<GrantedAuthority> grantedAuthorities = user.getGroups().stream().map(Group::getGroupCode)
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                grantedAuthorities);
    }

}
