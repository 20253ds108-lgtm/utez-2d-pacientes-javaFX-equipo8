Sistema de Directorio de Pacientes (Consultorio)
Asignatura: Programación Orientada a Objetos
Tecnología: Java + JavaFX
Grupo: 2°D
Integrantes: Aguilar Hernandez Judith Desiree y Carranza Priego Naomy Sayuri

Descripción del Proyecto
Este sistema es una aplicación de escritorio diseñada para un consultorio médico pequeño. Permite gestionar un directorio de pacientes mediante un CRUD completo con persistencia de datos en archivos locales.
El sistema implementa un "Borrado Lógico", lo que significa que los pacientes no se eliminan físicamente del archivo, sino que cambian su estatus a INACTIVO, permitiendo mantener un historial clínico íntegro.
Funcionalidades Principales
Gestión de Pacientes: Alta, consulta en tabla, edición de datos y cambio de estatus.
Validaciones: * CURP único (evita duplicados).
Nombre completo (mínimo 5 caracteres).
Edad válida (rango de 0 a 120 años).
Teléfono de 10 dígitos.
Resumen Automático: Contador de pacientes Totales, Activos e Inactivos.
Estructura del Código
El proyecto sigue el patrón de diseño Modelo-Vista-Controlador para separar la lógica de la interfaz:
Clase Paciente que define los atributos del sistema.
FileRepository encargado exclusivamente de leer y escribir en el archivo.
PacienteService donde reside la lógica de negocio, validaciones y cálculos.
Appcontroller manejan los eventos de JavaFX y la interacción con el usuario.

Cómo Ejecutar el Proyecto
Para correr la aplicación en su entorno local, sigan estos pasos:

Clonar el repositorio:
git clone https://github.com/20253ds108-lgtm/utez-2d-pacientes-javaFX-equipo8.git

Abrir el proyecto: Importar como proyecto Maven/Gradle en IntelliJ IDEA o NetBeans.

Configurar el SDK: Asegurarse de tener instalado JDK 21

Ejecutar: Correr la clase HelloApplication