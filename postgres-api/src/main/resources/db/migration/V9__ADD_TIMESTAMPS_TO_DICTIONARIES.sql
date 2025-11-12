-- ============================================================
-- Flyway Migration: V8__ADD_TIMESTAMPS_TO_DICTIONARIES.sql
-- Description: Add created_at and updated_at timestamps to all dictionary tables
-- ============================================================

-- Add timestamps to dict_color (if not exists)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_color' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_color ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_color' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_color ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_size
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_size' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_size ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_size' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_size ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_sex
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_sex' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_sex ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_sex' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_sex ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_temperament
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_temperament' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_temperament ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_temperament' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_temperament ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_health_flag
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_health_flag' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_health_flag ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_health_flag' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_health_flag ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_event_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_event_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_event_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_event_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_event_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_service_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_service_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_service_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_service_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_service_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_med_event_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_med_event_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_med_event_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_med_event_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_med_event_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_placement_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_placement_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_placement_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_placement_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_placement_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_placement_status
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_placement_status' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_placement_status ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_placement_status' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_placement_status ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_domestic_status
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_domestic_status' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_domestic_status ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_domestic_status' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_domestic_status ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_training_level
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_training_level' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_training_level ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_training_level' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_training_level ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_observation_category
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_observation_category' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_observation_category ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_observation_category' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_observation_category ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_outcome_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_outcome_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_outcome_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_outcome_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_outcome_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_facility_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_facility_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_facility_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_facility_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_facility_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_unit_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_unit_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_unit_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_unit_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_unit_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_asset_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_asset_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_asset_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_asset_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_asset_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_asset_status
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_asset_status' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_asset_status ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_asset_status' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_asset_status ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_zone_purpose
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_zone_purpose' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_zone_purpose ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_zone_purpose' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_zone_purpose ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_volunteer_area
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_volunteer_area' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_volunteer_area ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_volunteer_area' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_volunteer_area ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_volunteer_status
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_volunteer_status' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_volunteer_status ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_volunteer_status' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_volunteer_status ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_hold_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_hold_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_hold_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_hold_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_hold_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_source_type
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_source_type' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_source_type ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_source_type' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_source_type ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_dose_route
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_dose_route' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_dose_route ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_dose_route' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_dose_route ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_vaccine
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_vaccine' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_vaccine ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_vaccine' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_vaccine ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

-- Add timestamps to dict_organization (if not exists - this one already has it)
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_organization' AND column_name = 'created_at') THEN
        ALTER TABLE pawcial.dict_organization ADD COLUMN created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
    IF NOT EXISTS (SELECT 1 FROM information_schema.columns WHERE table_schema = 'pawcial' AND table_name = 'dict_organization' AND column_name = 'updated_at') THEN
        ALTER TABLE pawcial.dict_organization ADD COLUMN updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP;
    END IF;
END $$;

