package com.security.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import com.security.demo.dto.UserResponseDTO;
import com.security.demo.repository.UserRepository;
import com.security.demo.service.AdminService;
import com.security.demo.utils.PageUtility;


@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public PagedModel<UserResponseDTO> searchUser(String search, Integer page, Integer size) {
		Pageable pageable = PageUtility.getPageable(page, size);
        List<UserResponseDTO> response = userRepo.searchUserByName(search,pageable);
        Long totalElement = userRepo.totalElementOfUser(search);
        return new PagedModel<>(new PageImpl<>(response, pageable, totalElement));
	}

}
