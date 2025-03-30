# SISTEMA DE CÁLCULO DE DOSIS DE MEDICAMENTOS

## Descripción
Este proyecto es una solución digital diseñada para calcular la dosis exacta de medicamentos en función del peso corporal del paciente. La aplicación busca mejorar la precisión en la administración de medicamentos y reducir el riesgo de efectos adversos por dosis incorrectas.

## Autor
- **Nombre:** Sara Hevia López  
- **Curso:** 2 DAM  

## Características principales
- Cálculo preciso de dosis basado en el peso del usuario.
- Interfaz intuitiva y fácil de usar.
- Persistencia de datos para consultas futuras.
- Chat en tiempo real con profesionales de la salud.
- Seguridad en la gestión de datos personales.

## Tecnologías utilizadas
- **Backend:** Java con Spring Boot.
- **Frontend:** React con JavaScript.
- **Base de datos:** SQL.
- **Despliegue:** Docker.
- **Scripts y automatización:** Bash Scripting.

## Funcionamiento
1. El usuario introduce:
   - Nombre del medicamento.
   - Dosis recomendada en el prospecto.
   - Peso estándar para el que está calculada.
   - Su peso real.
2. La aplicación calcula la dosis personalizada usando las fórmulas:
   ```
   Dosis estimada por kg = Dosis recomendada / Peso estándar
   Dosis ajustada = Dosis estimada por kg * Peso real del usuario
   ```
3. Se muestran las dosis mínima, máxima y diaria recomendadas.
4. Validaciones:
   - No se calculan dosis para menores de 15 ni mayores de 75 años.
   - Se advierte si la dosis ajustada es significativamente diferente a la estándar.
5. Se permite la consulta a un profesional mediante un chat en tiempo real.

## Instalación y uso
### Requisitos previos
- Java 23+
- Docker
- PostgreSQL 

### Pasos de instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/usuario/proyecto-dosis.git
   cd proyecto-dosis
   ```
2. Configurar la base de datos en `application.properties`.
3. Ejecutar el backend:
   ```
   mvn spring-boot:run
   ```

4. Acceder a la aplicación en `http://localhost:8080`

## Contribución
Las contribuciones son bienvenidas. Para colaborar:
1. Realiza un fork del repositorio.
2. Crea una rama (`feature-nueva-funcionalidad`).
3. Realiza cambios y haz commit.
4. Envía un pull request.

## Licencia
Este proyecto está bajo la licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Contacto
Para más información o soporte, contactar a **Sara Hevia López**.



