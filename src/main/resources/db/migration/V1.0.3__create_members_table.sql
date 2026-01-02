CREATE
    TABLE
        members(
            id VARCHAR(255) PRIMARY KEY,
            name VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL,
            password VARCHAR(255) NOT NULL,
            CONSTRAINT uq_members_email UNIQUE(email)
        );
