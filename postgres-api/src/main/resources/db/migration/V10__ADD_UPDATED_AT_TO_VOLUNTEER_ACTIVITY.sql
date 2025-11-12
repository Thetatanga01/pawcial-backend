-- ============================================================
-- Flyway Migration: V10__ADD_UPDATED_AT_TO_VOLUNTEER_ACTIVITY.sql
-- Description: Add updated_at timestamp column to volunteer_activity table
-- ============================================================

ALTER TABLE pawcial.volunteer_activity
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ;

-- Add comment
COMMENT ON COLUMN pawcial.volunteer_activity.updated_at IS 'Timestamp when the record was last updated';

