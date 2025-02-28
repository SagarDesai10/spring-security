package com.security.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.security.demo.dto.UserResponseDTO;
import com.security.demo.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

	Optional<User> findByEmail(String email);
	
	
	// it use named native query
	@Query(nativeQuery=true,name="User.search")
	List<UserResponseDTO>  searchUserByName(@Param("search") String search,Pageable pageable);

	@Query(nativeQuery=true,name="User.count")
	Long totalElementOfUser(@Param("search") String search);
}
