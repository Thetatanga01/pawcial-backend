-- ============================================================
-- Flyway Migration: V13__ADD_LEASH_BEHAVIOR_DICTIONARY.sql
-- Description: Create leash behavior dictionary table
-- ============================================================

-- Create Leash Behavior Dictionary Table
CREATE TABLE pawcial.dict_leash_behavior (
    code TEXT PRIMARY KEY,
    label TEXT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

-- Insert initial data
INSERT INTO pawcial.dict_leash_behavior (code, label, is_active) VALUES
('LEASH_TRAINED', 'Tasmaya Alışık', true),
('LEASH_REACTIVE', 'Tasmalıyken Tepkisel', true),
('PULLS_ON_LEASH', 'Tasmayı Çeker', true),
('CALM_ON_LEASH', 'Tasmada Sakin', true),
('NOT_LEASH_TRAINED', 'Tasmaya Alışkın Değil', true);

-- Add comments
COMMENT ON TABLE pawcial.dict_leash_behavior IS 'Leash behavior (Tasma Uyum) dictionary for animals';
COMMENT ON COLUMN pawcial.dict_leash_behavior.code IS 'Unique code identifier (e.g., LEASH_TRAINED, PULLS_ON_LEASH)';
COMMENT ON COLUMN pawcial.dict_leash_behavior.label IS 'Display label in Turkish';
COMMENT ON COLUMN pawcial.dict_leash_behavior.is_active IS 'Soft delete flag';

