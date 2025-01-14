<p align="center">
  <img src="https://upload.wikimedia.org/wikipedia/en/c/cc/JavaFX_Logo.png" alt="JAVAFX Logo">
</p>



## Sobre Carvia

Carvia es una aplicación desarrollada como parte de la asignatura de Ingeniería del Software. Esta herramienta permite a los usuarios comprar y vender coches de segunda mano de manera rápida y eficiente. Utiliza tecnologías modernas para la gestión de datos, almacenamiento de imágenes y una interfaz de usuario interactiva.

### Tecnologías Utilizadas

- **Lenguaje de Programación:** Java
- **Framework de Interfaz:** JavaFX
- **Patrón de Diseño:** Modelo-Vista-Controlador (MVC)
- **Gestor de Dependencias:** Maven
- **Base de Datos en la Nube:** Aiven
- **Almacenamiento de Imágenes:** Cloudinary

---

## Características Principales

- **Búsqueda y Filtrado:**
  - Búsqueda avanzada de coches por marca, modelo, año, precio y otros criterios.
  - Filtros dinámicos para refinar los resultados de búsqueda.

- **Publicación de Anuncios:**
  - Los usuarios pueden publicar anuncios con descripciones detalladas y fotos de los vehículos.
  - Integración con Cloudinary para almacenar y gestionar imágenes de alta calidad.

- **Seguridad y Gestión de Datos:**
  - La información se almacena de forma segura utilizando la base de datos en la nube Aiven.
  - Soporte para actualizaciones en tiempo real de los anuncios.

- **Interfaz de Usuario:**
  - Diseño intuitivo y amigable construido con JavaFX.
  - Navegación sencilla entre las diferentes secciones de la aplicación.

---

## Configuración e Instalación

### Requisitos del Sistema

- **Java Development Kit (JDK):** Versión 11 o superior
- **Maven:** Versión 3.6 o superior
- **Conexión a Internet:** Requerida para acceder a la base de datos y el almacenamiento en la nube

### Instrucciones

1. **Clonar el Repositorio:**

   ```bash
   git clone <https://github.com/Crisvi785/INSO_CARVIA.git>
   ```

2. **Configurar las Dependencias:**

   Asegúrate de tener Maven instalado y ejecuta:

   ```bash
   mvn install
   ```

3. **Configurar Variables de Entorno:**

   - **Base de Datos:** Agrega las credenciales de Aiven a un archivo `.env` o configura directamente en el código.
   - **Cloudinary:** Configura las claves de acceso para el bucket de almacenamiento.

4. **Ejecutar la Aplicación:**

   ```bash
   mvn javafx:run
   ```

---

## Estructura del Proyecto

El proyecto sigue una arquitectura MVC. A continuación, se describe la estructura principal:

```
src/
├── main/
│   ├── java/
│   │   ├── controller/    # Lógica de control
│   │   ├── model/         # Representación de datos
│   │   ├── view/          # Interfaces gráficas con JavaFX
│   ├── resources/         # Recursos como fxml, css, e imágenes
├── test/                  # Pruebas unitarias
pom.xml                    # Archivo de configuración de Maven
```

---

## Autores

- Cristian Álvarez 
- Aitor Fernandes
- Jairo Ugidos
- Javier Miñambres


---







