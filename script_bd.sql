CREATE TABLE participantes (
    id           SERIAL       PRIMARY KEY,
    cedula       VARCHAR(10)  NOT NULL UNIQUE,
    nombre       VARCHAR(100) NOT NULL,
    apellido     VARCHAR(100) NOT NULL,
    edad         INTEGER      NOT NULL CHECK (edad > 5),
    correo       VARCHAR(150) NOT NULL UNIQUE,
    estado_civil VARCHAR(20)  NOT NULL,
    jornada      VARCHAR(20)  NOT NULL,
    categoria    VARCHAR(50)  NOT NULL,
    observaciones TEXT
);


INSERT INTO participantes (cedula, nombre, apellido, edad, correo, estado_civil, jornada, categoria, observaciones)
VALUES
    ('1712345678', 'Carlos',   'Pérez',    22, 'carlos.perez@email.com',   'Soltero',    'Matutina',   'Junior',      'Buen tiempo en 100m'),
    ('1798765432', 'Lucía',    'Morales',  17, 'lucia.morales@email.com',  'Soltero',    'Vespertina', 'Infantil',    'Participante nueva'),
    ('1756781234', 'Andrés',   'Torres',   30, 'andres.torres@email.com',  'Casado',     'Nocturna',   'Senior',      'Especialidad: mariposa'),
    ('1723456789', 'Valeria',  'Gómez',    25, 'valeria.gomez@email.com',  'Divorciado', 'Matutina',   'Master',      ''),
    ('1767891234', 'Miguel',   'Salazar',  14, 'miguel.salazar@email.com', 'Soltero',    'Vespertina', 'Juvenil',     'Promesa del club');
