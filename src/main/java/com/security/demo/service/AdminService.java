package com.security.demo.service;

import org.springframework.data.web.PagedModel;

import com.security.demo.dto.UserResponseDTO;

public interface AdminService {

	PagedModel<UserResponseDTO> searchUser(String search, Integer page, Integer size);

}
