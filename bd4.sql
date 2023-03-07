# Muestra el nombre y apellidos de todos los profesores que tengan la categoría de 'AYUDANTE'
SELECT p.nombre, p.apellido_1, p.apellido_2
FROM profesor p, categoria c
WHERE p.id_categoria=c.codigo AND c.nombre='AYUDANTE';

#Muestra todos los profesores cuyo salario esté entre 4.000.000 y 2.000.000
SELECT p.*
FROM profesor p, categoria c
WHERE p.id_categoria=c.codigo AND c.salario >= 2000000 AND c.salario <= 4000000; 

#Muestra el nombre y apellidos junto con la categoría de los profesores que ingresaron en los 90
SELECT p.nombre, p.apellido_1, p.apellido_2, p.fecha_ingreso, c.*
FROM profesor p, categoria c
WHERE p.id_categoria=c.codigo AND fecha_ingreso BETWEEN '1989-12-31' AND '2000-01-01';

#Muestra los distintos departamentos que están asociados con los profesores.
SELECT d.*
FROM profesor p, departamento d
WHERE p.id_departamento = d.codigo
GROUP BY d.codigo;

#Muestra aquellos departamentos que no tiene ningún profesor asociado.
SELECT d.*
FROM profesor p, departamento d
WHERE d.codigo NOT IN (SELECT id_departamento FROM profesor)
GROUP BY d.codigo;

#Muestra el número de profesores que conforma cada departamento.
SELECT d.codigo, COUNT(*) AS nProfesores
FROM profesor p, departamento d
WHERE p.id_departamento = d.codigo
GROUP BY d.codigo;

#Muestra el número total de departamentos junto con el número de departamentos que no tiene ningún profesor asociado.
SELECT COUNT(*) AS nDepartamentosTotal, (SELECT COUNT(*) FROM departamento d WHERE d.codigo NOT IN (SELECT id_departamento FROM profesor)) AS nDepartamentosSinProfesor
FROM departamento;

#Muestra el nombre del profesor junto con el nombre de las asignaturas que cursa.
SELECT p.nombre, a.nombre
FROM profesor_asignatura pa, profesor p, asignatura a 
WHERE p.dni = pa.id_profesor AND a.codigo = pa.id_asignatura;

#Muestra el nombre y apellidos de cada profesor junto con el número de asignaturas que cursa
SELECT p.nombre, p.apellido_1, p.apellido_2, COUNT(*) AS nAsignaturas
FROM profesor p, asignatura a, profesor_asignatura pa
WHERE pa.id_profesor = p.dni AND a.codigo = pa.id_asignatura
GROUP BY p.dni;

#Muestra el número de asignaturas optativas y el número de las no optativas.
SELECT optativa, COUNT(*)
FROM asignatura
WHERE optativa IS NOT NULL
GROUP BY optativa;

#Muestra las asignaturas de primero junto con el nombre del departamento al que pertenecen
SELECT a.*, d.nombre AS nombreDepartamento
FROM asignatura a, departamento d
WHERE a.id_departamento = d.codigo AND a.curso=1;

#Muestra las asignaturas cuyo cod. Postal del departamento es 37007
SELECT a.*
FROM asignatura a, departamento d
WHERE a.id_departamento = d.codigo AND d.codigo_postal = '37007';

#Muestra el nombre de los departamentos que participan en el cuarto curso.
SELECT d.nombre
FROM departamento d, asignatura a
WHERE d.codigo = a.id_departamento AND a.curso=4
GROUP BY d.nombre;

#Muestra el nombre y apellidos del profesor, el nombre de la asignatura y el teléfono del
#departamento de las asignaturas que imparte el profesor con DNI 71025489 y además
#pertenecen al departamento “ MAT. PURA Y APLICADA”.
SELECT p.nombre, p.apellido_1, p.apellido_2, a.nombre, d.telefono
FROM profesor p, asignatura a, departamento d, profesor_asignatura pa
WHERE p.dni='71025489' AND d.nombre='MAT. PURA Y APLICADA' AND a.id_departamento = d.codigo AND pa.id_profesor = p.dni AND pa.id_asignatura = a.codigo;

#Muestra el nombre de las categorías de los profesores que imparten la asignatura cuyodepartamento es “MAT. PURA Y APLICADA”.
SELECT c.nombre
FROM categoria c, profesor p, asignatura a, departamento d, profesor_asignatura pa
WHERE pa.id_profesor = p.dni AND a.codigo = pa.id_asignatura AND a.id_departamento=d.codigo AND d.nombre = 'MAT. PURA Y APLICADA'
GROUP BY c.nombre;

#Muestra el nombre y apellidos de los alumnos que hayan aprobado la asignatura AUTOMATICA I en junio o septiembre.
SELECT al.nombre, al.apellido_1, al.apellido_2
FROM alumno al, asignatura a, alumno_asignatura aa
WHERE al.dni = aa.id_alumno AND a.codigo=aa.id_asignatura AND (aa.calificacion_junio>=5 OR aa.calificacion_septiembre>=5) AND a.nombre = 'AUTOMATICA I';

#Muestra el nombre y apellidos de los alumnos junto con el nombre de la asignatura que se han matriculado en el 2000 y no han aprobado.
SELECT al.nombre, al.apellido_1, al.apellido_2, a.nombre
FROM alumno al, asignatura a, alumno_asignatura aa
WHERE aa.id_alumno = al.dni AND aa.id_asignatura = a.codigo AND aa.anno = 2000 AND (aa.calificacion_junio < 5 OR  aa.calificacion_septiembre < 5);

#Muestra los alumnos que han suspendido en junio y son de salamanca
SELECT a.*
FROM alumno a, alumno_asignatura aa
WHERE a.dni = aa.id_alumno AND localidad='salamanca' AND aa.calificacion_junio<5
GROUP BY a.dni;

#Muestra el nombre de asignatura, de carrera y departamento que sean optativas y de una licenciatura (¿Que es una licenciatura?).
SELECT a.nombre, c.nombre, d.nombre
FROM asignatura a, carrera c, departamento d
WHERE a.id_carrera = c.codigo AND a.id_departamento = d.codigo AND a.optativa='S';

#Muestra el nombre de las carreras indicando cuantas asignatura tiene optativas y obligatorias (¿Lo de que sea obligatoria donde vergas esta?).
SELECT c.nombre, (SELECT COUNT(*) FROM asignatura WHERE optativa='S') AS nOptativas, (SELECT COUNT(*) FROM asignatura WHERE optativa='N') AS nNoOptativas
FROM carrera c, asignatura a
WHERE c.codigo = a.id_carrera
GROUP BY c.nombre;