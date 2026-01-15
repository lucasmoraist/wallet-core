CREATE TABLE t_wallets (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL,
    balance DECIMAL(19,4) NOT NULL,
    version INTEGER,
    CONSTRAINT fk_wallet_user FOREIGN KEY (user_id) REFERENCES t_users(id) ON DELETE CASCADE
);