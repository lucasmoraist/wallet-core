CREATE TABLE t_wallet_transactions (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    wallet_id UUID NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_transaction_wallet FOREIGN KEY (wallet_id) REFERENCES t_wallets(id) ON DELETE CASCADE
);