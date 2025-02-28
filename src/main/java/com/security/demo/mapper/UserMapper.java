package com.security.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import com.security.demo.dto.UserResponseDTO;
import com.security.demo.entity.User;


@Named("UserMapper")
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
	@Mappings({ @Mapping(target = "id", source = "entity.id"),
			@Mapping(target = "name", source = "entity.name"),
			@Mapping(target = "role", source = "entity.role"),
			@Mapping(target = "email", source = "entity.email")})
	UserResponseDTO mapToDTO(User entity);
	
	List<UserResponseDTO> mapToDtoList(List<User> entity);
	
	

}
