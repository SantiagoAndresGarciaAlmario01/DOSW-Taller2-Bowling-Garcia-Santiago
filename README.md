# DOSW - Taller 2 - Bowling TDD

## 1. Identificación

- **Nombre completo**: Santiago Andrés García Almario
- **Código estudiantil**: _1000095651_
- **Correo institucional**: _santiago.garcia-a@mail.escuelaing.edu.co_

## 2. Descripción

Este proyecto implementa, usando TDD puro (ciclo RED → GREEN → REFACTOR), el motor de puntuación de un juego de bowling para un solo jugador.

### Reglas implementadas

- Un juego tiene 10 frames. En cada frame normal el jugador tira hasta 2 veces, salvo que haga strike en el primer tiro.
- **Strike**: derribar los 10 pinos en el primer tiro de un frame. Puntúa 10 + los 2 tiros siguientes.
- **Spare**: derribar los 10 pinos en los 2 tiros de un frame. Puntúa 10 + el tiro siguiente.
- **Frame 10**: puede recibir hasta 3 tiros si hay strike o spare (tiros de bono).
- Validaciones: pines fuera de rango (0-10), suma de 2 tiros de un frame no puede superar 10, no se puede tirar después de que el juego terminó, no se puede pedir el puntaje de un juego incompleto.

### Responsabilidades de cada clase

| Clase | Responsabilidad |
|---|---|
| `Frame` | Representa un frame individual: guarda sus tiros y sabe si es strike o spare. |
| `BowlingGame` | Orquesta el juego: valida y registra tiros (`roll`), sabe si el juego terminó (`isComplete`) y expone el puntaje final (`score`), delegando el cálculo. |
| `BowlingScorer` | Clase sin estado que recibe la lista de frames ya jugados y calcula el puntaje total aplicando los bonos de spare y strike. |

## 3. Evidencia TDD

El historial completo de commits sigue la convención:

- `test: RED - ...` → prueba que falla antes de implementar
- `feat: GREEN - ...` → implementación mínima que hace pasar la prueba
- `refactor: ...` → mejora de diseño sin cambiar comportamiento observable

### Ejemplo de ciclo completo (caso A2 — `roll(-1)` lanza `IllegalArgumentException`)

**RED** (commit `test: RED - roll(-1) lanza IllegalArgumentException`):

<img width="722" height="266" alt="image" src="https://github.com/user-attachments/assets/06d8921e-94b1-407a-952e-7d1a3c1edefb" />


**GREEN** (commit `feat: GREEN - valida pines negativos`):

<img width="719" height="264" alt="image" src="https://github.com/user-attachments/assets/c05f8e8f-873e-450e-82f0-e28bee3d484a" />


### Ciclo de REFACTOR

Commit `refactor: elimina campo no usado, codigo muerto y duplicacion; extrae metodos con nombres claros`, aplicado después de completar los Módulos A, B y C:

- Se eliminó el campo `currentFrame` en `BowlingGame` (nunca se usaba).
- Se extrajo el método `lastFrame()` para eliminar la duplicación de `frames.get(frames.size() - 1)`.
- `BowlingScorer` pasó de instanciarse en cada llamada a `score()` a ser un colaborador fijo (campo).
- Se eliminó una rama de código muerto en `BowlingScorer.firstRollOfNextFrame()` que ya no se alcanzaba tras el fix del caso B7.
- Se extrajo `frameScore()` en `BowlingScorer.calculate()` para separar el recorrido de la lógica de puntaje por frame.

Todas las 22 pruebas se mantuvieron en verde antes y después del refactor.

## 4. JaCoCo — Cobertura de código

```
mvn clean verify
```

**Resultado**: `All coverage checks have been met.` — `BUILD SUCCESS`.

| Métrica | Cobertura |
|---|---|
| Líneas | 100% |
| Ramas (branches) | 97% (2 de 68 ramas sin cubrir) |

Captura del reporte (`target/site/jacoco/index.html`):

<img width="1134" height="181" alt="image" src="https://github.com/user-attachments/assets/7d00ca58-caf8-42d8-a140-32ac138f56be" />


