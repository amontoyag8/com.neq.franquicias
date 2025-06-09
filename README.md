# Prueba Nequi Franquicias

Aplicación web para la administracion de franquicias, sucursales, productos y stock de productos en sucursal, desarrollada con **Spring Boot** y conectada a una base de datos relacional sobre el RDS **Amazon Aurora MySQL**.

---

## Tabla de Contenidos

1. [Descripción](#descripción)
2. [Características](#características)
3. [Tecnologías Utilizadas](#tecnologías-utilizadas)
4. [Requisitos Previos](#requisitos-previos)
5. [Instalación](#instalación)
6. [Ejecución del Proyecto](#ejecución-del-proyecto)
7. [Endpoints](#endpoints)

---

## Descripción

Sistema de administracion de franquicias que permite:

- Agregar, actualizar y listar franquicias, sucursales y productos.
- Asignar productos a sucursales y gestionar su stock.
- Eliminar productos de sucursales.
- Consultar productos con mayor stock por sucursal de una franquicia.
- Actualizar nombres de franquicias, sucursales y productos.

La aplicación utiliza el manejo eficiente y reactivo de solicitudes concurrentes por medio de Spring Webflux.

---

## Características

- Conexión a base de datos **Amazon Aurora MySQL**.
- Endpoints REST para gestionar franquicias, sucursales, productos y existencias.
- Acceso a datos eficiente mediante patrones reactivos.

---

## Tecnologías Utilizadas

- **Spring WebFlux**: Framework reactivo para aplicaciones web asíncronas.
- **Spring Data R2DBC**: Acceso reactivo a bases de datos relacionales.
- **Amazon Aurora MySQL**: Base de datos gestionada en AWS.
- **Java 17** o superior.
- **Gradle**: Gestión de dependencias y construcción del proyecto.
- **IntelliJ Community Edition**: IDE recomendado para el desarrollo.

---

## Requisitos Previos

- **JDK 21** o superior.
- **Gradle 8.14** o superior.
- **DBeaver** para visualizar la base de datos.

---

## Instalación

1. **Clonar el repositorio**  
   ```bash
   git clone https://github.com/amontoyag8/com.neq.franquicias.git
   ```

2. **Construiccion del proyecto**
   Ubicarse en la carpeta del proyecto y ejecuta:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

3. **Configurar la base de datos**  
   - La base de datos está alojada en AWS Aurora RDS en una instancia publica que estara disponible en las reglas mediante esta prueba sea evaluada.
   - Las credenciales y la URL de conexión se encuentran en el archivo `application.properties` dentro de la carpeta `resources`.

4. **Herramienta para pruebas de endpoints**  
   - Se recomienda usar **Postman**.
   - Adjunta una colección de Postman con todos los endpoints configurados en la carpeta de recursos del proyecto.

---

## Ejecución del Proyecto

Ejecuta el siguiente comando en la raíz del proyecto:

```bash
./gradlew bootRun
```

El servidor quedará disponible en `http://localhost:8080`.

---

## Endpoints

A continuación, los CURL con los ejemplos de uso de cada uno de los endpoints del API:

### Crear franquicia

```bash
curl --location 'http://localhost:8080/api/franquicias' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "KFC"
}'
```

### Crear sucursal

```bash
curl --location 'http://localhost:8080/api/sucursales' \
--header 'Content-Type: application/json' \
--data '{
  "nombre": "Frisby Santa Fe",
  "franquiciaId": 6
}'
```

### Crear producto

```bash
curl --location 'http://localhost:8080/api/productos' \
--header 'Content-Type: application/json' \
--data '{
  "nombre": "Gaseosa"
}'
```

### Crear stock de producto en sucursal

```bash
curl --location 'http://localhost:8080/api/productos-sucursal' \
--header 'Content-Type: application/json' \
--data '{
  "productoId": 11,
  "sucursalId": 6,
  "stock": 1000
}'
```

### Eliminar producto de sucursal

```bash
curl --location --request DELETE 'http://localhost:8080/api/productos-sucursal?productoId=1&sucursalId=1'
```

### Actualizar stock de producto

```bash
curl --location --request PUT 'http://localhost:8080/api/productos-sucursal/actualizar-stock?productoId=11&sucursalId=10&stock=5000'
```

### Obtener producto con más stock por sucursal de una franquicia

```bash
curl --location 'http://localhost:8080/api/productos-sucursal/prod-stock-mas/6'
```

### Actualizar nombre de franquicia

```bash
curl --location --request PUT 'http://localhost:8080/api/franquicias/8' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Llanerito"
}'
```

### Actualizar nombre de sucursal

```bash
curl --location --request PUT 'http://localhost:8080/api/sucursales/1' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Sucursal Nueva"
}'
```

### Actualizar nombre de producto

```bash
curl --location --request PUT 'http://localhost:8080/api/productos/13' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Frischuleta"
}'
```

### Obtener todas las franquicias

```bash
curl --location 'http://localhost:8080/api/franquicias/all'
```

### Obtener sucursales por franquicia

```bash
curl --location 'http://localhost:8080/api/sucursales/allByFranquisiaId/6'
```

### Obtener todos los productos

```bash
curl --location 'http://localhost:8080/api/productos/all'
```