CREATE TABLE accounts (
  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  username VARCHAR(20) NOT NULL,
  password VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,  
  lastlogin TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
  enabled BOOLEAN NOT NULL DEFAULT true,
  PRIMARY KEY (id),
  UNIQUE (username));

CREATE TABLE account_roles (
  role_id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  username VARCHAR(20) NOT NULL REFERENCES accounts (username),
  role VARCHAR(20) NOT NULL,
  PRIMARY KEY (role_id),
  UNIQUE (role, username));
