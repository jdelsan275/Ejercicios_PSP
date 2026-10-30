DROP TABLE IF EXISTS resultado;
DROP TABLE IF EXISTS piloto;
DROP TABLE IF EXISTS equipo;

-- Tabla A: equipo (validación)
CREATE TABLE equipo (
    id_equipo      SERIAL PRIMARY KEY,
    usuario        VARCHAR(50) NOT NULL UNIQUE,
    password       VARCHAR(100) NOT NULL,
    nombre_equipo  VARCHAR(100) NOT NULL,
    sede           VARCHAR(120),
    presupuesto    NUMERIC(12,2) NOT NULL DEFAULT 0,
    fecha_alta     DATE NOT NULL,
    logo_path      VARCHAR(200)
);

-- Tabla B: piloto // FK: eqpId_equipo (proviene de equipo.id_equipo)
-- Autorelación: pilId_piloto_mentor (proviene de piloto.id_piloto)
CREATE TABLE piloto (
    id_piloto            SERIAL PRIMARY KEY,
    eqpId_equipo         INTEGER NOT NULL REFERENCES equipo(id_equipo) ON DELETE CASCADE,
    nombre               VARCHAR(80) NOT NULL,
    nacionalidad         VARCHAR(50),
    fecha_nac            DATE,
    numero_coche         INTEGER,
    puntos_objetivo      INTEGER NOT NULL DEFAULT 100,   -- valor tope
    puntos_totales       INTEGER NOT NULL DEFAULT 0,     -- campo calculado desde C
    pilId_piloto_mentor  INTEGER REFERENCES piloto(id_piloto)
);

-- Tabla C: resultado // FK: pilId_piloto (proviene de piloto.id_piloto)
CREATE TABLE resultado (
    id_resultado     SERIAL PRIMARY KEY,
    pilId_piloto     INTEGER NOT NULL REFERENCES piloto(id_piloto) ON DELETE CASCADE,
    fecha_carrera    DATE NOT NULL,
    gran_premio      VARCHAR(80) NOT NULL,
    posicion         INTEGER NOT NULL,
    puntos_obtenidos INTEGER NOT NULL
);

-- Datos para las tablas
INSERT INTO equipo (usuario, password, nombre_equipo, sede, presupuesto, fecha_alta, logo_path) VALUES
('ferrari',  '1234', 'Scuderia Ferrari',   'Maranello',      520000000.00, '2025-12-01', NULL),
('mercedes', 'merc', 'Mercedes AMG F1',    'Brackley',       510000000.00, '2025-12-01', NULL),
('redbull',  'rbull', 'Oracle Red Bull',    'Milton Keynes',  550000000.00, '2025-12-01', NULL),
('mclaren',  'mclf1', 'McLaren F1 Team',    'Woking',         430000000.00, '2025-12-01', NULL),
('aston',    'martin', 'Aston Martin F1',    'Silverstone',    410000000.00, '2025-12-01', NULL);

INSERT INTO piloto (eqpId_equipo, nombre, nacionalidad, fecha_nac, numero_coche, puntos_objetivo, puntos_totales, pilId_piloto_mentor) VALUES
(1, 'Charles Leclerc',  'Mónaco',     '1997-10-16', 16, 150, 0, NULL),
(1, 'Carlos Sainz',     'España',     '1994-09-01', 55, 140, 0, 1),
(2, 'Lewis Hamilton',   'Reino Unido','1985-01-07', 44, 200, 0, NULL),
(2, 'George Russell',   'Reino Unido','1998-02-15', 63, 160, 0, 3),
(3, 'Max Verstappen',   'Países Bajos','1997-09-30', 1, 220, 0, NULL),
(3, 'Sergio Pérez',     'México',     '1990-01-26',11, 160, 0, 5),
(4, 'Lando Norris',     'Reino Unido','1999-11-13', 4, 150, 0, NULL),
(4, 'Oscar Piastri',    'Australia',  '2001-04-06',81, 130, 0, 7),
(5, 'Fernando Alonso',  'España',     '1981-07-29',14, 180, 0, NULL),
(5, 'Lance Stroll',     'Canadá',     '1998-10-29',18, 120, 0, 9);

INSERT INTO resultado (pilId_piloto, fecha_carrera, gran_premio, posicion, puntos_obtenidos) VALUES
(1, '2025-03-02', 'GP Bahréin', 2, 18),
(3, '2025-03-02', 'GP Bahréin', 1, 25),
(5, '2025-03-02', 'GP Bahréin', 3, 15),
(7, '2025-03-02', 'GP Bahréin', 4, 12),
(9, '2025-03-02', 'GP Bahréin', 6, 8),
(1, '2025-03-16', 'GP Arabia Saudí', 3, 15),
(3, '2025-03-16', 'GP Arabia Saudí', 2, 18),
(5, '2025-03-16', 'GP Arabia Saudí', 1, 25),
(8, '2025-03-16', 'GP Arabia Saudí', 5, 10),
(10,'2025-03-16', 'GP Arabia Saudí', 7, 6),
(2, '2025-03-30', 'GP Australia', 4, 12),
(4, '2025-03-30', 'GP Australia', 2, 18),
(6, '2025-03-30', 'GP Australia', 1, 25),
(8, '2025-03-30', 'GP Australia', 3, 15),
(10,'2025-03-30', 'GP Australia', 8, 4),
(2, '2025-05-11', 'GP España', 1, 25),
(4, '2025-05-11', 'GP España', 3, 15),
(6, '2025-05-11', 'GP España', 2, 18),
(9, '2025-05-11', 'GP España', 6, 8),
(10,'2025-05-11', 'GP España', 10, 1);

-- Inicializar puntos_totales de piloto
UPDATE piloto p
SET puntos_totales = COALESCE((
    SELECT SUM(r.puntos_obtenidos)
    FROM resultado r
    WHERE r.pilId_piloto = p.id_piloto
), 0);