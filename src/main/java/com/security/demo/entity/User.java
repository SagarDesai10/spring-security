package com.security.demo.entity;

import java.sql.Timestamp;

import org.hibernate.envers.Audited;

import com.fasterxml.uuid.Generators;
import com.security.demo.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
@Audited
@Builder
public class User {
	
	@Id
	@Builder.Default
	private String id=Generators.timeBasedEpochGenerator().generate().toString();

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
