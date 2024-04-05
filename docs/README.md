# Separating Unit and Integration Tests in Spring Boot Applications

## Introduction

In software development, ensuring the quality and reliability of your code is paramount. Testing plays a critical role in this process, and Spring Boot applications benefit greatly from a well-defined testing strategy. Separating unit and integration tests offers a structured approach to achieving comprehensive code coverage and enhanced maintainability.

Below is a breakdown of what this document will guide you through:

    - The fundamental concepts of unit and integration testing.
    - Why separating these tests is beneficial for Spring Boot applications.
    - Practical techniques for implementing separate unit and integration tests.

## Unit Tests

Unit tests focus on testing individual units of code in isolation, typically a single class or method. Common testing frameworks used in Spring Boot applications include JUnit and Mockito.

- Mocking Dependencies: Unit tests should not rely on external dependencies like databases or external APIs. Use frameworks like Mockito to mock these dependencies and create a controlled testing environment.

Example Unit Test (using Mockito):
```java
@Test
public void testMyService_addNumbers_success() {
  // Arrange (set up mocks, test data)
  MyService service = new MyService(Mockito.mock(MyRepository.class));
  int num1 = 10;
  int num2 = 20;

  // Act (call the method under test)
  int result = service.addNumbers(num1, num2);

  // Assert (verify the expected behavior)
  assertEquals(30, result);
}
```

## Integration Tests

Integration tests go beyond unit tests by examining how different system components interact with each other. They involve a broader scope, often encompassing services, repositories, and external systems.

- Spring Boot Testing Annotations: Utilize annotations like @SpringBootTest to spin up a full Spring application context for integration tests.
- Test Databases: Consider using in-memory databases like H2 or testcontainers.

Example Integration Test (using @SpringBootTest):
```java
@SpringBootTest
@IntegrationTest
public class MyServiceIntegrationTest {

  @Autowired
  private MyService service;

  @Autowired
  private MyRepository repository;

  @Test
  public void testMyService_saveAndRetrieveData_success() {
    // Arrange (set up test data)
    MyData data = new MyData("Test Data");

    // Act (save data and retrieve it)
    service.saveData(data);
    MyData retrievedData = repository.findById(data.getId()).get();

    // Assert (verify retrieved data matches)
    assertEquals(data, retrievedData);
  }
}
```

## Separating Test Files:

- Create separate folders for unit tests (src/test/java/unittest) and integration tests (src/test/java/integrationtest).
- Place all unit test files within the unittest folder and integration test files within the integrationtest folder. This promotes better organization and clarity.

## Maven Configuration:

The Maven configuration are defined into two parts:
- Default Unit Test Configuration: This section configures the maven-surefire-plugin to include all test files within the **/unittest/** path. This means any file ending with .java under the unittest folder or its subfolders will be considered a unit test by default.

```xml
<plugin>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
    <configuration>
        <includes>
            <include>**/unittest/**</include>
        </includes>
    </configuration>
</plugin>
```

- Integration Test Profile: The ITs profile defines a separate configuration for integration tests. It activates the same maven-surefire-plugin but includes test files under the **/integrationtest/** path. This ensures only integration tests are run when this profile is activated.
```xml
<profiles>
    <profile>
    <id>ITs</id>
        <build>
            <plugins>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>2.22.2</version>
                    <configuration>
                        <includes>
                            <include>**/integrationtest/*</include>
                        </includes>
                    </configuration>
                </plugin>
                <plugin>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <version>2.22.2</version>
                    <configuration>
                        <includes>
                            <include>**/unittest/**</include>
                        </includes>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

## Running Tests with Maven:

- Unit Tests (Default): To run unit tests, simply execute mvn test on the command line. This will use the default configuration and run all unit tests within the unittest folder structure.

- Integration Tests: To run both unit and integration tests specifically, you need to activate the ITs profile. You can achieve this by using mvn test -P ITs. This command instructs Maven to use the ITs profile, which includes both the unit and integration tests configuration and runs both tests (unit and integration).

## Building the Project:

- Using mvn clean install will by default only execute unit tests due to the default maven-surefire-plugin configuration.
- To run the integration test during the build process, you can use mvn clean install -P ITs. This activates the ITs profile and executes both unit and integration tests.



## Conclusion

Separating unit and integration tests fosters a well-structured testing approach for Spring Boot applications. This approach improves code quality, maintainability, and developer confidence. 
