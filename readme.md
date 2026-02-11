# Sudin Khanal Selenium Automation Framework

A modern, scalable UI automation framework built using **Selenium 4 +
TestNG + Page Object Model (POM)**.

------------------------------------------------------------------------

## 🚀 Tech Stack

-   Java 25
-   Selenium 4.40.0
-   TestNG 7.12.0
-   Maven
-   ChromeDriver
-   SLF4J (logging)

------------------------------------------------------------------------

## 📂 Project Structure

    src
     ├── main
     │    └── java
     │         └── pages
     │              ├── LoginPage.java
     │              ├── InventoryPage.java
     │              ├── CartPage.java
     │              └── CheckOutPage.java
     │
     └── test
          ├── java
          │     ├── base
          │     │     └── BaseTest.java
          │     └── tests
          │           ├── LoginPageTests.java
          │           ├── InventoryPageTests.java
          │           └── CheckOutPageTests.java
          │
          └── resources
                ├── config.properties
                └── testng.xml

------------------------------------------------------------------------

## 🧠 Framework Highlights

-   Page Object Model design
-   Explicit wait strategy (no implicit waits)
-   Config-driven execution
-   Headless execution support
-   Thread-safe WebDriver management
-   Parallel test execution support

------------------------------------------------------------------------

## ▶️ How To Run

### Run All Tests

mvn clean test

### Run in Headless Mode

mvn clean test -Dheadless=true

### Run With Custom Timeout

mvn clean test -DtimeoutSeconds=12

------------------------------------------------------------------------

## 🧪 Test Coverage

### Login Tests

-   Valid user login
-   Locked user validation
-   URL verification

### Inventory Tests

-   Sort by Name (Z → A)
-   Sort by Price (Low → High)
-   Add items to cart
-   Navigate to cart

### Checkout Tests

-   Complete end-to-end checkout
-   Validate order success message

------------------------------------------------------------------------

## 👨‍💻 Author

Sudin Khanal\
Selenium Automation Framework -- 2026
