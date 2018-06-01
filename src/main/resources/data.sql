
INSERT into USER (id, username,  password, enabled) VALUES
(1, 'admin','$2a$12$esTL5RGN9xDixtb3J7DFGuVLQnL6TLxW4ADIm.0KH/SO458uNiYLW', true),
(2, 'user','$2a$12$esTL5RGN9xDixtb3J7DFGuVLQnL6TLxW4ADIm.0KH/SO458uNiYLW', true); --password

DROP TABLE IF EXISTS role;

CREATE TABLE role (
    id bigint,
role_name varchar(64),
description varchar(255)
);

INSERT INTO ROLE (id, role_name, description) VALUES
(1, 'ROLE_ADMIN', 'Administrator'),
(2, 'ROLE_USER', 'User');

DROP TABLE IF EXISTS user_role;

CREATE TABLE user_role (
    user_id bigint,
    role_id bigint
);

INSERT INTO USER_ROLE (user_id, role_id) VALUES
(1,1),
(2,2);