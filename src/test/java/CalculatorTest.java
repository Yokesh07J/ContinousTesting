import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Calculator implementation for laboratory demonstration.
 */
class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed.");
        }
        return a / b;
    }

    public int modulus(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Modulus by zero is not allowed.");
        }
        return a % b;
    }
}

/**
 * Automated Unit Tests for Continuous Testing lab experiment using JUnit 5.
 */
public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Test 1: Addition")
    void testAddition() {
        int result = calculator.add(10, 5);
        assertEquals(15, result, "10 + 5 should equal 15");
    }

    @Test
    @DisplayName("Test 2: Subtraction")
    void testSubtraction() {
        int result = calculator.subtract(20, 8);
        assertEquals(12, result, "20 - 8 should equal 12");
    }

    @Test
    @DisplayName("Test 3: Multiplication")
    void testMultiplication() {
        int result = calculator.multiply(6, 7);
        assertEquals(42, result, "6 * 7 should equal 42");
    }

    @Test
    @DisplayName("Test 4: Division")
    void testDivision() {
        int result = calculator.divide(50, 5);
        assertEquals(10, result, "50 / 5 should equal 10");
    }

    @Test
    @DisplayName("Test 5: Modulus Calculation")
    void testModulus() {
        int result = calculator.modulus(29, 5);
        assertEquals(4, result, "29 % 5 should equal 4");
    }
}
