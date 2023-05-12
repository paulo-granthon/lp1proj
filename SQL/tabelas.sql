
CREATE TABLE IF NOT EXISTS person (
    id serial NOT NULL,
    prsn_name VARCHAR NOT NULL,
    prsn_gender VARCHAR NOT NULL,
    prsn_birth DATE NOT NULL,

    CONSTRAINT person_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS place (
    id serial NOT NULL,
    plce_country VARCHAR NOT NULL,
    plce_state VARCHAR NOT NULL,
    plce_city VARCHAR NOT NULL,

    CONSTRAINT place_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vehicle (
    id serial NOT NULL,
    vhcl_model VARCHAR NOT NULL,
    vhcl_year INT NULL,

    CONSTRAINT vehicle_pkey PRIMARY KEY (id)
);
