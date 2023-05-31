
CREATE TABLE IF NOT EXISTS person (
    id SERIAL NOT NULL,
    prsn_name VARCHAR NOT NULL,
    prsn_gender VARCHAR NOT NULL,
    prsn_birth DATE NOT NULL,
    CONSTRAINT person_pkey PRIMARY KEY (id) 
);

CREATE TABLE IF NOT EXISTS place (
    id SERIAL NOT NULL,
    plce_country VARCHAR NOT NULL,
    plce_state VARCHAR NOT NULL,
    plce_city VARCHAR NOT NULL,
    CONSTRAINT place_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS vehicle (
    id SERIAL NOT NULL,
    vhcl_model VARCHAR NOT NULL,
    vhcl_year INT NULL,
    CONSTRAINT vehicle_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS trip (
    id SERIAL NOT NULL,
    vhcl_id INT NOT NULL,
    CONSTRAINT trip_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS traveler (
    id SERIAL NOT NULL,
    trip_id INT NOT NULL,
    prsn_id INT NOT NULL,
    CONSTRAINT traveler_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS parade (
    id SERIAL NOT NULL,
    trip_id INT NOT NULL,
    plce_id INT NOT NULL,
    CONSTRAINT parade_pkey PRIMARY KEY (id)
);
