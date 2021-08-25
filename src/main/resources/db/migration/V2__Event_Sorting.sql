ALTER TABLE event
    ADD archived       boolean   NOT NULL DEFAULT (false),
    ADD last_update_date timestamp;
UPDATE event
SET last_update_date = creation_date
WHERE last_update_date IS NULL;
