package com.security.demo.audit;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUSTOM_REVINFO")
@RevisionEntity(CustomEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AuditRevisionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@RevisionNumber
	@Column(name = "ID")
	private Long id;
	
	@RevisionTimestamp
	@Column(name = "TIMESTAMP")
	private LocalDateTime timestamp;
	
	@Column(name = "USERMODIFIED")
	private String userModified;
	
	@Column(name = "PROCESSID")
	private String processId;
}
