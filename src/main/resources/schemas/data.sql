# ##################################################################################################

# You can use this file to pre-populate your DB with, for example, roles
# Roles MUST be uppercase and are unique by rolename.

# ##################################################################################################

/*
INSERT INTO example.role (rolename)
VALUES ("USER")
ON DUPLICATE KEY UPDATE
rolename = "ROLE_USER";

INSERT INTO example.role (rolename)
VALUES ("ADMIN")
ON DUPLICATE KEY UPDATE
rolename = "ROLE_ADMIN";
*/