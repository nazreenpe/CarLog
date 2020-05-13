CREATE TABLE documents(
    id VARCHAR(36) PRIMARY KEY,
    record_id VARCHAR(36) NOT NULL,
    path VARCHAR(250) NOT NULL,
    description TEXT NOT NULL
 );