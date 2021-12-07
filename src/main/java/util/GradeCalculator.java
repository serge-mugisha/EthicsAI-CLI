package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GradeCalculator {
    public static Double calculateFinalScore(Double midTerm1, Double midTerm2, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double assignment5) {
        Double finalScore = ((assignment1 + assignment2 + assignment3 + assignment4 + assignment5 + midTerm1 + midTerm2) / 700) * 100;
        return finalScore;
    }

    public static String calculatefinalGrade(Double finalScore) {
        String grade;
        if (finalScore >= 90) grade = "A+";
        else if (finalScore >= 85) grade = "A";
        else if (finalScore >= 80) grade = "A-";
        else if (finalScore >= 77) grade = "B+";
        else if (finalScore >= 73) grade = "B";
        else if (finalScore >= 70) grade = "B-";
        else if (finalScore >= 67) grade = "C+";
        else if (finalScore >= 63) grade = "C";
        else if (finalScore >= 60) grade = "C-";
        else if (finalScore >= 57) grade = "D+";
        else if (finalScore >= 53) grade = "D";
        else if (finalScore >= 50) grade = "D-";
        else grade = "F";
        return grade;
    }
}
