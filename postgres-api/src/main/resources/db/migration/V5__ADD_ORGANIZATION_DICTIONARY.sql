-- ============================================================
-- Flyway Migration: V5__ADD_ORGANIZATION_DICTIONARY.sql
-- Description: Add Organization Dictionary Table
-- Author: System
-- Date: 2025-01-19
-- ============================================================

-- ============================================================
-- DICT_ORGANIZATION
-- ============================================================
CREATE TABLE IF NOT EXISTS PAWCIAL.DICT_ORGANIZATION (
    CODE TEXT PRIMARY KEY,
    LABEL TEXT NOT NULL,
    ORGANIZATION_TYPE TEXT,
    CONTACT_PHONE TEXT,
    CONTACT_EMAIL TEXT,
    ADDRESS TEXT,
    NOTES TEXT,
    IS_ACTIVE BOOLEAN DEFAULT TRUE,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_dict_organization_label ON PAWCIAL.DICT_ORGANIZATION(LABEL);
CREATE INDEX IF NOT EXISTS idx_dict_organization_type ON PAWCIAL.DICT_ORGANIZATION(ORGANIZATION_TYPE);
CREATE INDEX IF NOT EXISTS idx_dict_organization_active ON PAWCIAL.DICT_ORGANIZATION(IS_ACTIVE);

-- Insert some sample organizations
INSERT INTO PAWCIAL.DICT_ORGANIZATION (CODE, LABEL, ORGANIZATION_TYPE) VALUES
    ('MUNICIPALITY', 'Belediye', 'Kamu Kurumu'),
    ('VET_CLINIC', 'Veteriner Kliniği', 'Özel Sektör'),
    ('ANIMAL_SHELTER', 'Hayvan Barınağı', 'STK'),
    ('UNIVERSITY', 'Üniversite', 'Eğitim Kurumu')
ON CONFLICT (CODE) DO NOTHING;

-- ============================================================
-- Alter PERSON table to use DICT_ORGANIZATION
-- ============================================================

-- Add new organization_code column
ALTER TABLE PAWCIAL.PERSON ADD COLUMN IF NOT EXISTS organization_code TEXT;

-- Create foreign key to DICT_ORGANIZATION
ALTER TABLE PAWCIAL.PERSON
    ADD CONSTRAINT fk_person_organization
    FOREIGN KEY (organization_code)
    REFERENCES PAWCIAL.DICT_ORGANIZATION(CODE);

-- Create index for better performance
CREATE INDEX IF NOT EXISTS idx_person_organization ON PAWCIAL.PERSON(organization_code);

-- Migrate existing data: Create organizations from existing organization_name values
INSERT INTO PAWCIAL.DICT_ORGANIZATION (CODE, LABEL, ORGANIZATION_TYPE, IS_ACTIVE)
SELECT
    UPPER(REGEXP_REPLACE(organization_name, '[^a-zA-Z0-9]', '_', 'g')) as CODE,
    organization_name as LABEL,
    organization_type as ORGANIZATION_TYPE,
    TRUE as IS_ACTIVE
FROM PAWCIAL.PERSON
WHERE organization_name IS NOT NULL
  AND organization_name != ''
  AND is_organization = TRUE
GROUP BY organization_name, organization_type
ON CONFLICT (CODE) DO NOTHING;

-- Update PERSON records to reference the new organization dictionary
UPDATE PAWCIAL.PERSON
SET organization_code = UPPER(REGEXP_REPLACE(organization_name, '[^a-zA-Z0-9]', '_', 'g'))
WHERE organization_name IS NOT NULL
  AND organization_name != ''
  AND is_organization = TRUE;

-- Drop old columns (keep for now, can be removed in a future migration if needed)
-- ALTER TABLE PAWCIAL.PERSON DROP COLUMN IF EXISTS organization_name;
-- ALTER TABLE PAWCIAL.PERSON DROP COLUMN IF EXISTS organization_type;

COMMENT ON TABLE PAWCIAL.DICT_ORGANIZATION IS 'Dictionary table for organizations (municipalities, shelters, clinics, etc.)';
COMMENT ON COLUMN PAWCIAL.DICT_ORGANIZATION.CODE IS 'Unique organization code';
COMMENT ON COLUMN PAWCIAL.DICT_ORGANIZATION.LABEL IS 'Organization name/label';
COMMENT ON COLUMN PAWCIAL.DICT_ORGANIZATION.ORGANIZATION_TYPE IS 'Type of organization (e.g., Municipality, NGO, Private)';

