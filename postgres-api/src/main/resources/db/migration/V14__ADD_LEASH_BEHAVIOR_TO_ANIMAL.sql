-- ============================================================
-- Flyway Migration: V14__ADD_LEASH_BEHAVIOR_TO_ANIMAL.sql
-- Description: Add leash_behavior column to animal table with foreign key
-- ============================================================

-- Add leash_behavior column to animal table
ALTER TABLE pawcial.animal
ADD COLUMN IF NOT EXISTS leash_behavior VARCHAR(50);

-- Add foreign key constraint
ALTER TABLE pawcial.animal
DROP CONSTRAINT IF EXISTS fk_animal_leash_behavior,
ADD CONSTRAINT fk_animal_leash_behavior
    FOREIGN KEY (leash_behavior)
    REFERENCES pawcial.dict_leash_behavior(code)
    ON DELETE SET NULL;

-- Add comment
COMMENT ON COLUMN pawcial.animal.leash_behavior IS 'Leash behavior code reference to dict_leash_behavior';

