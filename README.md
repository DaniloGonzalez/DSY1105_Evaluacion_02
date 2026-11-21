# Master Martini

Aplicación móvil de demostración desarrollada para el ramo **Desarrollo de Aplicaciones Móviles (DSY1105)**. La idea del proyecto es presentar contenido y recursos para personas relacionadas con la pastelería y la gastronomía, en una app Android con navegación entre pantallas, registro local y contenido de ejemplo.

## Integrantes

**Equipo 12**

- **EDIXON ELIASAF ELIAS VILLAFANE**
- **DANILO ALEJANDRO GONZALEZ ARCE**

## Tecnologías utilizadas

- Kotlin
- Jetpack Compose y Material 3
- Arquitectura MVVM
- Navigation Compose
- StateFlow para observar y actualizar el estado de las pantallas
- DataStore Preferences para guardar datos simples en el dispositivo
- Media3 para el componente de contenido audiovisual

## Funcionalidades implementadas

- Pantalla de bienvenida con transición al flujo de acceso.
- Registro local de una cuenta y pantalla de inicio de sesión.
- Validaciones de campos del registro e inicio de sesión, incluyendo correo, contraseña, teléfono chileno con prefijo y RUT.
- Persistencia local de la cuenta, los intereses y el estado de sesión. La aplicación no utiliza un backend.
- Mantención de la sesión al volver a abrir la aplicación y opción para cerrar sesión desde el perfil.
- Cuestionario para indicar el tipo y tamaño del negocio, además de los intereses de contenido.
- Pantalla de inicio con contenido y recomendaciones de demostración.
- Secciones para explorar contenidos, recetas y eventos.
- Pantallas de detalle para recetas, con ingredientes y preparación, y para eventos, con información de fecha, lugar y descripción.
- Acceso a contacto de ventas desde el contenido correspondiente.
- Navegación principal entre Inicio, Contenidos, Eventos, Recetas y Perfil, además de navegación de regreso en pantallas de detalle.
- Preferencia de accesibilidad para aumentar el tamaño del texto.
- Recursos gráficos y datos de ejemplo incluidos en el proyecto.

El contenido del catálogo, recetas y eventos es de demostración y está definido en la aplicación. No hay cuentas precargadas: para probar el acceso, primero se debe registrar una cuenta desde la app.

## Organización general del código

El código de la aplicación está en `app/src/main/java/com/example/ev2_master_martini/`:

- `ui/screens/`: pantallas de la aplicación.
- `ui/components/`: componentes reutilizables, como las barras de navegación y el reproductor.
- `ui/theme/`: colores, tipografía y tema de Compose.
- `navigation/`: rutas y configuración del flujo de navegación.
- `viewmodel/`: estado de pantalla, validaciones y acciones de usuario.
- `model/`: estados y estructuras de datos.
- `data/`: repositorio que guarda y recupera datos locales con DataStore.
- `util/`: funciones de validación y apoyo.

## Alcance del proyecto

Esta aplicación se realizó como un prototipo académico para demostrar los contenidos vistos en clases. Los datos de usuarios se guardan localmente en el dispositivo; las recetas, eventos y demás contenidos son datos de ejemplo. No existe conexión a un servidor ni administración remota de contenidos.
