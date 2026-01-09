# PacmanProject

Juego básico de Pac-Man desarrollado en Java utilizando Swing para la interfaz gráfica.

## Badges

[![CI/CT](https://github.com/alexgc04/pacmanGPS/actions/workflows/ci-ct.yml/badge.svg)](https://github.com/alexgc04/pacmanGPS/actions/workflows/ci-ct.yml)
[![CD - Deploy to GitHub Pages](https://github.com/alexgc04/pacmanGPS/actions/workflows/cd.yml/badge.svg)](https://github.com/alexgc04/pacmanGPS/actions/workflows/cd.yml)
![Coverage](https://img.shields.io/badge/coverage-99%25-brightgreen)

## Características del Juego

- 🎮 **Laberinto clásico**: Un tablero donde Pac-Man y los fantasmas se mueven libremente.
- 🕹️ **Control del jugador**: Movimiento de Pac-Man controlado por el usuario mediante las teclas de flecha.
- 👻 **Fantasmas inteligentes**: Cuatro tipos de fantasmas (Blinky, Pinky, Inky, Clyde) con movimiento automático.
- 🔵 **Puntos coleccionables**: Dots que aumentan el puntaje al ser comidos.
- 🏆 **Condiciones de juego**: Victoria al comer todos los puntos, derrota al ser atrapado por un fantasma.
- 🖥️ **Interfaz gráfica**: Desarrollada con Java Swing, sencilla y modular.
- ⬆️⬇️⬅️➡️ **Direcciones de movimiento**: Sistema de direcciones (UP, DOWN, LEFT, RIGHT) para el control preciso.

## Estructura de Archivos

```
pacmanGPS/
├── .github/
│   └── workflows/
│       ├── ci-ct.yml     # Workflow de Integración y Prueba Continua
│       └── cd.yml        # Workflow de Despliegue Continuo a GitHub Pages
├── src/
│   ├── main/java/pacman/
│   │   ├── Game.java         # Clase principal: inicializa la ventana y el juego
│   │   ├── Board.java        # Lógica y renderizado del tablero
│   │   ├── Pacman.java       # Lógica y renderizado de Pac-Man
│   │   ├── Ghost.java        # Lógica y renderizado de los fantasmas
│   │   ├── GhostType.java    # Enum para los tipos de fantasmas (BLINKY, PINKY, INKY, CLYDE)
│   │   └── Direction.java    # Enum para las direcciones de movimiento
│   └── test/java/pacman/
│       ├── GameTest.java     # Pruebas unitarias de la clase Game
│       ├── BoardTest.java    # Pruebas unitarias de la clase Board
│       ├── PacmanTest.java   # Pruebas unitarias de la clase Pacman
│       ├── GhostTest.java    # Pruebas unitarias de la clase Ghost
│       ├── GhostTypeTest.java # Pruebas unitarias del enum GhostType
│       └── DirectionTest.java # Pruebas unitarias del enum Direction
├── pom.xml               # Configuración de Maven (dependencias, plugins, etc.)
├── screenshot.png        # Captura de pantalla del juego
└── README.md             # Este archivo
```

## Requisitos

| Requisito | Versión Mínima | Descripción |
|-----------|----------------|-------------|
| Java | 17 o superior | JDK necesario para compilar y ejecutar |
| Maven | 3.8+ | Gestor de dependencias y construcción |
| IDE (Opcional) | - | IntelliJ IDEA, Eclipse, VSCode, etc. |

### Dependencias del Proyecto

- **JUnit 5** (5.10.0): Framework de pruebas unitarias
- **JaCoCo** (0.8.12): Herramienta de análisis de cobertura de código

## Compilación y Ejecución

### Con Maven (recomendado)

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar pruebas
mvn test

# Ejecutar pruebas con cobertura
mvn test jacoco:report

# Construir el proyecto completo
mvn clean install

# Generar el JAR ejecutable
mvn clean package
```

### Desde la terminal (legacy)

```bash
javac -d bin src/main/java/pacman/*.java
java -cp bin pacman.Game
```

## Controles

- **Flechas del teclado** para mover a Pac-Man: izquierda, derecha, arriba, abajo.

## GitHub Actions

Este proyecto utiliza GitHub Actions para automatizar la integración, prueba y despliegue continuo.

### 📦 CI/CT - Integración y Prueba Continua (`ci-ct.yml`)

| Característica | Descripción |
|----------------|-------------|
| **Activación** | Push y Pull Request a ramas `main` y `develop` |
| **Entorno** | Ubuntu latest con JDK 17 (Temurin) |
| **Concurrencia** | Evita ejecuciones duplicadas en PRs |

**Pasos del workflow:**
1. ✅ Checkout del repositorio
2. ☕ Configuración de JDK 17 con cache de Maven
3. 🔨 Build con Maven (`mvn clean install -DskipTests`)
4. 🧪 Ejecución de pruebas (`xvfb-run mvn test`)
5. 📊 Generación de informe de cobertura JaCoCo
6. 📤 Subida del reporte de cobertura como artefacto
7. 🧹 Limpieza de archivos temporales
8. 📢 Notificación del estado (éxito/fallo)

### 🚀 CD - Despliegue Continuo a GitHub Pages (`cd.yml`)

| Característica | Descripción |
|----------------|-------------|
| **Activación** | Después de que CI/CT finalice con éxito en `main` |
| **Entorno** | Ubuntu latest con JDK 17 (Temurin) |
| **Concurrencia** | Evita despliegues duplicados |

**Pasos del workflow:**
1. ✅ Checkout del repositorio
2. ☕ Configuración de JDK 17 con cache de Maven
3. 📦 Construcción del JAR (`mvn clean package -DskipTests`)
4. 🌐 Creación de landing page HTML
5. 📤 Subida del artefacto para GitHub Pages
6. 🚀 Despliegue a GitHub Pages
7. 🔗 Notificación de la URL de despliegue

## Cobertura de Tests

![Coverage Badge](https://img.shields.io/badge/coverage-99%25-brightgreen)

| Métrica | Cobertura |
|---------|-----------|
| Instrucciones | 99% |
| Ramas | 96% |
| Líneas | 100% |
| Métodos | 100% |
| Clases | 100% |

## Extensiones Futuras

- Mejorar la IA de los fantasmas.
- Añadir niveles y nuevos mapas.
- Implementar efectos de sonido.
- Añadir "power-ups" y más funcionalidades clásicas del juego.

## Autor

Proyecto desarrollado por alexgc04.

---

¡Disfruta programando y jugando Pac-Man!
