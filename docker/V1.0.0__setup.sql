CREATE USER "r2dbc" WITH password 'r2dbc';

CREATE SCHEMA "example_schema";

GRANT USAGE ON SCHEMA "example_schema" TO "r2dbc";
ALTER USER "r2dbc" SET search_path = "example_schema, public";

ALTER DEFAULT PRIVILEGES
IN SCHEMA "example_schema"
GRANT SELECT, UPDATE, INSERT, DELETE ON TABLES
TO "r2dbc";

-- ensure that user backoffice-web-bff-app will have the needed privileges on new sequences
ALTER DEFAULT PRIVILEGES
IN SCHEMA "example_schema"
GRANT USAGE ON SEQUENCES
TO "r2dbc";

-- ensure that new functions will not have default privilege in public schema
ALTER DEFAULT PRIVILEGES
REVOKE EXECUTE ON FUNCTIONS
FROM PUBLIC;

-- revoke default function privilege
REVOKE EXECUTE
ON ALL FUNCTIONS IN SCHEMA PUBLIC
FROM PUBLIC;

CREATE SEQUENCE example_schema.PERSON_SEQUENCE START 1 INCREMENT BY 1;

CREATE TABLE example_schema.PERSON (
  ID INT4 DEFAULT NEXTVAL('example_schema.PERSON_SEQUENCE') PRIMARY KEY,
  NAME VARCHAR(255)
);