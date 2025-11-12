-- ============================================================
-- Flyway Migration: V15__ADD_ANIMAL_PHOTO_TABLE.sql
-- Description: Create animal_photo table for storing S3 photo references
-- ============================================================

-- Create animal_photo table
CREATE TABLE pawcial.animal_photo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    animal_id UUID NOT NULL,
    photo_url TEXT NOT NULL,
    s3_key TEXT NOT NULL,
    photo_order INTEGER,
    is_primary BOOLEAN NOT NULL DEFAULT false,
    description VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
);

-- Add foreign key constraint
ALTER TABLE pawcial.animal_photo
ADD CONSTRAINT fk_animal_photo_animal
    FOREIGN KEY (animal_id)
    REFERENCES pawcial.animal(id)
    ON DELETE CASCADE;

-- Create indexes
CREATE INDEX idx_animal_photo_animal_id ON pawcial.animal_photo(animal_id);
CREATE INDEX idx_animal_photo_order ON pawcial.animal_photo(animal_id, photo_order);
CREATE INDEX idx_animal_photo_primary ON pawcial.animal_photo(animal_id, is_primary) WHERE is_primary = true;

-- Add comments
COMMENT ON TABLE pawcial.animal_photo IS 'Animal photos stored in S3';
COMMENT ON COLUMN pawcial.animal_photo.animal_id IS 'Reference to animal';
COMMENT ON COLUMN pawcial.animal_photo.photo_url IS 'Public S3 URL of the photo';
COMMENT ON COLUMN pawcial.animal_photo.s3_key IS 'S3 object key (path in bucket)';
COMMENT ON COLUMN pawcial.animal_photo.photo_order IS 'Display order (1, 2, 3, ...)';
COMMENT ON COLUMN pawcial.animal_photo.is_primary IS 'Primary/profile photo flag';
COMMENT ON COLUMN pawcial.animal_photo.description IS 'Photo description';

