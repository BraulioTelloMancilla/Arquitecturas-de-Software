# Arquitecturas-de-Software
## Integrantes del equipo:
- Ivan Alejandro Chi Polanco
<img width="231" height="216" alt="IMG_4894" src="https://github.com/user-attachments/assets/666ed8a9-37f3-4c46-a4db-a5508422f9d7" />


- Omar Sebastian Chim Canche
<img width="231" height="216" alt="image" src="https://github.com/user-attachments/assets/3b411f77-7ba6-4ffc-87c8-dd40632af937" />

- Braulio Tello Mancilla
<img width="231" height="216" alt="image" src="https://github.com/user-attachments/assets/6aa365b5-d562-4076-b636-36a54d214c54" />

## Base de datos

La aplicación usa MySQL (o MariaDB, el que incluye XAMPP) en `localhost:3306`.

Tabla `empleados`:

| Campo     | Tipo            | Descripción              |
|-----------|-----------------|--------------------------|
| `id`      | `BIGINT`        | Identificador único (PK) |
| `nombre`  | `VARCHAR(100)`  | Nombre del empleado      |
| `salario` | `DECIMAL(10,2)` | Salario (no negativo)    |

### Crear la base de datos

Ejecuta los scripts de la carpeta `database/` en este orden:

1. `schema.sql`: crea la base `empleados_db` y la tabla `empleados` (si la tabla ya existe, la borra).
2. `data.sql`: inserta registros de ejemplo.

**Opción A: MySQL Workbench o phpMyAdmin.** Abre cada archivo y ejecútalo.

**Opción B: terminal.**

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/data.sql
```

Con XAMPP en macOS, el cliente está en `/Applications/XAMPP/xamppfiles/bin/mysql`.

### Configurar la conexión

Por defecto la aplicación se conecta como `root` sin contraseña. Si tu MySQL usa otras credenciales, define estas variables de entorno antes de ejecutarla (no modifiques `application.properties`):

```bash
export DB_USER=root
export DB_PASSWORD=tu_contraseña
```

En Windows (PowerShell): `$env:DB_PASSWORD="tu_contraseña"`.
