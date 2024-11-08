CREATE TABLE TipoUva (
                         id SERIAL PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         descripcion TEXT
);

CREATE TABLE Varietal (
                          id SERIAL PRIMARY KEY,
                          descripcion VARCHAR(255) NOT NULL,
                          porcentajeComposicion INT NOT NULL CHECK (porcentajeComposicion BETWEEN 0 AND 100),
                          tipoUva_id INT UNIQUE REFERENCES TipoUva(id) ON DELETE SET NULL
);

CREATE TABLE Maridaje (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(100) NOT NULL,
                          descripcion TEXT
);

CREATE TABLE Usuario (
                         id SERIAL PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL UNIQUE,
                         contrasenia VARCHAR(100) NOT NULL,
                         esPremium BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE Enofilo (
                         id SERIAL PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         apellido VARCHAR(100) NOT NULL,
                         imagenPerfil VARCHAR(255),
                         usuario_id INT UNIQUE REFERENCES Usuario(id) ON DELETE CASCADE
);

CREATE TABLE Bodega (
                        id SERIAL PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        fechaUltimaActualizacion DATE NOT NULL,
                        peridisidadActualizacion INT NOT NULL,
                        descripcion TEXT,
                        coordenadasUbicacion DOUBLE PRECISION,
                        historia TEXT
);

CREATE TABLE Siguiendo (
                           id SERIAL PRIMARY KEY,
                           fechaInicio DATE NOT NULL,
                           fechaFin DATE,
                           enofilo_id INT NOT NULL REFERENCES Enofilo(id) ON DELETE CASCADE,
                           bodega_id INT REFERENCES Bodega(id) ON DELETE SET NULL
);

CREATE TABLE Vino (
                      id SERIAL PRIMARY KEY,
                      nombre VARCHAR(100) NOT NULL,
                      imagenEtiqueta VARCHAR(255),
                      precioARS DOUBLE PRECISION NOT NULL,
                      notaCataBodega DOUBLE PRECISION,
                      aniada INT,
                      bodega_id INT REFERENCES Bodega(id) ON DELETE SET NULL
);

CREATE TABLE Vino_Varietal (
                               vino_id INT REFERENCES Vino(id) ON DELETE CASCADE,
                               varietal_id INT REFERENCES Varietal(id) ON DELETE CASCADE,
                               PRIMARY KEY (vino_id, varietal_id)
);

-- Tabla Relacional Vino-Maridaje (Para relación de muchos a muchos entre Vino y Maridaje)
CREATE TABLE Vino_Maridaje (
                               vino_id INT REFERENCES Vino(id) ON DELETE CASCADE,
                               maridaje_id INT REFERENCES Maridaje(id) ON DELETE CASCADE,
                               PRIMARY KEY (vino_id, maridaje_id)
);