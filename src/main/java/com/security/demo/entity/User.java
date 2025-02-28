package com.security.demo.entity;

import java.sql.Timestamp;

import org.hibernate.envers.Audited;

import com.fasterxml.uuid.Generators;
import com.security.demo.dto.UserResponseDTO;
import com.security.demo.enums.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// named native query is use for native query
@NamedNativeQueries(value = { @NamedNativeQuery(name = "User.search", query = """
		SELECT
		u.id AS id,
		u.name AS name,
		u.email AS email,
		u.role AS role
		FROM user u
		WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
		""", resultSetMapping = "UserDetails"),
		
		@NamedNativeQuery(name = "User.count", query = """
		SELECT
		COUNT(u.id) AS userCount
		FROM user u
		WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
		""",  resultClass = Long.class)
  }
)

//sql result set mapping is use for map native query response to dto
@SqlResultSetMapping(name = "UserDetails", classes = {
		@ConstructorResult(columns = { @ColumnResult(name = "id", type = Long.class), @ColumnResult(name = "name"),
				@ColumnResult(name = "email"), @ColumnResult(name = "role", type = Roles.class)

		}, targetClass = UserResponseDTO.class) })

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
@Audited
@Builder
public class User {

	@Id
	@Builder.Default
	private String id = Generators.timeBasedEpochGenerator().generate().toString();

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

	@Column(name = "is_deleted")
	@Builder.Default
	private boolean isDeleted = false;

	@Column(name = "role")
	@Enumerated(EnumType.STRING)
	private Roles role;

	@Column(name = "password")
	private String password;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "modified_at")
	private Timestamp modifiedAt;

}
