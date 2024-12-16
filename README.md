# Spotify Api Rest

## 1. Descripcion del proyecto

Este proyecto implementa una API REST para un pequeño Spotify en el que se incluyen funcionalidades para gestionar usuarios, canciones y playlist.

---

## **2. Descripcion de tablas**

### **Entidad Usuario**:
   - Representa un usuario.
   - Propiedades:
      - `idUsuario` (Int): Identificador único del usuario.
      - `username` (String): Nombre de usuario.
      - `password` (String): Contraseña de usuario.
      - `rol` (String): Rol de usuario (`USER`, `ADMIN` o `ARTIST`).

```sql
CREATE TABLE usuario (
    idUsuario INTEGER PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(10)
);
```

### **Entidad Cancion**:
Representa una cancion disponible en el sistema.
   - `idCancion` (Int): Identificador único de la canción.
   - `titulo` (String): Título de la canción.
   - `artista` (String): Artista de la canción.
   - `album` (String): Album al que pertenece la canción.
   - `fechaPublicacion` (LocalDate): Fecha de publicación de la canción.
   - `duracion` (Int): Duración en minutos de la canción.

```sql
CREATE TABLE cancion (
    idCancion INTEGER PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    artista VARCHAR(100) NOT NULL,
    album VARCHAR(100) NOT NULL,
    fechaPublicacion DATE NOT NULL,
    duracion INTEGER NOT NULL
);
```

### **Entidad Playlist**:
Representa una lista de reproduccion de las canciones.
   - `idPlaylist` (Int): Identificador único de la playlist.
   - `cancion` (List< Cancion >): Referencia a la cancion asociada.
   - `titulo` (String): Titulo de la playlist.
   - `breveDescripcion` (String): Breve descripción de la playlist.
   - `totalCanciones` (Int): Número total de canciones de la playlist.
   - `duracionTotal` (Int): Duracion total de la playlist.

```sql
CREATE TABLE playlist (
    id_playlist INTEGER PRIMARY KEY AUTO_INCREMENT,
    id_cancion INTEGER NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    breve_descripcion VARCHAR(255) NOT NULL,
    total_canciones INTEGER NOT NULL,
    duracion_total INTEGER NOT NULL
);
```

### Relacion entre entidades

- **Cancion y Playlist**: Relacion *Many-to-Many* en la que una cancion puede estar en varias playlists y una playlist puede tener varias canciones.
- **Aclaracion**: En una relacion Many-to-Many, aunque no genere explicitamente la tabla, JPA se encarga de crearla automáticamente con las configuraciones definidas en las entidades.
```sql
CREATE TABLE playlists_canciones (
    id_playlist INTEGER NOT NULL,
    id_cancion INTEGER NOT NULL,
    PRIMARY KEY (id_playlist, id_cancion),
    CONSTRAINT fk_playlist FOREIGN KEY (id_playlist) REFERENCES playlists (id_playlist) ON DELETE CASCADE,
    CONSTRAINT fk_cancion FOREIGN KEY (id_cancion) REFERENCES canciones (id_cancion) ON DELETE CASCADE
);
```

---

## 3. Resumen de los endpoints

### Endpoints para Usuarios
- **POST** `/usuarios/login`
  - El usuario inicia sesion con sus datos
  - *RUTA PÚBLICA* (Cualquier usuario puede acceder a este endpoint)

- **POST** `/usuarios/register`
   - El usuario se registra en la base de datos
   - *RUTA PÚBLICA* (Cualquier usuario puede acceder a este endpoint)

- **GET** `/usuarios/obtenerUsuarios`
  - Devuelve una lista de todos los usuarios
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)

- **GET** `/usuarios/obtenerUsuarios/{id}`
  - Devuelve un usuario por su identificador
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)

- **PUT** `/usuarios/updateUsuario/{id}`
  - Actualiza un usuario por su identificador
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)

- **DELETE** `/usuarios/borrarUsuario/{id}`
  - Borra un usuario por su identificador
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)

