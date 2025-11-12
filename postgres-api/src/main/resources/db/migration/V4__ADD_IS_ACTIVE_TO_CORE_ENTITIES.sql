-- Add is_active column to all core entity tables
-- This column will be used for soft delete functionality

-- Species
ALTER TABLE pawcial.species
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Breed
ALTER TABLE pawcial.breed
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Facility
ALTER TABLE pawcial.facility
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Facility Zone
ALTER TABLE pawcial.facility_zone
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Facility Unit
ALTER TABLE pawcial.facility_unit
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Person
ALTER TABLE pawcial.person
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Volunteer
ALTER TABLE pawcial.volunteer
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Volunteer Area
ALTER TABLE pawcial.volunteer_area
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Volunteer Activity
ALTER TABLE pawcial.volunteer_activity
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Animal
ALTER TABLE pawcial.animal
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Animal Breed Composition
ALTER TABLE pawcial.animal_breed_composition
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Animal Temperament
ALTER TABLE pawcial.animal_temperament
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Animal Health Flag
ALTER TABLE pawcial.animal_health_flag
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Animal Event
ALTER TABLE pawcial.animal_event
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Animal Placement
ALTER TABLE pawcial.animal_placement
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Animal Observation
ALTER TABLE pawcial.animal_observation
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Asset
ALTER TABLE pawcial.asset
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- Asset Service
ALTER TABLE pawcial.asset_service
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

