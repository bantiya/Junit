
# JUNIT

Explore the power of JUnit testing with our comprehensive repository! This project is designed to showcase best practices in writing and organizing JUnit tests


Generally you follow this setting while writing a test:
- MOCK => EXECUTE => VERIFY

Function name should be in this format: { should_..._When_...() }
- should_ReturnTrue_When_DietRecommended()

Break the unit test in three steps: 
- // Given (Arrange)
- // When (Act)
- // Then (Assert)



## Architecture
- JUnit Platform: (The platform is responsible for launching testing frameworks on the JVM)
- JUnit Jupiter
- JUnit Vintage: (JUnit Vintage supports running tests based on JUnit 3 and JUnit 4 on the JUnit 5 platform)



 

 

## Annotations

### @Test 
Used to annotate a test function.

### @RepeatedTest 
Used to run the test multiple times. Generally used when the method under test generates random values, or changes their state.

```Java
@RepeatedTest(value=10, name = RepeatedTest.LONG_DISPLAY_NAME)
void should_ReturnCorrectDietPlan_When_CorrectCoder() { ... }
```

### @ParameterizedTest
Used to test multiple test cases without making different test functions

### @ValueSource
Used to specify a single array of literal values to be passed to a parameterized test method.

### @CsvSource
Used to define data in a Comma Seperated way to pass it to the @ParameterizedTest.

```Java
@ParameterizedTest(name= "Weight={0}, Height={1}")
@CsvSource(value = {"89.0, 1.72", "95.0, 1.75", "110.0, 1.75"})
void should_ReturnTrue_When_DietRecommended(double coderWeight, double coderHeight){ ... }
```


### @CsvFileSource 
Used to give a file directly to the test

```Java
@ParameterizedTest(name= "Weight={0}, Height={1}")
@CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
void should_ReturnTrue_When_DietRecommended(double coderWeight, double coderHeight){ ... }
```

### @BeforeEach
 Run this function before each test.

```Java
@BeforeEach
void setup() {
   this.dietPlanner = new DietPlanner(20, 30, 50);
}
```


### @AfterEach
Run this function after each test runs.

```Java
@AfterEach
void afterEach() {
   System.out.println("A unit test was finished.");
}
```


### @BeforeAll 
Run this function before all the test functions. Generally used when there are some DB or server connections that need to be made.

```Java
@BeforeAll
static void beforeAll(){
   System.out.println("Setting up Db connection, before all unit test starts");
}
```

### @AfterAll 
Run this function after all the test functions. Generally used when we need to disconnect DB or server connections.

```Java
@AfterAll
static void afterAll(){
   System.out.println("Close Db connection, after all unit test");
}
```


### @DisplayName 
Used to display custom display names.

```Java
@Test
@DisplayName(">>>>> Sample Method Display Name")
void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){
 . . . 
}
```


### @Disabled 
Used to ignore a test (Skip a Test)

```Java
@Test
@Disabled
void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements(){
 . . .
}
```

### @DisableOnOS()
Used to ignore a test on a specific OS (Skip a Test)

```Java
@Test
@DisabledOnOs(OS.MAC)
void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements(){ 
. . . 
}
```










## Assert Functions

### assertTrue()
To check that a condition is true. If the condition returns to true, the test passes; if it evaluates to false, the test fails.

```Java
// then (Assert)
assertTrue(recommended);
```


### assertFalse() 
To check that a condition is false. If the condition returns to false, the test passes; if it evaluates to true, the test fails.

```Java
// then (Assert)
assertFalse(recommended);
```


### assertThrows()
To test that an exception is thrown as expected.

```Java
// when (Act)
Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

// then (Assert)
assertThrows(ArithmeticException.class, executable);
```


### assertAll()
Used to group multiple assertions together, so that all of them run regardless.

```Java
assertAll(
       () -> assertEquals(1.82, coderWorstBMI.getHeight()),
       () -> assertEquals(98, coderWorstBMI.getWeight())
);
```


### assertNull()
Used to check that a condition returns Null or not.

```Java
// then (Assert)
assertNull(coderWorstBMI);
```


### assertTimeout 
Allows you to specify a duration within which a test executable must complete. If the executable takes longer than the specified duration, the test fails.
assertTimeout(Duration timeout, Executable executable)

```Java
@Test
void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements(){
   // given
   List<Coder> coders = new ArrayList<>();
   for(int i = 0; i < 10000; i++){
       coders.add(new Coder(1.0 + i, 10.0 + i));
   }

   // when
   Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

   // then
   assertTimeout(Duration.ofMillis(500), executable);
}
```


### assumeTrue(condition)
 Used to conditionally ignore a test based on certain conditions. It just skips the test if the condition is not fulfilled.

```Java
@Test
void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements(){
   // given
   assumeTrue(this.environment.equals("prod"));
   . . .
}
```


## @Nested

Enable you to organize your tests in a more hierarchical and structured manner, allowing for a cleaner separation of concerns and grouping of related test cases.
- Just add @Nested above a class to make it inner class.

## Cheat Sheet

![App Screenshot](https://raw.githubusercontent.com/bantiya/Junit/main/HealthyCoderApp/src/main/resources/Screenshot%202024-03-02%20at%206.20.14%20PM.png)

