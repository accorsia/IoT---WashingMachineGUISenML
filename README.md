# Smart Washing Machine IoT Simulator

## Overview

The Smart Washing Machine IoT Simulator is a Java-based project that demonstrates how to integrate sensors, actuators, and user interfaces in an IoT environment. The project simulates the operation of a smart washing machine using the CoAP protocol for lightweight communication. Sensor data (e.g., weight, temperature) and actuator commands (e.g., motor, door control) are exchanged using SenML (Sensor Markup Language) formatted messages.

## Features

- **IoT Communication:** Uses the CoAP protocol to interact with sensors and actuators.
- **Data Serialization:** Implements SenML serialization/deserialization to format sensor readings and actuator responses.
- **Smart Washing Programs:** Supports multiple washing programs (e.g., Cotton, Wool, Synthetics, Delicate, Rinse) with dynamic configuration based on sensor data.
- **Graphical User Interface:** Built with JavaFX, offering a user-friendly interface to interact with the system.
- **Modular Design:** Structured into modules with a clear separation between client, server, GUI, and business logic.

## Project Structure

- **Module Definition:** Utilizes `module-info.java` to define module dependencies including JavaFX, Californium, Gson, and others.
- **Resources:** Classes like `WeightSensorResource` and `IotResponse` manage sensor data and response formatting.
- **GUI:** The JavaFX-based GUI (e.g., `HelloApplication`, `HelloController`) allows users to create servers, select servers, and control the washing machine.
- **Clients & Observers:** Implements CoAP clients (`CoapPostClient`, `CoapGetClient`, `CoapHistoryClient`) and observation capabilities (`CoapObserveClient`).
- **Business Logic:** The `MotorActuator` class encapsulates washing machine operations including starting, stopping, and configuring washing programs.

## Installation

1. **Prerequisites:**
   - Java 11 or higher
   - JavaFX SDK
   - Dependencies: Californium Core, ControlsFX, Gson, SLF4J, etc.
2. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/your-repo.git
   ```
3. **Build the Project**: Use your preferred build tool (e.g., Maven, Gradle) or your IDE to compile the project.
4. **Run the Application**: Execute the main class (`HelloApplication`) to launch the GUI and start interacting with the smart washing machine simulator.

## Usage

- **Creating and Selecting Servers:**  
  Use the GUI to create multiple CoAP servers and select the one you want to interact with.
- **Controlling the Washing Machine:**  
  Utilize the provided interface buttons to start the motor, apply different washing programs, and observe sensor data in real time.
- **Observing Data:**  
  Start sensor observation to monitor changes (e.g., motor status, door status) using the CoAP Observe functionality.

## Contributing

Contributions are welcome! Feel free to open issues or submit pull requests for bug fixes and improvements.
