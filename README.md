# Big Data Analysis

This project is a Spark application for analyzing big data using Apache Spark. It performs language analysis and complexity metrics on web pages in the WARC format.

## Academic Context

Originating as a school project, this application was designed, developed, and tested on the university's Hadoop-powered servers. This setup ensured robust and efficient analysis of expansive WARC datasets, tapping into the power of distributed data processing.

## Prerequisites

- Apache Spark (version X.X.X or higher)
- Java Development Kit (JDK) 8 or higher
- Scala (version 2.12.X)
- sbt (Scala Build Tool)
- Typesafe Config library (version 1.4.1 or higher)

## Instructions

1. Make the `run.sh` script executable
```
chmod +x run.sh
```

2. Run the application:
```
./run.sh
```
This script assembles the project and submits the Spark application for execution.

3. View the results:

The application will output the top language counts and complexity metrics based on the configured limit.

## Project Structure

The project has the following structure:

- `build.sbt`: The build configuration file for sbt.
- `run.sh`: Shell script to assemble and submit the Spark application.
- `src/main/scala/`: Contains the Scala source code.
- `src/main/resources/`: Contains the application configuration file.
- `src/main/scala/org/bigdataanalysis/Main.scala`: Entry point of the application.
- `src/main/scala/org/bigdataanalysis/config/`: Contains the application configuration classes.
- `src/main/scala/org/bigdataanalysis/utils/`: Contains utility classes for WARC reading and text analysis.

