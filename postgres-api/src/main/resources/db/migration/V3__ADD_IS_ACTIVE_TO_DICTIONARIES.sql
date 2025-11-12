-- Add is_active column to all dictionary tables
-- This column will be used for soft delete functionality

-- dict_asset_status
ALTER TABLE pawcial.dict_asset_status
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_asset_type
ALTER TABLE pawcial.dict_asset_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_color
ALTER TABLE pawcial.dict_color
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_domestic_status
ALTER TABLE pawcial.dict_domestic_status
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_dose_route
ALTER TABLE pawcial.dict_dose_route
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_event_type
ALTER TABLE pawcial.dict_event_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_facility_type
ALTER TABLE pawcial.dict_facility_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_health_flag
ALTER TABLE pawcial.dict_health_flag
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_hold_type
ALTER TABLE pawcial.dict_hold_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_med_event_type
ALTER TABLE pawcial.dict_med_event_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_observation_category
ALTER TABLE pawcial.dict_observation_category
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_outcome_type
ALTER TABLE pawcial.dict_outcome_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_placement_status
ALTER TABLE pawcial.dict_placement_status
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_placement_type
ALTER TABLE pawcial.dict_placement_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_service_type
ALTER TABLE pawcial.dict_service_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_sex
ALTER TABLE pawcial.dict_sex
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_size
ALTER TABLE pawcial.dict_size
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_source_type
ALTER TABLE pawcial.dict_source_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_temperament
ALTER TABLE pawcial.dict_temperament
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_training_level
ALTER TABLE pawcial.dict_training_level
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_unit_type
ALTER TABLE pawcial.dict_unit_type
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_vaccine
ALTER TABLE pawcial.dict_vaccine
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_volunteer_area
ALTER TABLE pawcial.dict_volunteer_area
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_volunteer_status
ALTER TABLE pawcial.dict_volunteer_status
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

-- dict_zone_purpose
ALTER TABLE pawcial.dict_zone_purpose
    ADD COLUMN is_active BOOLEAN NOT NULL DEFAULT TRUE;

