-- Insertar datos en la tabla TipoUva
INSERT INTO TipoUva (nombre, descripcion)
VALUES
    ('Malbec', 'Variedad de uva tinta originaria de Francia, conocida por su sabor afrutado y especiado.'),
    ('Cabernet Sauvignon', 'Uva tinta de gran adaptabilidad, conocida por su robustez y taninos intensos.'),
    ('Chardonnay', 'Variedad de uva blanca muy versátil, usada para hacer vinos frescos o con crianza en barrica.'),
    ('Syrah', 'Variedad de uva tinta originaria de la región del Ródano, conocida por su intensidad de sabor.'),
    ('Pinot Noir', 'Uva tinta delicada, famosa por su complejidad aromática.');

-- Insertar datos en la tabla Varietal
INSERT INTO Varietal (descripcion, porcentajeComposicion, tipoUva_id)
VALUES
    ('Blend de Malbec y Cabernet Sauvignon', 60, 1),
    ('Blend de Chardonnay y Sauvignon Blanc', 70, 3),
    ('Monovarietal de Syrah', 100, 4),
    ('Monovarietal de Pinot Noir', 100, 5);

-- Insertar datos en la tabla Maridaje
INSERT INTO Maridaje (nombre, descripcion)
VALUES
    ('Carne Roja', 'Marida perfectamente con vinos tintos como el Malbec o Cabernet Sauvignon.'),
    ('Pescado', 'Ideal con vinos blancos frescos como el Chardonnay.'),
    ('Pasta', 'Vinos como el Syrah combinan muy bien con platos de pasta.'),
    ('Queso', 'El Pinot Noir es ideal para acompañar quesos curados.'),
    ('Pollo', 'Los vinos tintos ligeros o blancos secos van muy bien con pollo.');

-- Insertar datos en la tabla Usuario
INSERT INTO Usuario (nombre, contrasenia, esPremium)
VALUES
    ('juan.perez', 'contrasenia123', TRUE),
    ('ana.gomez', '123456', FALSE),
    ('luis.martin', 'password321', TRUE),
    ('maria.sanchez', 'mypassword', FALSE),
    ('jose.lopez', 'qwerty123', TRUE);

-- Insertar datos en la tabla Enofilo
INSERT INTO Enofilo (nombre, apellido, imagenPerfil, usuario_id)
VALUES
    ('Juan', 'Perez', 'juan_perez.jpg', 1),
    ('Ana', 'Gomez', 'ana_gomez.jpg', 2),
    ('Luis', 'Martin', 'luis_martin.jpg', 3),
    ('Maria', 'Sanchez', 'maria_sanchez.jpg', 4),
    ('Jose', 'Lopez', 'jose_lopez.jpg', 5);

-- Insertar datos en la tabla Bodega
INSERT INTO Bodega (nombre, fechaUltimaActualizacion, peridisidadActualizacion, descripcion, coordenadasUbicacion, historia)
VALUES
    ('Bodega Valle de Uco', '2023-10-15', 6, 'Bodega ubicada en el corazón del Valle de Uco.', 40.123456, 'Historia rica en tradición vitivinícola.'),
    ('Bodega San Juan', '2023-09-25', 12, 'Famosa por sus vinos de clima cálido.', 39.876543, 'Innovación en técnicas de vinificación.'),
    ('Bodega Mendoza Sur', '2023-08-10', 18, 'Bodega que trabaja con variedades autóctonas.', 38.456789, 'Pasión por los vinos argentinos.');

-- Insertar datos en la tabla Siguiendo
INSERT INTO Siguiendo (fechaInicio, fechaFin, enofilo_id, bodega_id)
VALUES
    ('2023-01-01', '2023-12-31', 1, 1),
    ('2023-02-01', '2023-12-01', 2, 2),
    ('2023-03-01', NULL, 3, NULL),
    ('2023-04-01', '2023-06-30', 4, 3),
    ('2023-05-01', NULL, 5, NULL);

-- Insertar datos en la tabla Vino
INSERT INTO Vino (nombre, imagenEtiqueta, precioARS, notaCataBodega, aniada, bodega_id)
VALUES
    ('Malbec Reserva', 'malbec_reserva.jpg', 2500.00, 89.5, 2021, 1),
    ('Cabernet Sauvignon', 'cabernet_sauvignon.jpg', 2300.00, 85.3, 2020, 2),
    ('Chardonnay', 'chardonnay.jpg', 2800.00, 91.2, 2022, 3),
    ('Syrah', 'syrah.jpg', 2200.00, 88.1, 2021, 1),
    ('Pinot Noir', 'pinot_noir.jpg', 2700.00, 90.4, 2020, 2);


-- Insertar datos en la tabla Vino_Varietal (Relación muchos a muchos entre Vino y Varietal)
INSERT INTO Vino_Varietal (vino_id, varietal_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4);

-- Insertar datos en la tabla Vino_Maridaje (Relación muchos a muchos entre Vino y Maridaje)
INSERT INTO Vino_Maridaje (vino_id, maridaje_id)
VALUES
    (1, 1),
    (2, 4),
    (3, 2),
    (4, 3),
    (5, 5);
