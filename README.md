# Continuous Testing with Jenkins and JUnit 5

## Experiment Title
**"Perform Continuous Testing by integrating automated test execution into the Jenkins pipeline. Generate and analyze test reports."**

---

## 1. Objective
To understand and implement **Continuous Testing (CT)** in a CI/CD lifecycle by:
1. Writing automated unit test suites using **JUnit 5**.
2. Configuring **Maven Surefire Plugin** to execute tests during the build cycle and export standardized XML reports.
3. Automating test execution via a **Jenkins Declarative Pipeline**.
4. Publishing and analyzing test reports within Jenkins using the **JUnit Plugin**.
5. Demonstrating pipeline failure handling when a regression or broken test occurs.

---

## 2. Tools & Technologies Used
| Component | Tool / Technology | Version | Purpose |
| :--- | :--- | :--- | :--- |
| **Language** | Java | 17 (or 21) | Core programming language |
| **Testing Framework** | JUnit 5 (Jupiter) | 5.10.2 | Automated unit testing framework |
| **Build & Dependency Tool** | Apache Maven | 3.9+ | Build management, compilation, test runner |
| **Test Reporting Plugin** | Maven Surefire Plugin | 3.2.5 | Executes unit tests & creates XML reports |
| **CI/CD Automation** | Jenkins | 2.x (Windows) | Orchestrates checkout, build, testing, reporting |
| **Jenkins Plugin** | JUnit Plugin | Latest | Parses `target/surefire-reports/*.xml` into visual charts |
| **Version Control** | Git & GitHub | Latest | Source code repository and webhook integration |

---

## 3. Project Structure
```text
ContinuousTesting/
├── pom.xml
├── Jenkinsfile
├── README.md
└── src/
    └── test/
        └── java/
            └── CalculatorTest.java
```

### File Breakdown:
- **`pom.xml`**: Defines project coordinates (`com.example:continuous-testing:1.0-SNAPSHOT`), Java 17 compliance, JUnit 5 Jupiter dependency, and Maven Surefire Plugin.
- **`src/test/java/CalculatorTest.java`**: Contains the `Calculator` logic and 5 unit tests covering:
  - `testAddition`: $10 + 5 = 15$
  - `testSubtraction`: $20 - 8 = 12$
  - `testMultiplication`: $6 \times 7 = 42$
  - `testDivision`: $50 / 5 = 10$
  - `testModulus`: $29 \pmod 5 = 4$
- **`Jenkinsfile`**: Declarative Jenkins Pipeline executing `Checkout`, `Build` (`bat 'mvn clean compile'`), `Automated Testing` (`bat 'mvn test'`), and `Generate Test Report` (`junit 'target/surefire-reports/*.xml'`).
- **`README.md`**: Laboratory manual and documentation.

---

## 4. How to Run Tests Locally

Before integrating into Jenkins, verify test execution on your local machine:

1. Open PowerShell or Command Prompt.
2. Navigate to the project directory:
   ```powershell
   cd c:\ContinousIntegeration\ContinuousTesting
   ```
3. Run the automated test suite:
   ```powershell
   mvn clean test
   ```

### Expected Local Console Output:
```text
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running CalculatorTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.114 s -- in CalculatorTest
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

### Generated Test Reports:
Surefire generates report files under `target/surefire-reports/`:
- `target/surefire-reports/TEST-CalculatorTest.xml` (Standard XML consumed by Jenkins)
- `target/surefire-reports/CalculatorTest.txt` (Plain text summary)

---

## 5. Jenkins Setup & Configuration (Windows)

### Prerequisites in Jenkins:
1. **JUnit Plugin**: Ensure the **JUnit** plugin is installed (**Manage Jenkins** > **Plugins** > **Installed Plugins** > search for `JUnit Plugin`).
2. **Maven & Java Tool Configuration**:
   - Go to **Manage Jenkins** > **Tools**.
   - Under **JDK**, ensure Java 17 (or Java 21) is configured, or ensure `JAVA_HOME` is set in Windows System Environment Variables.
   - Under **Maven**, configure Maven installation or ensure `mvn` is accessible in the Windows system `PATH`.

---

## 6. Creating and Executing the Jenkins Pipeline Job

### Step-by-Step Instructions:
1. Log in to Jenkins (`http://localhost:8080`).
2. Click **New Item** from the left dashboard.
3. Enter job name: `Continuous-Testing-Lab`.
4. Select **Pipeline** and click **OK**.
5. In the configuration page, scroll down to the **Pipeline** section.
6. Choose one of two options:

#### Option A: Pipeline script from SCM (Recommended for GitHub)
- **Definition**: `Pipeline script from SCM`
- **SCM**: `Git`
- **Repository URL**: `https://github.com/<your-username>/<your-repo-name>.git`
- **Branch Specifier**: `*/main` or `*/master`
- **Script Path**: `Jenkinsfile` (or `ContinuousTesting/Jenkinsfile` if the repo contains the parent folder)

