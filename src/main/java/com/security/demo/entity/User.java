package com.security.demo.entity;

import java.sql.Timestamp;

import com.security.demo.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name="user")
@Builder
public class User {
	
	@Id
	private String id;

	@Column(name="name")
	private String name;
	
	@Column(name="email")
	private String email;
	
	@Column(name="is_deleted")
	@Builder.Default
	private boolean isDeleted=false;
	
	@Column(name="role")
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@Column(name="password")
	private String password;
	
	@Column(name="created_at")
	private Timestamp createdAt;
	
	@Column(name="modified_at")
	private Timestamp modifiedAt;
	
}
