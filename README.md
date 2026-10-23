# Sistema de Inventario – JUnit 5, Mockito y JaCoCo

## Descripción del proyecto

Este proyecto consiste en el desarrollo de un sistema básico de inventario utilizando Java 21 y Maven. Su objetivo es aplicar pruebas unitarias para verificar el funcionamiento de las operaciones del sistema y evaluar la cobertura del código.

Inicialmente trabajé con JUnit 5 para comprobar las funcionalidades principales. Posteriormente incorporé Mockito para realizar pruebas con objetos simulados y JaCoCo para analizar qué partes del código estaban siendo ejecutadas durante las pruebas.

## Tecnologías utilizadas

- Java 21
- Maven
- JUnit 5
- Mockito
- JaCoCo
- Visual Studio Code
- Git y GitHub

## Funcionalidades del sistema

El sistema permite registrar productos, buscar productos por código, consultar el inventario, eliminar productos, calcular el valor total de las existencias y realizar ventas actualizando las cantidades disponibles.

También incluye validaciones para evitar registros duplicados, productos nulos, ventas con cantidades inválidas y operaciones con existencias insuficientes.

## Pruebas unitarias con JUnit 5

Implementé pruebas para verificar el comportamiento de los productos, el repositorio en memoria y el servicio de inventario.

Estas pruebas permiten comprobar tanto operaciones correctas como situaciones en las que el sistema debe rechazar una acción.

## Pruebas con Mockito

Utilicé Mockito para simular el comportamiento del repositorio y comprobar las interacciones realizadas por el servicio de inventario.

Esto permitió evaluar las operaciones sin depender directamente de una implementación real del repositorio.

También realicé pruebas adicionales para comprobar una venta de una unidad, la consulta de un inventario vacío y el cálculo del valor total cuando no existen productos.

## Análisis de cobertura con JaCoCo

Integré JaCoCo en Maven para generar reportes de cobertura del código.

Durante el desarrollo comparé los resultados iniciales y finales:

| Métrica | Inicial | Final |
|---|---|---|
| Cobertura de instrucciones | 94% | 100% |
| Cobertura de ramas | 88% | 100% |
| Instrucciones sin cubrir | 12 | 0 |
| Ramas sin cubrir | 3 | 0 |

Para mejorar la cobertura, agregué pruebas que verifican la venta con existencias insuficientes y el intento de vender un producto inexistente.

Con estas pruebas logré ejecutar las instrucciones y ramas que anteriormente no estaban cubiertas.

## Resultados obtenidos

En la ejecución final de Maven obtuve los siguientes resultados:

- 59 pruebas ejecutadas.
- 0 fallos.
- 0 errores.
- 0 pruebas omitidas.
- Compilación exitosa (BUILD SUCCESS).
- 100% de cobertura de instrucciones.
- 100% de cobertura de ramas.

## Ejecución del proyecto

Desde la carpeta principal del proyecto, donde se encuentra el archivo `pom.xml`, se pueden ejecutar las pruebas mediante Maven:

```bash
mvn clean verify
```

El reporte HTML de JaCoCo se genera en:

```text
target/site/jacoco/index.html
```

Si Maven no está configurado en la variable PATH, debe utilizarse la ruta completa del ejecutable `mvn.cmd`.

## Estructura del proyecto

- `src/main/java/com/inventario/`: código principal del sistema.
- `src/test/java/com/inventario/`: pruebas unitarias con JUnit 5 y Mockito.
- `pom.xml`: configuración de Maven y dependencias.
- `README.md`: documentación del proyecto.
- `.gitignore`: exclusión de archivos generados automáticamente.

## Conclusión

Este proyecto me permitió comprender mejor la importancia de realizar pruebas unitarias durante el desarrollo de software. Aprendí a utilizar JUnit 5 para verificar resultados, Mockito para simular dependencias y JaCoCo para identificar partes del código que necesitaban más pruebas.

También comprendí que alcanzar el 100% de cobertura no significa que un programa esté completamente libre de errores, pero sí ayuda a comprobar que las instrucciones y decisiones del código fueron ejecutadas durante las pruebas.