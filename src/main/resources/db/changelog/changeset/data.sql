INSERT INTO roles (name) VALUES
    ('USER_ROLE'),
    ('ADMIN_ROLE'),
    ('OPERATOR_ROLE');

INSERT INTO users (username, password) VALUES
    ('Andrew Smirnov', '$2a$10$DFDectT2.InN1O3vJA8m5ukEJuoz3HY98LOu51nWh8IHSbL8arXpG'),
    ('USER', '$2a$10$QT4DYML.0uJW8Tnx1zMsAuD6TatCcu1mCWgJI8vGp2/yBJwWimCj6'),
    ('ADMIN', '$2a$10$0Hq9E.iRz4jxu/kPZW1z2Ol2QsuzlCp0V.hksUbVIGDUinAM/WjpG'),
    ('OPERATOR', '$2a$10$f5bBr3dA29WUaTch3TS5V.vAiB0WdnavaTKJzXAwN3v7.n3m9RNMa');

INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 1),
    (3, 2),
    (4, 3);

