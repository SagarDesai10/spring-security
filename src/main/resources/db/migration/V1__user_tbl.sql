-- user_tbl.sql
CREATE TABLE user (
    id CHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    role VARCHAR(10),
    is_deleted BOOLEAN DEFAULT FALSE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP ,
    modified_at TIMESTAMP 
);