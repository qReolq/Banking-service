CREATE TABLE IF NOT EXISTS users
(
    id            BIGSERIAL PRIMARY KEY,
    login         VARCHAR      NOT NULL UNIQUE,
    password      VARCHAR      NOT NULL,
    full_name     VARCHAR(255),
    date_of_birth DATE
);

CREATE TABLE IF NOT EXISTS users_emails
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT       NOT NULL,
    email   VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_users_email_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS users_phones
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT       NOT NULL,
    phone_number   VARCHAR(255) NOT NULL UNIQUE,
    CONSTRAINT fk_users_phones_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS accounts
(
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT         NOT NULL,
    balance         DECIMAL(19, 4) NOT NULL CHECK ( balance >= 0 ),
    initial_deposit DECIMAL(19, 4) NOT NULL CHECK ( initial_deposit >= 0 ),
    CONSTRAINT fk_accounts_users FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS transactions
(
    id               BIGSERIAL PRIMARY KEY,
    from_account_id  BIGINT         NOT NULL,
    to_account_id    BIGINT         NOT NULL,
    amount           DECIMAL(19, 4) NOT NULL,
    transaction_date TIMESTAMP      NOT NULL,
    CONSTRAINT fk_transactions_accounts_from FOREIGN KEY (from_account_id) REFERENCES accounts (id),
    CONSTRAINT fk_transactions_accounts_to FOREIGN KEY (to_account_id) REFERENCES accounts (id)
);