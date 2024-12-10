# Spotify Api Rest

## 1. Descripcion del proyecto


## **2. Descripcion de tablas**

1. **Entidad Usuario**:
   - Representa un usuario.
   - Propiedades:
      - `idUsuario` (Int): Identificador único del usuario.
      - `username` (String): Nombre de usuario.
      - `password` (String): Contraseña de usuario.
      - `rol` (String): Rol de usuario (`USER`, `ADMIN` o `ARTIST`).

```kotlin
data class Usuario(
    val idUsuario: Int,
    val username: String,
    val password: String,
    val rol: String
)
```

```sql
CREATE TABLE usuario (
    idUsuario INTEGER PRIMARY KEY,
    username VARCHAR(15) NOT NULL,
    password VARCHAR(50) NOT NULL,
    rol VARCHAR(5) NOT NULL
);
```
   
2. **Entidad Cancion**:
   - `idCancion` (Int): Identificador único de la canción.
   - `titulo` (String): Título de la canción.
   - `artista` (String): Artista de la canción.
   - `album` (String): Album al que pertenece la canción.
   - `fechaPublicacion` (LocalDate): Fecha de publicación de la canción.
   - `duracion` (Int): Duración en minutos de la canción.

```kotlin
data class Cancion(
    val idCancion: Int,
    val titulo: String,
    val artista: String,
    val album: String,
    val fechaPublicacion: LocalDate,
    val duracion: Int
)
```

```sql
CREATE TABLE usuario (
    idCancion INTEGER PRIMARY KEY,
    titulo VARCHAR(15) NOT NULL,
    artista VARCHAR(15) NOT NULL,
    album VARCHAR(20) NOT NULL,
    fechaPublicacion DATE NOT NULL,
    duracion INTEGER NOT NULL
);
```

3. **Entidad Playlist**:
   - `idPlaylist` (Int): Identificador único de la playlist.
   - `cancion` (Cancion): Referencia a la cancion asociada.
   - `titulo` (String): Titulo de la playlist.
   - `breveDescripcion` (String): Breve descripción de la playlist.
   - `totalCanciones` (Int): Número total de canciones de la playlist.
   - `duracionTotal` (Int): Duracion total de la playlist.

```kotlin
data class Playlist(
    val idPlaylist: Int,
    val cancion: Cancion,
    val titulo: String,
    val breveDescripcion: String,
    val totalCanciones: Int,
    val duracionTotal: Int
)
```

```sql
CREATE TABLE usuario (
    id_playlist INTEGER PRIMARY KEY,
    id_cancion INTEGER NOT NULL,
    titulo VARCHAR(15) NOT NULL,
    breve_descripcion VARCHAR(200) NOT NULL,
    total_canciones INTEGER NOT NULL,
    duracion_total INTEGER NOT NULL,
   CONSTRAINT FK_Cancion FOREIGN KEY (id_cancion) REFERENCES cancion(id_cancion) ON DELETE CASCADE
);
```

## 3. Resumen de los endpoints

### Endpoints para Usuarios
- **POST** `/usuarios/login`
  - El usuario inicia sesion con sus datos
  - *RUTA PÚBLICA* (Cualquier usuario puede acceder a este endpoint)
- **POST** `/usuarios/register`
   - El usuario se registra en la base de datos
   - *RUTA PÚBLICA* (Cualquier usuario puede acceder a este endpoint)

### Endpoints para Canciones
- **GET** `/canciones`
  - Devuelve una lista de todas las canciones
  - *RUTA PROTEGIDA* **ADMIN** (Solo usuarios con `ROL ADMIN` pueden acceder a este recurso)
- **GET** `/canciones/{idCancion}`
  - Devuelve una canción por su identificador
  - *RUTA PROTEGIDA* **AUTHENTICATED ROL USER** (Solo usuarios con `ROL USER` que estén registrados correctamente podrán acceder a este recurso)
  - *RUTA PROTEGIDA* **ROL ADMIN** (Usuarios con `ROL ADMIN` pueden acceder al recurso libremente)
  - *RUTA PROTEGIDA* **AUTHENTICATED ROL ARTIST** (Solo usuarios con `ROL ARTIST` que estén registrados correctamente podrán acceder a este recurso)
- **POST** `/canciones`
  - Crea una nueva cancion
  - *RUTA PROTEGIDA* **ADMIN** (Solo usuarios con `ROL ADMIN` pueden acceder a este recurso)
  - *RUTA PROTEGIDA* **AUTHENTICATED ROL ARTIST** (Solo usuarios con `ROL ARTIST` que estén registrados correctamente podrán acceder a este recurso)
- **PUT** `/canciones/{idCancion}`
  - Actualiza una cancion por su identificador
  - *RUTA PROTEGIDA* **ADMIN** (Solo usuarios con `ROL ADMIN` pueden acceder a este recurso)
  - *RUTA PROTEGIDA* **AUTHENTICATED ROL ARTIST** (Solo usuarios con `ROL ARTIST` que estén registrados correctamente podrán acceder a este recurso)
- **DELETE** `/canciones/{idCancion}`
  - Borra una cancion por su identificador
  - *RUTA PROTEGIDA* **ADMIN** (Solo usuarios con `ROL ADMIN` pueden acceder a este recurso)

### Endpoints para Playlist
- **GET** `/playlist`
  - Devuelve una lista de todas las playlist
  - *RUTA PROTEGIDA* **ADMIN** (Los usuarios con `ROL ADMIN` pueden ver todas las playlist)
- **GET** `/playlist/{idPlaylist}`
  - Devuelve la playlist por id
  - *RUTA PROTEGIDA* **ROL USER** (Los usuarios con `ROL USER` que sean propietarios de la playlist pueden buscar una playlist)
  - *RUTA PROTEGIDA* **ROL ARTIST** (Los usuarios con `ROL ARTIST` que estén registrados correctamente podrán buscar una playlist)
- **GET** `/playlist/{idPlaylist}/cancion`
  - Añade una cancion a la playlist
  - *RUTA PROTEGIDA* **AUTENTICATED** **ROL USER** (Solo usuarios con `ROL USER` que sean propietarios de la playlist pueden acceder a este recurso)
- **POST** `/playlist`
  - Crea una playlist
  - *RUTA PROTEGIDA* **ROL USER** (Solo usuarios con `ROL USER` pueden acceder a este recurso)
- **PUT** `/playlist/{idPlaylist}`
  - Actualiza la playlist por identificador
  - *RUTA PROTEGIDA* **ROL USER** (Solo usuarios con `ROL USER` que sean propietarios de la playlist pueden editar la playlist)
- **DELETE** `/playlist/{idPlaylist}`
  - Borra una playlist por identificador
  - *RUTA PROTEGIDA* **ROL USER** (Los usuarios con `ROL USER` que sean propietarios de la playlist pueden borrarla)
  - *RUTA PROTEGIDA* **ADMIN** (Los usuarios con `ROL ADMIN` pueden eliminar cualquie playlist en caso de moderacion)

## 4. Validaciones


## 5. Restricciones de seguridad
