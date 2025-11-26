-- Script de criação do banco EasyStop (SQLite/MariaDB compatível)

CREATE TABLE IF NOT EXISTS veiculos (
    placa           VARCHAR(10) PRIMARY KEY,
    modelo          VARCHAR(80) NOT NULL,
    cor             VARCHAR(40) NOT NULL,
    proprietario    VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS vagas (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    codigo      VARCHAR(20) NOT NULL UNIQUE,
    setor       VARCHAR(40) NOT NULL,
    ocupada     BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS checkins (
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    veiculo_placa    VARCHAR(10) NOT NULL REFERENCES veiculos(placa),
    vaga_id          INTEGER NOT NULL REFERENCES vagas(id),
    horario_entrada  DATETIME NOT NULL,
    observacao       VARCHAR(255),
    finalizado       BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS checkouts (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    checkin_id      INTEGER NOT NULL UNIQUE REFERENCES checkins(id),
    horario_saida   DATETIME NOT NULL,
    valor_calculado DECIMAL(10,2) NOT NULL
);

CREATE TABLE IF NOT EXISTS pagamentos (
    id              INTEGER PRIMARY KEY AUTOINCREMENT,
    checkout_id     INTEGER NOT NULL UNIQUE REFERENCES checkouts(id),
    metodo          VARCHAR(30) NOT NULL,
    status          VARCHAR(20) NOT NULL,
    valor_total     DECIMAL(10,2) NOT NULL,
    data_pagamento  DATETIME NOT NULL
);

