package com.healthycoderapp;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class BMICalculatorTest {

    private static String environment = "prod";

    @BeforeAll
    static void beforeAll(){
        System.out.println("Setting up Db connection, before all unit test starts");
    }

    @AfterAll
    static void afterAll(){
        System.out.println("Close Db connection, after all unit test");
    }

    @Nested
    class IsDietRecommendedTests{

        @ParameterizedTest(name= "Weight={0}, Height={1}")
        @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip = 1)
        void should_ReturnTrue_When_DietRecommended(double coderWeight, double coderHeight){
            // given (Arrange
            double weight = coderWeight;
            double height = coderHeight;

            // when (Act)
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            // then (Assert)
            assertTrue(recommended);
        }

        @Test
        void should_ReturnFalse_When_DietNotRecommended(){

            // given (Arrange
            double weight = 50;
            double height = 1.65;

            // when (Act)
            boolean recommended = BMICalculator.isDietRecommended(weight, height);

            // then (Assert)
            assertFalse(recommended);
        }

        @Test
        void should_ThrowArithmeticException_When_HeightZero(){

            // given (Arrange
            double weight = 50.0;
            double height = 0.0;

            // when (Act)
            Executable executable = () -> BMICalculator.isDietRecommended(weight, height);

            // then (Assert)
            assertThrows(ArithmeticException.class, executable);
        }
    }

    @Nested
    class FindCoderWithWorstBMITests{

        @Test
        @DisplayName(">>>>> Sample Method Display Name")
        void should_ReturnCoderWithWorstBMI_When_CoderListNotEmpty(){

            // given (Arrange)
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));

            // when (Act)
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // then (Assert)
            assertAll(
                    () -> assertEquals(1.82, coderWorstBMI.getHeight()),
                    () -> assertEquals(98, coderWorstBMI.getWeight())
            );
        }

        @Test
        @DisabledOnOs(OS.MAC)
        void should_ReturnCoderWithWorstBMIIn1Ms_When_CoderListHas10000Elements(){
            // given
            assumeTrue(BMICalculatorTest.environment.equals("prod"));
            List<Coder> coders = new ArrayList<>();
            for(int i = 0; i < 10000; i++){
                coders.add(new Coder(1.0 + i, 10.0 + i));
            }

            // when
            Executable executable = () -> BMICalculator.findCoderWithWorstBMI(coders);

            // then
            assertTimeout(Duration.ofMillis(500), executable);
        }

        @Test
        void should_ReturNullWithWorstBMI_When_CoderListNotEmpty(){

            // given (Arrange)
            List<Coder> coders = new ArrayList<>();

            // when (Act)
            Coder coderWorstBMI = BMICalculator.findCoderWithWorstBMI(coders);

            // then (Assert)
            assertNull(coderWorstBMI);
        }
    }

    @Nested
    class GetBMIScoresTests{
        @Test
        void should_ReturnCorrectBMIScoreArray_When_CoderListNotEmpty(){
            // given (Arrange)
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(1.82, 98.0));
            coders.add(new Coder(1.82, 64.7));
            double[] expected = {18.52, 29.59, 19.53};

            // when (Act)
            double[] bmiScores = BMICalculator.getBMIScores(coders);

            // then (Assert)
            assertArrayEquals(expected, bmiScores);
        }

        @Test
        void should_ThrowArithmeticException_When_HeightIsZero(){
            // given (Arrange)
            List<Coder> coders = new ArrayList<>();
            coders.add(new Coder(1.80, 60.0));
            coders.add(new Coder(0, 98.0));
            coders.add(new Coder(1.82, 64.7));
            double[] expected = {18.52, 29.59, 19.53};

            // when (Act)
            Executable executable = () -> BMICalculator.getBMIScores(coders);

            // then (Assert)
            assertThrows(ArithmeticException.class, executable);
        }
    }
}