#### Option B: Direct Pipeline Script (Local Experimentation)
- **Definition**: `Pipeline script`
- Paste the content of `Jenkinsfile` directly into the editor.
- If running directly on the local machine workspace, ensure the working directory points to `ContinuousTesting`.

7. Click **Save**.
8. Click **Build Now** to execute the pipeline.

---

## 7. Where Jenkins Gets the Test Report

1. When the `Automated Testing` stage runs:
   ```groovy
   bat 'mvn test'
   ```
   Maven Surefire executes all tests inside `src/test/java` and saves the test execution results in XML format inside:
   ```text
   target/surefire-reports/TEST-CalculatorTest.xml
   ```
2. When the `Generate Test Report` stage runs:
   ```groovy
   junit 'target/surefire-reports/*.xml'
   ```
   The Jenkins JUnit Plugin reads all XML files in `target/surefire-reports/`.
3. Jenkins aggregates test counts, execution durations, individual test statuses (pass/fail/skip), and stack traces.

---

## 8. How to View and Analyze Test Reports in Jenkins

1. Go to the **Continuous-Testing-Lab** job page.
2. In the left menu of the job, click **Test Result Trend** or click directly on the latest build number (e.g., `#1`).
3. Click **Test Result** in the left menu of the build.
4. You will see:
   - **Total Tests**: `5`
   - **Failures**: `0`
   - **Skipped**: `0`
   - **Package**: `(root)`
   - **Class**: `CalculatorTest`
5. Clicking on `CalculatorTest` lists each individual test:
   - `testAddition` - Passed
   - `testSubtraction` - Passed
   - `testMultiplication` - Passed
   - `testDivision` - Passed
   - `testModulus` - Passed

---

## 9. Demonstrating the 3-Build Continuous Testing Cycle

To demonstrate continuous feedback and pipeline gates, execute the following 3 builds:

### Build 1: All Tests Pass (Initial State)
- **Code State**: Default `CalculatorTest.java` (all 5 assertions correct).
- **Execution**: Click **Build Now** in Jenkins.
- **Pipeline Stage View**:
  - `Checkout` -> SUCCESS
  - `Build` -> SUCCESS
  - `Automated Testing` -> SUCCESS
  - `Generate Test Report` -> SUCCESS
- **Console Output**: `All automated tests passed successfully!`
- **Test Report**: 5 tests passed, 0 failures.
- **Overall Status**: **SUCCESS (Blue/Green)**.

---

### Build 2: One Test Intentionally Broken (Defect Injection)
- **Modify Code**: Open `src/test/java/CalculatorTest.java` and intentionally break one assertion:
  ```java
  @Test
  @DisplayName("Test 1: Addition")
  void testAddition() {
      int result = calculator.add(10, 5);
      // Change expected value from 15 to 99 to simulate a test failure
      assertEquals(99, result, "10 + 5 should equal 15");
  }
  ```
- **Commit & Push / Trigger Build**: Run **Build Now** in Jenkins.
- **Pipeline Behavior**:
  - `Automated Testing` stage fails because `mvn test` exits with return code 1.
  - Jenkins executes the `post { failure { ... } }` block:
    `"Automated testing failed. Check the Jenkins test report."`
- **Overall Status**: **FAILURE (Red)**.
- **Analyzing the Defect in Jenkins**:
  1. Click on Build `#2` > **Test Result**.
  2. Notice the summary: **5 tests, 1 failure**.
  3. Under **Failed Tests**, click `CalculatorTest.testAddition`.
  4. View the failure reason and stack trace:
     ```text
     org.opentest4j.AssertionFailedError: 10 + 5 should equal 15 ==> 
     Expected :99
     Actual   :15
     ```
  5. The **Test Result Trend** graph clearly indicates 1 failed test in red and 4 passing tests in blue.

---

### Build 3: Defect Resolved (Recovery)
- **Fix Code**: Revert the assertion back to the expected value:
  ```java
  assertEquals(15, result, "10 + 5 should equal 15");
  ```
- **Commit & Push / Trigger Build**: Run **Build Now** in Jenkins.
- **Pipeline Behavior**:
  - All stages complete successfully.
  - Jenkins executes `post { success { ... } }`.
- **Test Report**: 5 tests passed, 0 failures.
- **Overall Status**: **SUCCESS (Blue/Green)**.
- **Test Result Trend**: Displays recovery from the failed build back to 100% pass rate.

---

## 10. Expected Final Result for Laboratory Record

1. **Continuous Integration & Testing Verified**: Automated test execution runs on every build without manual intervention.
2. **Quality Gate Enforced**: A broken build prevents downstream progression and notifies the team immediately.
3. **Traceability**: Jenkins generates historical test result trends and detailed failure diagnostics for complete auditability.
