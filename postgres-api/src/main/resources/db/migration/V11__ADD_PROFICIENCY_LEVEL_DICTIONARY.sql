-- ============================================================
-- Flyway Migration: V11__ADD_PROFICIENCY_LEVEL_DICTIONARY.sql
-- Description: Create proficiency level dictionary table
-- ============================================================

-- Drop table if exists (for clean migration)
DROP TABLE IF EXISTS pawcial.dict_proficiency_level CASCADE;

-- Create Proficiency Level Dictionary Table
CREATE TABLE pawcial.dict_proficiency_level (
    code TEXT PRIMARY KEY,
    label TEXT NOT NULL,
    description TEXT,
    display_order INTEGER,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

-- Insert initial data
INSERT INTO pawcial.dict_proficiency_level (code, label, description, display_order, is_active) VALUES
('BEGINNER', 'Başlangıç', 'Temel seviye bilgi ve deneyim', 1, true),
('INTERMEDIATE', 'Orta', 'Orta seviye bilgi ve deneyim', 2, true),
('EXPERT', 'Uzman', 'İleri seviye bilgi ve deneyim', 3, true);

-- Add comments
COMMENT ON TABLE pawcial.dict_proficiency_level IS 'Proficiency level dictionary for volunteer areas and skills';
COMMENT ON COLUMN pawcial.dict_proficiency_level.code IS 'Unique code identifier (e.g., BEGINNER, INTERMEDIATE, EXPERT)';
COMMENT ON COLUMN pawcial.dict_proficiency_level.label IS 'Display label in Turkish';
COMMENT ON COLUMN pawcial.dict_proficiency_level.description IS 'Detailed description of the proficiency level';
COMMENT ON COLUMN pawcial.dict_proficiency_level.display_order IS 'Order for UI display';
COMMENT ON COLUMN pawcial.dict_proficiency_level.is_active IS 'Soft delete flag';

-- Add foreign key constraint to volunteer_area table
ALTER TABLE pawcial.volunteer_area
DROP CONSTRAINT IF EXISTS fk_volunteer_area_proficiency_level,
ADD CONSTRAINT fk_volunteer_area_proficiency_level
    FOREIGN KEY (proficiency_level)
    REFERENCES pawcial.dict_proficiency_level(code)
    ON DELETE RESTRICT;

