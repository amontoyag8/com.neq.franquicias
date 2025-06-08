# Prueba Nequi Franquicias

Aplicación web para la gestión de franquicias, sucursales y productos, desarrollada con **Spring WebFlux** y conectada a una base de datos relacional en **Amazon Aurora MySQL**.

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

Sistema de gestión de franquicias que permite:

- Agregar, actualizar y listar franquicias, sucursales y productos.
- Asignar productos a sucursales y gestionar su stock.
- Eliminar productos de sucursales.
- Consultar productos con mayor stock por sucursal de una franquicia.
- Actualizar nombres de franquicias, sucursales y productos.

La aplicación utiliza **Spring WebFlux** para un manejo eficiente y reactivo de solicitudes concurrentes.

---

## Características

- Conexión a base de datos **Amazon Aurora MySQL**.
- Endpoints REST para gestionar franquicias, sucursales y productos.
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
- **Gradle**.
- **MySQL** o **DBeaver** para visualizar la base de datos (opcional).

---

## Instalación

1. **Clonar el repositorio**  
   ```bash
   git clone https://github.com/CamiloAGarciaMorales/franquicias.git
   ```

2. **Construir el proyecto**  
   Ubícate en la carpeta del proyecto y ejecuta:
   ```bash
   ./gradlew clean
   ./gradlew build
   ```

3. **Configurar la base de datos**  
   - La base de datos está alojada en AWS Aurora RDS.
   - Las credenciales y la URL de conexión se encuentran en el archivo `application.properties` dentro de la carpeta `resources`.

4. **Herramienta para pruebas de endpoints**  
   - Se recomienda usar **Postman**.
   - Se adjunta una colección de Postman con todos los endpoints configurados en la carpeta de recursos del proyecto.

---

## Ejecución del Proyecto

Ejecuta el siguiente comando en la raíz del proyecto:

```bash
./gradlew bootRun
```

El servidor quedará disponible en `http://localhost:8080`.

---

## Endpoints

A continuación, algunos ejemplos de uso de los endpoints principales:

### Agregar franquicia

```bash
curl --location 'http://localhost:8080/api/franquicias' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Franquicia H",
    "direccion": "Calle 321, Ibague"
}'
```

### Agregar sucursal

```bash
curl --location 'http://localhost:8080/api/sucursales' \
--header 'Content-Type: application/json' \
--data '{
  "nombre": "Sucursal C",
  "direccion": "Calle 789, Ciudad",
  "franquiciaId": 1
}'
```

### Agregar producto

```bash
curl --location 'http://localhost:8080/api/productos' \
--header 'Content-Type: application/json' \
--data '{
  "nombre": "Lapiceros",
  "descripcion": "Lapicero de tinta color negro",
  "precio": 3.0
}'
```

### Asignar producto a sucursal

```bash
curl --location 'http://localhost:8080/api/sucursal-producto' \
--header 'Content-Type: application/json' \
--data '{
  "productoId": 4,
  "sucursalId": 3,
  "stock": 1000
}'
```

### Eliminar producto de sucursal

```bash
curl --location --request DELETE 'http://localhost:8080/api/sucursal-producto?productoId=1&sucursalId=1'
```

### Actualizar stock de producto

```bash
curl --location --request PUT 'http://localhost:8080/api/sucursal-producto/actualizar-stock?productoId=1&sucursalId=1&stock=50'
```

### Listar producto con más stock por sucursal de una franquicia

```bash
curl --location 'http://localhost:8080/api/sucursal-producto/1/productos-mas-stock'
```

### Actualizar nombre de franquicia

```bash
curl --location --request PUT 'http://localhost:8080/api/franquicias/cambio-nombre/1' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Franquicia Coca-Cola"
}'
```

### Actualizar nombre de sucursal

```bash
curl --location --request PUT 'http://localhost:8080/api/sucursales/cambio-nombre/1' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Sucursal Nueva"
}'
```

### Actualizar nombre de producto

```bash
curl --location --request PUT 'http://localhost:8080/api/productos/cambio-nombre/1' \
--header 'Content-Type: application/json' \
--data '{
    "nombre": "Producto Actualizado"
}'
```

### Listar todas las franquicias

```bash
curl --location 'http://localhost:8080/api/franquicias/all'
```

### Listar sucursales por franquicia

```bash
curl --location 'http://localhost:8080/api/sucursales/allByFranquisiaId/1'
```

### Listar todos los productos

```bash
curl --location 'http://localhost:8080/api/productos/all'
```

---

**Nota:**  
Para mayor comodidad, consulta la colección de Postman incluida en los recursos del proyecto.

---