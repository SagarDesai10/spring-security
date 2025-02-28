CREATE TABLE CUSTOM_REVINFO (
    ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    TIMESTAMP TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    USERMODIFIED CHAR(36),
    PROCESSID VARCHAR(255)
);


-- Create_user_audit_table.sql
CREATE TABLE USERS_AUD (
    id CHAR(36) NOT NULL,
    rev BIGINT NOT NULL,
    revtype SMALLINT,
    name VARCHAR(100) ,
    email VARCHAR(100),
    role VARCHAR(10),
    is_deleted BOOLEAN ,
    password VARCHAR(255),
    created_at TIMESTAMP ,
    modified_at TIMESTAMP, 
    PRIMARY KEY (id, rev),
    FOREIGN KEY (rev) REFERENCES CUSTOM_REVINFO (ID)
);

CREATE INDEX idx_users_aud_rev ON USERS_AUD (rev);