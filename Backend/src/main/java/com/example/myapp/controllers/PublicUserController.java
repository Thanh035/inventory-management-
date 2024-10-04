package com.example.myapp.controllers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.myapp.constants.RolesConstants;
import com.example.myapp.domain.dto.admin.user.UserDTO;
import com.example.myapp.services.UserService;
import com.example.myapp.utils.PaginationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1.0")
@RequiredArgsConstructor
@Slf4j
public class PublicUserController {
	private static final List<String> ALLOWED_ORDERED_PROPERTIES = Collections.unmodifiableList
			(Arrays.asList("id",
					"fullname",
					"email",
					"activated",
					"createdDate",
					"lastModifiedBy",
					"lastModifiedDate")
			);

	private final UserService userService;

	@GetMapping("/users")
	@PreAuthorize("hasAuthority(\"" + RolesConstants.ADMIN + "\")")
	public ResponseEntity<List<UserDTO>> getAllPublicUsers(
			@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
		log.debug("REST request to get all User for an admin");
		if (!onlyContainsAllowedProperties(pageable)) {
			return ResponseEntity.badRequest().build();
		}

		final Page<UserDTO> page = userService.getAllPublicUsers(pageable);
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	@GetMapping("/roles")
	public List<String> getRoles() {
		return userService.getRoles();
	}

	private boolean onlyContainsAllowedProperties(Pageable pageable) {
		return pageable.getSort().stream().map(Sort.Order::getProperty).allMatch(ALLOWED_ORDERED_PROPERTIES::contains);
	}

}
