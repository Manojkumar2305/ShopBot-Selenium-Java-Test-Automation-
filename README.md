# ShopBot – SauceDemo Automation Framework

## AUT
**URL:** https://www.saucedemo.com  
**No backend setup needed** — use built-in test accounts

## Built-in Users (all password: secret_sauce)
| Username | Behavior |
|---|---|
| standard_user | Normal flow – all features work |
| locked_out_user | Login blocked – error shown |
| problem_user | Logs in but images broken, cart buggy |

## Tech Stack
| Tool | Version |
|---|---|
| Java | 11+ |
| Selenium | 4.18.1 |
| TestNG | 7.9.0 |
| WebDriverManager | 5.7.0 |
| ExtentReports | 5.1.1 |
| Apache POI | 5.2.5 |
| Jackson | 2.16.1 |

## Project Structure
```
ShopBot/
├── pom.xml
├── testng.xml
├── screenshots/           ← auto-created on failure
├── test-output/
│   └── ShopBotReport.html ← auto-generated after run
└── src/
    ├── main/java/com/shopbot/
    │   ├── config/ConfigReader.java
    │   ├── pages/
    │   │   ├── BasePage.java
    │   │   ├── LoginPage.java
    │   │   ├── ProductListPage.java
    │   │   ├── ProductDetailPage.java
    │   │   ├── CartPage.java
    │   │   └── CheckoutPage.java
    │   ├── utils/
    │   │   ├── DriverManager.java
    │   │   ├── ScreenshotUtil.java
    │   │   ├── ExtentReportManager.java
    │   │   └── RetryAnalyzer.java
    │   └── listeners/TestListener.java
    └── test/
        ├── java/com/shopbot/
        │   ├── dataproviders/TestDataProvider.java
        │   └── tests/
        │       ├── BaseTest.java
        │       ├── AuthTest.java       ← Module 1
        │       ├── ProductTest.java    ← Module 2
        │       ├── CartTest.java       ← Module 3
        │       ├── CheckoutTest.java   ← Module 4
        │       └── ProblemUserTest.java← Module 5
        └── resources/
            ├── config.properties
            ├── loginData.json
            └── testdata.xlsx
```

## Eclipse Setup
1. File → Import → Maven → Existing Maven Projects → browse to ShopBot folder
2. Right-click pom.xml → Maven → Update Project (Alt+F5)
3. Run: `mvn test`  OR  right-click testng.xml → Run As → TestNG Suite

## Key Design Points
| Rule | Implementation |
|---|---|
| No Thread.sleep() | WebDriverWait + FluentWait only |
| No hardcoded URLs/credentials | config.properties + testdata.xlsx |
| No WebDriver in test methods | DriverManager + BaseTest handle lifecycle |
| POM enforced | All locators/actions in Page classes |
| Data-driven | Excel (LoginData, CheckoutData) + JSON |
| Screenshot on failure | TestListener auto-captures |
| Parallel execution | thread-count="2" in testng.xml |
| Retry on failure | RetryAnalyzer (1 retry) |
| Headless mode | Toggle in config.properties |