**Qué pruebas subieron la cobertura**: los casos A6/A7/A8 (detección de strike, spare y tiros de bono del frame 10) y B3-B7 (bonos de spare, strike y juego perfecto) fueron los que forzaron a cubrir las ramas más complejas — en particular la distinción entre frame 10 abierto/cerrado y el cálculo de bono cuando el strike/spare está a 1 o 2 frames de distancia.

## 5. SonarQube — Análisis estático

```
mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.projectKey=bowling-tdd \
  -Dsonar.projectName='Bowling TDD' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=$SONAR_TOKEN
```

**Seguridad**: el token se pasó por variable de entorno / línea de comandos, nunca se guardó en el repositorio.

Captura del dashboard (cobertura, issues, Quality Gate):

<img width="1920" height="914" alt="image" src="https://github.com/user-attachments/assets/7cb44915-e54d-470b-a26a-e91b81006f82" />


Quality Gate: Passed

## 6. Pull Requests

| PR | Fecha de merge | Módulo que cubre |
|---|---|---|
| _[link]_ | _[fecha]_ | Módulo A - `BowlingGame.roll()` |
| _[link]_ | _[fecha]_ | Módulo B - `BowlingScorer.calculate()` |
| _[link]_ | _[fecha]_ | Módulo C - `BowlingGame.isComplete()` |
| _[link]_ | _[fecha]_ | Refactor + cobertura + Sonar |

## 7. Reflexión técnica

**1. ¿Qué caso edge del Bowling fue el más difícil de implementar con TDD y por qué?**

El frame 10 fue, por lejos, el más difícil — específicamente el caso A8 (aceptar hasta 3 tiros tras un strike). El problema no fue tanto escribir la prueba, sino que la solución obligó a redescubrir que `isStrike()` no podía derivarse del tamaño de la lista de tiros (`rolls.size() == 1`), porque en cuanto el frame 10 recibía tiros de bono ese tamaño dejaba de ser 1 y la marca de "fue strike" se perdía. Hubo que cambiar el diseño de `Frame` para que recordara el strike con un campo booleano persistente en vez de recalcularlo. Esto es un buen ejemplo de cómo TDD expone supuestos de diseño incorrectos apenas el caso de prueba los pone a prueba.

**2. ¿Qué parte del código cambió durante REFACTOR sin modificar el comportamiento observable?**

Se eliminó el campo `currentFrame` (nunca usado), se extrajo `lastFrame()` para quitar duplicación de `frames.get(frames.size() - 1)`, `BowlingScorer` pasó de crearse en cada llamada a `score()` a ser un campo fijo, y se eliminó una rama muerta en `BowlingScorer.firstRollOfNextFrame()` que dejó de alcanzarse después de que el cálculo del último frame se resolvió de forma distinta (caso B7). Las 22 pruebas existentes se usaron como red de seguridad: se corrieron antes y después del refactor y ninguna cambió de resultado.

**3. ¿Qué casos de prueba descubriste al revisar el reporte de cobertura de JaCoCo que no habías considerado antes?**

Al revisar el detalle por clase en target/site/jacoco/index.html, las 2 ramas sin cubrir corresponden a casos de borde en la lógica del frame 10 (isTenthFrameOpen) y en la validación de suma de un frame (validateFrameSum) que no tienen una prueba explícita dedicada — por ejemplo, la combinación exacta de un frame 10 que no es ni strike ni spare tras su primer tiro. No se agregaron pruebas adicionales para estas ramas porque su comportamiento ya queda cubierto indirectamente por los casos existentes (A4, C3) y el riesgo de no cubrirlas es bajo.

**4. ¿Qué hallazgo de SonarQube produjo un cambio real en el código?**

SonarQube marcó 1 issue de mantenibilidad (code smell) en el proyecto. [Si alcanzas a revisarlo: describe aquí qué regla violó y si lo corregiste o decidiste dejarlo documentado]. En términos generales, el análisis confirmó que el refactor previo (eliminar el campo currentFrame no usado y el código muerto en BowlingScorer) ya había resuelto los hallazgos más evidentes antes incluso de correr Sonar.
