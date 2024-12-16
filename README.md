
# CSV Transformer

A Java library for transforming CSV files according to configurable json file.

## Features

- Includes a CSVProcessor to read and parse CSV file as a list of Maps. Each map represents a row of the CSV file, mapping the header to the String value, similar to a json object.
- Includes a CSVTransformService to transform a row from CSVProcessor into an object of type Map<String, Object> according to a configuration file. Similarly, this maps a header to an Object of type String, Integer, BigDecimal, LocalDate, etc.
- Includes a CSVTransformConfig to configure the process according to a json configuration file.
- Includes a Main class to run the application, outputs the result of the transformation to the console and keeps track of errors.
- Includes test cases for all classes and errors.
- Includes javadoc comments for classes when applicable.

## Assumptions

- The CSV file is a comma-separated file, with the first row containing the headers of the CSV file.
- The CSV file is UTF-8 encoded.
- Column names in the CSV file are exact matches to the configuration file.
- The CSV file is not empty.
- All required fields in the configuration file are present.
- Memory usages scales with single row size, not the entire file size.
- Minimal dependencies, i.e. OpenCSV, Commons Lang, Jackson.

## Architecture Overview

1. Core Components
    - Configuration (org.s2n.csv.config) - Handles json based configuration for transformation.
    - Service Layer (org.s2n.csv.service) - Handles CSV processing and transformation line by line. Loads each line as a map of header name to String value. Transforms into a map of header name to Object (String, Integer, BigDecimal, LocalDate, etc).
    - Main (org.s2n.csv.driver) - Command line interface for running the application. Handles command line arguments and outputs transformation results to the console. Keeps track of errors and total lines processed. 

2.Data Flow
    - Configuration loaded from json file.
    - CSVProcessor reads a CSV file and returns a Map of header names to String values line by line.
    - CSVTransformService transforms a Map of header names to String values into a Map of header names to Objects.
    - Main handles command line arguments and outputs transformation results to the console.

3. Error Handling
    - Exceptions are handled by the Main class and printed to the console.
    - The Main class keeps track of total lines processed and errors encountered.
    - Custom exception for each error type.

## Prerequisites

- Java 21 or higher
- Maven 3.6.3 or higher

## Building the Project

To build the project, run:

```bash
git clone https://github.com/WinitskyStan/transformer.git
cd transformer
mvn clean install
```

## Running Test

To run the tests, run:

```bash
mvn test
```

## Running the Application

to run the application, run:

```bash
mvn exec:java -Dconf=config.json -Dcsv=sample.csv
```

or to use a default configuration and csv file:

```bash
mvn exec:java
```
