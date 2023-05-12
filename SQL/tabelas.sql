
CREATE TABLE IF NOT EXISTS pessoa (
    id serial NOT NULL,
    nome INT NOT NULL,
    genero VARCHAR NOT NULL,
    nascimento DATE NOT NULL,

    CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS lugar (
    id serial NOT NULL,
    pais VARCHAR NOT NULL,
    estado VARCHAR NOT NULL,
    cidade VARCHAR NOT NULL,

    CONSTRAINT lugar_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS veiculo (
    id serial NOT NULL,
    modelo VARCHAR NOT NULL,
    ano INT NULL,

    CONSTRAINT veiculo_pkey PRIMARY KEY (id)
);
