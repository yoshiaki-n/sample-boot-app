CREATE
    TABLE user_members(
        id VARCHAR(60) PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(100) NOT NULL,
        CONSTRAINT uq_user_members_email UNIQUE(email)
    );
