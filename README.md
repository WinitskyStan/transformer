
# CSV Transformer

A Java library for transforming CSV files according to configurable json file.

## Features

- Includes a CSVProcessor to read and parse CSV file as a list of Maps. Each map represents a row of the CSV file, mapping the header to the String value, similar to a json object.
- Includes a CSVTransformService to transform a row from CSVProcessor into an object of type Map<String, Object> according to a configuration file. Similarly, this maps a header to an Object of type String, Integer, BigDecimal, LocalDate, etc.
- Includes a CSVTransformConfig to configure the process according to a json configuration file.
- Includes a Main class to run the application, outputs the result of the transformation to the console and keeps track of errors.
- Includes test cases for all classes and errors.

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
