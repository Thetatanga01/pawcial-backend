-- ============================================================
-- Flyway Migration: V7__ADD_SYSTEM_PARAMETERS.sql
-- Description: Add system parameters dictionary table for configuration management
-- ============================================================

-- Create System Parameter Dictionary Table
CREATE TABLE IF NOT EXISTS pawcial.dict_system_parameter (
    code TEXT PRIMARY KEY,
    label TEXT NOT NULL,
    parameter_value TEXT NOT NULL,
    description TEXT,
    is_active BOOLEAN NOT NULL DEFAULT true
);

-- Insert default hard delete window parameter (300 seconds = 5 minutes)
INSERT INTO pawcial.dict_system_parameter (code, label, parameter_value, description, is_active)
VALUES
    ('HARD_DELETE_WINDOW_SECONDS', 'Hard Delete Time Window (Seconds)', '300',
     'Time window in seconds after record creation during which hard delete is allowed. Default: 300 seconds (5 minutes)',
     true);

-- Create index on is_active for faster filtering
CREATE INDEX IF NOT EXISTS idx_system_parameter_active
ON pawcial.dict_system_parameter(is_active);

