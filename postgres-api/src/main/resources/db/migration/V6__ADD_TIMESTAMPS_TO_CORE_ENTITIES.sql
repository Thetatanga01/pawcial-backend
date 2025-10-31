-- ============================================================
-- Flyway Migration: V6__ADD_TIMESTAMPS_TO_CORE_ENTITIES.sql
-- Description: Add created_at and updated_at timestamp columns to core entities
-- ============================================================

-- Add timestamps to ANIMAL_EVENT
ALTER TABLE pawcial.animal_event
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

-- Add timestamps to ANIMAL_PLACEMENT
ALTER TABLE pawcial.animal_placement
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

-- Add timestamps to ANIMAL_OBSERVATION
ALTER TABLE pawcial.animal_observation
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

-- Add timestamps to ANIMAL_BREED_COMPOSITION
ALTER TABLE pawcial.animal_breed_composition
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

-- Add timestamps to FACILITY_ZONE
ALTER TABLE pawcial.facility_zone
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

-- Add timestamps to FACILITY_UNIT
ALTER TABLE pawcial.facility_unit
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

-- Add timestamps to ASSET_SERVICE
ALTER TABLE pawcial.asset_service
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

