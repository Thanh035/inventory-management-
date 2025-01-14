package com.example.demo.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.dto.admin.role.RoleDTO;
import com.example.demo.mappers.RoleDTOMapper;
import com.example.demo.repositories.GroupRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class RoleService {

	private final GroupRepository groupRepository;

	private final RoleDTOMapper roleDTOMapper;

	@Transactional(readOnly = true)
	public Page<RoleDTO> getAllManagedRoles(Pageable pageable) {
		return groupRepository.findAll(pageable).map(roleDTOMapper);
	}

}
