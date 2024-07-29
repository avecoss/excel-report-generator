# Excel Report Generator
The Report Generator application fetches 1,000,000 records from a PostgreSQL database and writes them into an Excel file without running into OutOfMemoryError. It leverages batching and multi-threading to efficiently process and write large amounts of data.

## Configuration

Make sure to configure your database connection and other settings in the `application.yml` file:

```yml
spring:
    application:
        name: report-generator

    datasource:
        url: jdbc:postgresql://localhost:5433/your_db_name
        username: your_username
        password: your_password
        driver-class-name: org.postgresql.Driver

filePath:
    report: "report/data.xlsx"

batch:
    size: 1000

threads:
    count: 2
```

## Setup

1. **Clone the repository:**

    ```bash
    git clone https://github.com/avecoss/report-generator.git
    cd report-generator
    ```

2. **Configure the database:**

    Ensure your PostgreSQL database is running and the specified database, username, and password in the application.yml file are correct.

3. **Build the project:**

    For Maven:
    ```bash
    mvn clean install
   ```

## Implementation Details
**DataFetcher**

Responsible for fetching data from the database using batch processing to avoid memory issues.

**ExcelWriter**

Handles writing data to the Excel file using SXSSFWorkbook to manage memory efficiently.

**MainService**

Coordinates the fetching and writing of data using a thread pool to parallelize the work and improve performance.

### Contributors
- [avexcoss](https://github.com/avecoss)