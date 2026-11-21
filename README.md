# Master Martini

Aplicación móvil de demostración desarrollada para el ramo **Desarrollo de Aplicaciones Móviles (DSY1105)**. Presenta contenido y recursos para personas relacionadas con la pastelería y la gastronomía: registro local, catálogo de contenidos, recetas, eventos y una sección que consume una **API externa** de recetas.

## Integrantes

**Equipo 12**

- **EDIXON ELIASAF ELIAS VILLAFANE**
- **DANILO ALEJANDRO GONZALEZ ARCE**

## Tecnologías utilizadas

- Kotlin
- Jetpack Compose y Material 3
- Arquitectura MVVM (UI · ViewModel · Repository · dominio)
- Navigation Compose
- StateFlow para observar y actualizar el estado de las pantallas
- DataStore Preferences para la persistencia local
- Media3 para el contenido audiovisual
- **Retrofit + OkHttp + kotlinx.serialization** para el consumo de la API externa
- **Coil** para la carga de imágenes remotas

## Funcionalidades implementadas

- Pantalla de bienvenida con transición al flujo de acceso.
- Registro local de una cuenta y pantalla de inicio de sesión.
- Validaciones de campos (correo, contraseña, teléfono chileno con prefijo y RUT), centralizadas en `domain/UserValidator`.
- Persistencia local de la cuenta, los intereses y el estado de sesión con DataStore.
- Mantención de la sesión y cierre de sesión desde el perfil.
- Cuestionario de tipo/tamaño de negocio e intereses de contenido.
- Pantalla de inicio con recomendaciones y eventos de demostración.
- Secciones para explorar contenidos, recetas y eventos, con sus pantallas de detalle.
- Contacto de ventas desde el contenido (llamada y correo mediante recursos nativos).
- Acceso a la cámara para la foto de perfil.
- **Sección "Inspírate con recetas del mundo"** que consume la API externa TheMealDB: buscador, categorías, receta aleatoria y detalle, con imágenes remotas.
- **Consola de red** y chip de estado que evidencian las llamadas a la API (método, endpoint, código y tiempo).
- Preferencia de accesibilidad "Modo texto grande" (contraste correcto en modo claro y oscuro).
- Recursos gráficos y datos de ejemplo incluidos en el proyecto.

El contenido del catálogo, recetas y eventos locales es de demostración. No hay cuentas precargadas: para probar el acceso, primero se debe registrar una cuenta desde la app.

## API externa utilizada

- **Nombre:** TheMealDB
- **Base URL:** `https://www.themealdb.com/`
- **Autenticación:** no requiere API key (versión pública v1, HTTPS).

### Endpoints utilizados

| Método | Endpoint | Uso en la app |
|--------|----------|---------------|
| GET | `api/json/v1/1/search.php?s={nombre}` | Buscador de la sección "Inspírate" |
| GET | `api/json/v1/1/categories.php` | Chips de categoría |
| GET | `api/json/v1/1/filter.php?c={categoría}` | Filtrado por categoría |
| GET | `api/json/v1/1/random.php` | Botón "Sorpréndeme" |
| GET | `api/json/v1/1/lookup.php?i={id}` | Pantalla de detalle de la receta |

Las llamadas pueden verificarse en vivo en la **Consola de red** de la app (ícono/chip de la sección de Home).

> **Microservicios:** este entregable **no incluye microservicios con Spring Boot** (decisión de alcance del equipo). La persistencia de datos de usuario se resuelve localmente con DataStore.

## Pasos para ejecutar

1. Abrir el proyecto en **Android Studio** (versión Compatible con AGP 9.5 / Kotlin 2.2).
2. Sincronizar Gradle.
3. Ejecutar en un emulador o dispositivo con **Android 12 (API 31) o superior**:
   ```
   ./gradlew installDebug
   ```
   o usar el botón **Run** de Android Studio.
4. Registrar una cuenta en la app para acceder al flujo principal.
5. En **Inicio**, usar la sección "Inspírate con recetas del mundo" (requiere conexión a internet).

## Generar el APK firmado

1. Copiar `keystore.properties.example` a `keystore.properties` (raíz del proyecto).
2. Generar la llave `.jks` (raíz del proyecto):
   ```
   keytool -genkeypair -v -keystore mastermartini.jks -alias mastermartini \
     -keyalg RSA -keysize 2048 -validity 10000
   ```
3. Editar `keystore.properties` con la contraseña, el alias y la ruta de la llave.
4. Compilar el APK release firmado:
   ```
   ./gradlew :app:assembleRelease
   ```
   El artefacto queda en `app/build/outputs/apk/release/`.
5. Dejar en el repositorio: el APK firmado, la llave `mastermartini.jks` y la configuración (`keystore.properties`).

> Si `keystore.properties` no existe, el proyecto compila igual (release sin firmar): la firma se habilita automáticamente cuando la llave está presente.

### Captura del APK firmado y la llave

<!-- Reemplazar por la captura real antes de la entrega -->
`(Insertar aquí la captura de: APK firmado en app/build/outputs/apk/release/ + archivo mastermartini.jks + keystore.properties)`

## Organización general del código

Código en `app/src/main/java/com/example/ev2_master_martini/`:

- `ui/screens/`: pantallas (incluye `home/InspirationSection.kt` y `InspirationDetailScreen.kt`).
- `ui/components/`: componentes reutilizables (barras, reproductor y `NetworkConsoleSheet`).
- `ui/theme/`: colores, tipografía y tema de Compose.
- `navigation/`: rutas y flujo de navegación.
- `viewmodel/`: estado de pantalla y acciones (incluye `InspirationViewModel` y `InspirationDetailViewModel`).
- `model/`: estados y estructuras de datos.
- `domain/`: validaciones de dominio (`UserValidator`).
- `data/`: repositorios locales (DataStore, catálogo/recetas/eventos) y `data/remote/` (Retrofit, DTOs y cliente HTTP).
- `util/`: utilidades (validación, contraseña y acciones nativas).

## Alcance del proyecto

Prototipo académico para demostrar los contenidos vistos en clases. Los datos de usuario se guardan localmente; el catálogo, las recetas y los eventos locales son datos de ejemplo. Además, se consume la API pública TheMealDB como contenido externo, sin administración remota propia.
