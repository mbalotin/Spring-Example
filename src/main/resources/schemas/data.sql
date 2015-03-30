# ##########################

# You can use this file to pre-populate your DB with, for example, roles
# Roles MUST be named ROLE_${something} and are unique by rolename.

# ##########################

/*
INSERT INTO example.role (rolename)
VALUES ("ROLE_USER")
ON DUPLICATE KEY UPDATE
rolename = "ROLE_USER";

INSERT INTO example.role (rolename)
VALUES ("ROLE_ADMIN")
ON DUPLICATE KEY UPDATE
rolename = "ROLE_ADMIN";
*/