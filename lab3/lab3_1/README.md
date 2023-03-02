 # Lab 3.1

**a) Identify a couple of examples that use AssertJ expressive methods chaining.**

assertThat(allEmployees).hasSize(3).extracting(Employee::getName).containsOnly(alex.getName(), ron.getName(), bob.getName());

assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());

**b) Identify an example in which you mock the behavior of the repository (and avoid involving a 
database).**

In B_EmployeeService_UnitTest.

@Mock( lenient = true)
private EmployeeRepository employeeRepository;

**c) What is the difference between standard @Mock and @MockBean?**

@Mock is the default way to mock something in a java app.

@MockBean is used to mock something in a Spring Boot app; since the classes are often special to Spring's framework (like @Service), we need to mock in a different way that the framework understands.


**d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be 
used?**

Instead of using application.properties for testing, we can use a variant of it named application-integrationtest.properties specifically designed for integration tests. This way, those tests can have specific properties different from the actual application properties.


**e) the sample project demonstrates three test strategies to assess an API (C, D and E) developed 
with SpringBoot. Which are the main/key differences?**

C runs fast and light tests and no database or repository is involved.

D and E are the slowest tests because they load all Spring Boot context with @SpringBootTest.

In E, serialization and unserialization of data is tested, unlike in D.

D mocks a servlet while in E we have an actual REST client.