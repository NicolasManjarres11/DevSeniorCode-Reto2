# Sistema de Gestión de Emergencias Urbanas 🚑🚒🚓

Este proyecto es una aplicación de consola desarrollada en Java para la gestión de emergencias urbanas. El sistema permite registrar emergencias, asignar y gestionar recursos (como bomberos 🚒, ambulancias 🚑 y policía 🚓), atender emergencias y transferir recursos entre servicios. Además, incorpora varios patrones de diseño (Singleton, Factory, Observer, Strategy) y permite la priorización de emergencias según criterios como gravedad o proximidad.

## Características 👩‍⚕️👮👨‍🚒

- **Registro de Emergencias:**  🚨

  Permite crear emergencias de diferentes tipos (Accidente, Robo, Incendio) con información como ubicación, gravedad y tiempo estimado de atención.

- **Gestión de Recursos:**  👩‍🏫

  El sistema gestiona distintos recursos de emergencia (bomberos, ambulancias y policía), mostrando su estado (personal y combustible disponibles).  
  Además, permite transferir recursos (personal o combustible) entre servicios.

- **Atención de Emergencias:**  🕐

  Asigna automáticamente recursos disponibles para atender emergencias y simula el proceso de atención mostrando el progreso en porcentaje.

- **Prioridad y Orden de Atención:**  ⚕️

  Implementa estrategias de prioridad mediante el patrón Strategy para atender emergencias basándose en la gravedad o la proximidad a la base de operaciones.  
  Se puede elegir atender emergencias por orden de llegada o por prioridad.

- **Estadísticas y Registro:**  👀

  Se guardan estadísticas del día (número de emergencias atendidas, tiempo promedio de atención, emergencias no atendidas) y se actualiza un registro en archivo.

- **Patrones de Diseño Implementados:**  🤖

  - **Singleton:** Para gestionar una única instancia del sistema de emergencias.  
  - **Factory:** Para la creación de emergencias y gestión de recursos.  
  - **Observer:** Para notificar a observadores cuando se registra una nueva emergencia.  
  - **Strategy:** Para definir cómo se priorizan las emergencias (por gravedad o proximidad).

## Requisitos

- Java JDK 11 o superior ☕
- [Opcional] IDE de tu preferencia (IntelliJ IDEA, Eclipse, VSCode, etc.) 👨‍💻

## Cómo Ejecutar la Aplicación

1. **Clonar el repositorio:**

   ```bash
   git clone https://github.com/NicolasManjarres11/DevSeniorCode-Reto2.git
   cd DevSeniorCode-Reto2

2. **Ejecutar el programa:**

   ```bash
   cd DevSeniorCode-Reto2
   cd src
   cd views
   Desde el IDE de tu preferencia, debemos ejecutar el archivo Main.java


## ¿Qué verás? ✍️

La aplicación mostrará un menú interactivo en la consola donde podrás:

- Registrar una nueva emergencia.
- Mostrar el estado de los recursos y transferir recursos entre servicios.
- Atender emergencias, eligiendo atender por prioridad o por orden de llegada.
- Ver estadísticas del día.
- Finalización de la jornada.

## Uso del Sistema  🕹️
Al iniciar la aplicación, se presentará un menú con las siguientes opciones:  

### 1. Registrar emergencia  🔔
Permite ingresar datos como:  
- **Tipo de emergencia** (Accidente, Robo, Incendio).  
- **Ubicación** (Zona-Norte, Zona-Sur, Zona-Centro, Zona-Oriente, Zona-Occidente).  
- **Gravedad** (Baja, Media, Alta).  
- **Tiempo estimado de atención** (en minutos).  

### 2. Mostrar recursos  📝
- Muestra el estado actual de los recursos (personal y combustible).  
- Ofrece la opción de **transferir recursos** entre servicios.  

### 3. Atender emergencia  🏹
- Permite seleccionar una emergencia pendiente.  
- Asigna automáticamente los recursos necesarios para su atención.  
- Se puede elegir atender emergencias **por prioridad** (calculada con estrategias) o **por orden de llegada**.  

### 4. Ver estadísticas del día  📊
Muestra datos clave como:  
- **Número de emergencias atendidas.**  
- **Tiempo promedio de atención.**  
- **Emergencias no atendidas.**  

### 5. Finalizar jornada  ✅
- Guarda la información del día.  
- Finaliza el ciclo del sistema.  

## Estructura del proyecto 🏗️

```bash
├── src
│   ├── controller
│   │   ├── Database.java
│   │   └── EmergencySystem.java
│   ├── model
│   │   ├── Emergency.java
│   │   ├── Accident.java
│   │   ├── Fire.java
│   │   ├── Robbery.java
│   │   └── emergencies.txt
│   ├── model/factory
│   │   └── FactoryEmergency.java
│   ├── model/interfaces
│   │   └── IEmergencyService.java
│   ├── model/services
│   │   ├── Ambulance.java
│   │   ├── Firefighters.java
│   │   └── Police.java
|   |   └── EmergencyService.java
│   ├── model/observer
│   │   ├── ObserverEmergency.java
│   │   └── SubjectEmergency.java
│   ├── model/strategy
│   │   ├── StrategyPriority.java
│   │   ├── StrategyGravityPriority.java
│   │   └── StrategyProximityPriority.java
│   ├── utils
│   │   ├── Gravity.java
│   │   └── TypeEmergency.java
│   └── views
│       └── Main.java
└── README.md
```

### ✨ ¡Esperamos que esta aplicacion sea de tu agrado! 🚒🚑👮‍♂️ 