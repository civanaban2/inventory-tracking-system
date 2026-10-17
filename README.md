# Inventory Tracking System

A Java Swing desktop application for managing product inventory with SQLite
persistence. Developed as a Mathematical Engineering course project at
Istanbul Technical University (`MAT335E`).

The application separates product records, database access, table management,
and UI screens. It provides an administrator interface for inventory changes
and a user interface for browsing products.

## Features

- Add, update, and delete products from the administrator panel.
- Browse products in a Swing table with category filtering.
- Sort by name, ID, price, quantity, supplier, expiry date, or category.
- Generate a low-stock report using selectable quantity thresholds
  (5, 10, 20, 50, or 100) and sort by quantity or product name.
- Persist products in a local SQLite database through JDBC prepared statements.

Each product records a name, ID, price, quantity, supplier, expiry date, and
category. A product appears in a low-stock report when its quantity is
strictly less than the selected threshold.

## Requirements

- JDK 23, matching the source and target versions in `pom.xml`.
- Apache Maven.
- A graphical desktop session; the Swing UI cannot run headlessly.

The source uses unnamed lambda parameters (`_`), so an older JDK may reject
it. Maven downloads the SQLite JDBC dependency on the first build.

## Build and run

Run these commands from the repository root:

```sh
git clone https://github.com/civanaban2/inventory-tracking-system.git
cd inventory-tracking-system
java -version
mvn -version
mvn compile dependency:copy-dependencies
```

On macOS or Linux:

```sh
java -cp "target/classes:target/dependency/*" edu.itu.mat335e.Main
```

On Windows (PowerShell or Command Prompt):

```powershell
java -cp "target/classes;target/dependency/*" edu.itu.mat335e.Main
```

Alternatively, import `pom.xml` into an IDE, select JDK 23, and run
`edu.itu.mat335e.Main` with the repository root as the working directory.
There is no executable JAR configuration; `java -jar` is not the launch method.

## Demo accounts

| Role | Username | Password |
| --- | --- | --- |
| Administrator | `root` | `1234` |
| User | `user` | `1234` |

These are hard-coded demonstration credentials, not a production authentication
system. The user screen is for browsing; the administrator screen exposes
product changes and low-stock reporting.

## Database

The application opens `src/main/data/inventory.db` relative to the current
working directory. Start it from the repository root so this path resolves
correctly. The repository includes a database file; changes made through the
application modify that file. Copy it elsewhere before experimenting if you
want to preserve its starting contents.

`ProductDAO` creates the following table if it does not already exist:

```sql
CREATE TABLE IF NOT EXISTS products (
    name TEXT,
    id TEXT,
    price REAL,
    quantity INTEGER,
    supplier TEXT,
    expire_date TEXT,
    category TEXT
);
```

To start with an empty inventory, close the application, back up the database,
and remove `inventory.db`. Keep the `src/main/data/` directory in place; SQLite
and the DAO recreate the file and table when the application connects.

## Structure

```text
pom.xml
src/main/
  data/inventory.db
  java/edu/itu/mat335e/
    Main.java
    model/Product.java
    database/
      DatabaseConnection.java
      ProductDAO.java
    gui/
      Frame.java
      LoginFrame.java
      AdminFrame.java
      UserFrame.java
      ReportFrame.java
      TableManager.java
```

## Implementation notes and limitations

- Authentication uses fixed credentials and UI-level roles. There are no user
  records, password hashing, or server-side authorization.
- Prices are represented as `double`; expiry dates are stored and sorted as
  text. Date-format validation and financial decimal handling are not provided.
- Duplicate-ID checking occurs in the administrator UI; the database schema
  does not enforce a primary key or uniqueness constraint.
- Numeric input validation and database resource/error handling need further
  work. Invalid form values can cause runtime errors.
- There is no automated test suite or migration system in the repository.
  This is a local educational application rather than a production inventory
  service.
