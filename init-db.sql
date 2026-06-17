DO $$
BEGIN
  IF NOT EXISTS (SELECT FROM pg_catalog.pg_roles WHERE rolname = 'rednorte') THEN
    CREATE ROLE rednorte LOGIN PASSWORD 'rednorte_pass_2024';
  END IF;
END
$$;

CREATE DATABASE auth_db OWNER rednorte;
CREATE DATABASE lista_espera OWNER rednorte;
CREATE DATABASE reasignacion OWNER rednorte;
CREATE DATABASE pacientes OWNER rednorte;