### Endpoints para Canciones
*RUTA PROTEGIDA* Todas las rutas requieren que el usuario esté autenticado para acceder a las mismas.

- **GET** `/canciones/obtenerCanciones`
  - Devuelve una lista de todas las canciones
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **GET** `/canciones/obtenerCanciones/{id}`
  - Devuelve una canción por su identificador
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **POST** `/canciones/crearCancion`
  - Crea una nueva cancion
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **PUT** `/canciones/actualizarCancion/{id}`
  - Actualiza una cancion por su identificador
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **DELETE** `/canciones//borrarCancion/{id}`
  - Borra una cancion por su identificador
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)

### Endpoints para Playlist
*RUTA PROTEGIDA* Todas las rutas requieren que el usuario esté autenticado para acceder a las mismas.

- **GET** `playlists/obtenerPlaylists`
  - Devuelve una lista de todas las playlists
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **GET** `/playlists/obtenerPlaylist/{id}`
  - Devuelve la playlist por id
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **GET** `/playlists/obtenerPlaylist/{id}/{titulo}`
  - Devuelve una cancion de la playlist
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **POST** `/playlists/crearPlaylist`
  - Crea una playlist
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **PUT** `/playlists/actualizarPlaylist/{id}`
  - Actualiza la playlist por identificador
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **PUT** `/playlists/agregarCancion/{id}`
  - Agrega una cancion a la playlist
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **DELETE** `/playlists/borrarPlaylist/{id}`
  - Borra una playlist por identificador
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

- **DELETE** `/playlists/borrarCancion/{idPlaylist}/{idCancion}`
  - Borra una cancion de la playlist
  - **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - **ROL USER** (Usuarios con `ROL USER` pueden acceder al recurso libremente)
  - **ROL ARTIST** (Usuarios con `ROL ARTIST` pueden acceder al recurso libremente)

---

## 4. Validaciones

| Campo                                                          | Regla de Validación                      | Código HTTP | Mensaje de Error                                                         |
|----------------------------------------------------------------|------------------------------------------|-------------|--------------------------------------------------------------------------|
| `username`, `username`, `breveDescripcion`, `artista`, `album` | No puede estar vacio                     | 400         | "El campo (nombre_campo) debe estar vacío."                              |
| `totalCanciones`, `duracionTotal`, `duracion`                  | No puede ser menor que 0.                | 400         | "El campo (total canciones) no puede ser negativo."                      |
| `cancion`                                                      | No puede ser nulo.                       | 400         | "Playlist's song can not be null."                                       |
| `fechaPublicacion`                                             | No puede ser despues de la fecha actual. | 400         | "El campo (fecha publicacion) no puede ser posterior a la fecha actual." |

---

## 5. SQL de insercion de datos de prueba

```sql
INSERT INTO canciones (id_cancion, titulo, artista, album, duracion, fecha_publicacion)
VALUES 
    (1, 'Cancion Uno', 'Artista A', 'Album X', 210, '2023-05-01'),
    (2, 'Cancion Dos', 'Artista B', 'Album Y', 180, '2023-06-15'),
    (3, 'Cancion Tres', 'Artista C', 'Album Z', 240, '2023-07-10'),
    (4, 'Cancion Cuatro', 'Artista D', 'Album W', 200, '2023-08-20'),
    (5, 'Cancion Cinco', 'Artista E', 'Album V', 230, '2023-09-05');

INSERT INTO playlists (id_playlist, titulo, breve_descripcion, total_canciones, duracion_total)
VALUES 
    (1, 'Hits del Verano', 'Las canciones más populares del verano para disfrutar en la playa o la piscina.', 15, 3600),
    (2, 'Relax y Chill', 'Música relajante para desconectar después de un día largo.', 20, 5400),
    (3, 'Rock Clásico', 'Los mejores éxitos del rock clásico que nunca pasan de moda.', 12, 4200);
```


