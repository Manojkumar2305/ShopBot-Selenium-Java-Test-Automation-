# ShopBot-Selenium-Java-Test-Automation-
Selenium-Java Test Automation Framework for SauceDemo using POM, TestNG, WebDriverManager, and Extent Reports. Covers authentication, product validation, cart, checkout, and problem user scenarios with scalable and maintainable design.

# 🛒 ShopBot – Selenium Java Automation Framework

ShopBot is a **Selenium-Java Test Automation Framework** built to automate a retail e-commerce application.
It follows industry-standard practices like **Page Object Model (POM)**, **TestNG**, and **data-driven testing** to ensure scalability, maintainability, and reusability.

The framework automates end-to-end workflows including authentication, product validation, cart operations, checkout, and error scenarios.

---

## 🎯 Objective

This project demonstrates:

* Selenium WebDriver automation
* Page Object Model design pattern
* TestNG framework usage
* Config-driven execution
* Clean and scalable framework design

---

## 🌐 Application Under Test

* URL: https://www.saucedemo.com
* Test Users:

  * `standard_user` – normal flow
  * `locked_out_user` – login blocked
  * `problem_user` – UI issues

---

## 🧩 Test Modules Covered

### 🔐 1. Authentication

* Login with valid and invalid users
* Locked user validation
* Logout functionality

### 📦 2. Product Listing

* Verify product display
* Sorting (Name & Price)
* Product detail validation

### 🛒 3. Cart

* Add/remove items
* Cart badge verification
* Persistence after navigation

### 💳 4. Checkout

* Fill customer details
* Order summary validation
* Complete order flow

### ⚠️ 5. Problem User

* Validate broken UI behavior
* Detect incorrect images
* Cart behavior issues

---

## 🏗️ Framework Architecture

```
src
├── main/java
│   ├── base            → DriverManager (WebDriver setup)
│   ├── pages           → Page classes (UI actions)
│   ├── config          → ConfigReader (reads properties)
│   ├── utils           → Reusable utilities
│   └── listeners       → Screenshot & reporting
│
├── test/java
│   ├── base            → BaseTest (setup/teardown)
│   └── tests           → Test classes
│
└── test/resources
    └── config.properties
```

---

## ⚙️ Key Features

✅ Page Object Model (POM)
✅ TestNG execution with testng.xml
✅ Config-driven framework
✅ Parallel execution support
✅ Retry mechanism for failed tests
✅ Screenshot capture on failure
✅ Extent HTML reporting
✅ No hardcoded values
✅ No Thread.sleep() (uses explicit waits)

---

## 🔧 Configuration

All configurable values are stored in:

```
config.properties
```

Example:

```
browser=chrome
base.url=https://www.saucedemo.com
timeout=15
headless=false
```

Accessed via:

```
ConfigReader.getInstance().getBaseUrl()
```

---

## ▶️ How to Run

### Using Maven:

```
mvn clean test
```

### Using TestNG:

* Right-click `testng.xml`
* Run as TestNG Suite

---

## 📊 Reporting

* ExtentReports generates HTML reports
* Screenshots captured on failure
* Stored in:

```
/test-output/
```

---

## ⚠️ Rules Followed

* ❌ No Thread.sleep()
* ❌ No hardcoded URLs
* ❌ No WebDriver in test classes
* ✅ Clean POM architecture
* ✅ Config-driven execution

---

## 🚀 Good to Have (Implemented / Extendable)

* DataProvider (JSON/Excel)
* Parallel execution
* Headless execution
* FluentWait for dynamic elements
* Retry logic using IRetryAnalyzer

---

## 🧠 Learning Outcomes

* Real-world automation framework design
* Separation of concerns
* Scalable test architecture
* Debugging and maintainability

---

## 📌 Future Enhancements

* CI/CD integration (Jenkins/GitHub Actions)
* Docker execution
* Cross-browser testing grid
* API + UI hybrid framework

---

## 👨‍💻 Author

Developed as part of automation framework practice to build industry-level testing skills.

---
