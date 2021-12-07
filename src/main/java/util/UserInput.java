package util;

import java.util.Scanner;

public class UserInput {
    private Scanner keyboard = new Scanner(System.in);

    public int menuInput(int minValue, int maxValue) {
        boolean inputIsWrong = true;
        boolean isNumeric = false;
        int userInput = 0;
        do {
            isNumeric = keyboard.hasNextInt();
            if (isNumeric) { // is it an integer if yes then
                userInput = keyboard.nextInt(); // read number
                if (userInput >= minValue && userInput <= maxValue) {
                    inputIsWrong = false; // this means it is within range.
                }
            }
            if (inputIsWrong) { // only show this is the input was invalid.
                System.out.printf("Invalid input. Enter integer numbers from %d"
                                + " to %d%n",
                        minValue, maxValue);
            }
        } while (inputIsWrong);
        return userInput;
    }

    public int integerInput() {
        boolean inputIsWrong = true;
        boolean isNumber = false;
        int userInput = 0;
        do {
            isNumber = keyboard.hasNextInt();
            if (isNumber) { // is it a double if yes then
                userInput = keyboard.nextInt(); // read number
                inputIsWrong = false;
            }
            if (inputIsWrong) {
                System.out.printf("Invalid input. Enter a valid integer input");
                String Input = keyboard.nextLine();
                if (Input.equals("*")) {
                    userInput = 0;
                    inputIsWrong = false;
                }
            }
//            keyboard.nextLine(); // clear stream before next loop
        } while (inputIsWrong);
        return userInput;
    }

    public double doubleInput() {
        final double EPSILON = 1.0E-14;
        boolean inputIsWrong = true;
        boolean isNumeric = false;
        double userInput = 0.0;
        double lowerBoundCheck;
        double upperBoundCheck;

        do {
            isNumeric = keyboard.hasNextDouble();
            if (isNumeric) { // is it a double if yes then
                userInput = keyboard.nextDouble(); // read number
                lowerBoundCheck = Math.abs(userInput - 0); // Math call
                upperBoundCheck = Math.abs(userInput - 100); // Math call
                if ((userInput > 0 && userInput < 100) ||
                        lowerBoundCheck <= EPSILON ||
                        upperBoundCheck <= EPSILON) {
                    inputIsWrong = false;
                }
            }
            else {
                String Input = keyboard.nextLine();
                if (Input.equals("*")) {
                    userInput = -1;
                    inputIsWrong = false;
                }
            }
            if (inputIsWrong) {
                System.out.printf("Invalid input. Enter decimal numbers from %f" + " to %f%n or press * to skip the input", 0, 100);
                String Input = keyboard.nextLine();
                if (Input.equals("*")) {
                    userInput = 0.0;
                    inputIsWrong = false;
                }
            }
        } while (inputIsWrong);
        return userInput;
    }

    public String stringInput() {
        boolean inputIsWrong = true;
        boolean isString = false;
        String userInput = "";
        do {
            isString = keyboard.hasNextLine();
            if (isString) { // is it a string if yes then
                userInput = keyboard.nextLine(); // read string
                if (userInput.equals("*")) userInput = null;
                inputIsWrong = false;
            }
        } while (inputIsWrong);
        return userInput;
    }
}
