package com.security.demo.audit;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.hibernate.envers.RevisionListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.security.demo.dto.JwtDTO;


public class CustomEntityListener implements RevisionListener {

	@Override
	public void newRevision(Object revisionEntity) {
		AuditRevisionEntity entity = (AuditRevisionEntity) revisionEntity;
		entity.setProcessId(UUID.randomUUID().toString());
		entity.setTimestamp(LocalDateTime.now(ZoneOffset.UTC));
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null) {
			try {
				if(auth.getDetails() instanceof JwtDTO) {
					JwtDTO jwtToken = (JwtDTO) auth.getDetails();
					if(jwtToken != null) {
						entity.setUserModified(jwtToken.getId());
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		
	}

}
