# Distributed System for Estimating π using the Monte Carlo Method

This project, developed by **David Artunduaga Penagos**, **Rony Farid Ordoñez García**, **Gabriel Ernesto Escobar**, and **Vanessa Sánchez Morales**, implements a distributed system to estimate the value of π using the Monte Carlo method. It uses a client-master-worker model with asynchronous communication via ICE (Internet Communications Engine).

## Description of the Monte Carlo Method for π
The Monte Carlo method involves throwing random points within a square centered at the origin and calculating how many of these points fall inside the inscribed circle. The ratio of points within the circle compared to the total allows us to estimate π, as π ≈ 4 × (number of points in the circle / total points thrown).

## Design of the Client-Master-Worker Model
- **Client:** Sends a request to the master, specifying a number `N` of random points for the estimation of π.
- **Master:** Coordinates the task by distributing the total `N` points into subtasks for `n` workers, assigning each worker `N/n` points.
- **Workers:** Receive their assigned points, generate these random points, calculate how many fall inside the circle, and send the result back to the master.

## Project Structure
The project is organized as follows:
- `client/`: Contains the client’s code and configuration.
- `master/`: Contains the master’s code and configuration.
- `worker/`: Contains the workers' code and configuration.
- `Demo/`: Contains interfaces generated from the `.ice` files for communication between components.

## Requirements
- **Java 17**
- **Gradle 8**
- **ICE 3.7**

## Instructions to Run the Project

1. **Build the Project**
   Navigate to the project’s root folder and execute:
   ```bash
   gradle build
   ```
   If you encounter errors, run:
   ```bash
   gradle clean build
   ```

2. **Run the Master**
   Navigate to the master folder and execute the generated JAR:
   ```bash
   cd master/build/libs/
   java -jar master.jar
   ```

3. **Run the Workers**
   Each worker should be run in a separate terminal to distribute the load. Navigate to the worker folder and execute the JAR:
   ```bash
   cd worker/build/libs/
   java -jar worker.jar
   ```

4. **Run the Client**
   Open a terminal, navigate to the client folder, and execute the JAR file:
   ```bash
   cd client/build/libs/
   java -jar client.jar
   ```

5. **Request an Estimation of π**
   Once the client is running, submit a request in the format:
   ```
   ClientName::N
   ```
   where `N` is the number of random points for the estimation. The master will distribute the task and coordinate responses from the workers.
