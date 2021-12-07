package ethicsAi;
import jdbc.Controller;
import jdbc.JDBCController;
import jdbc.url.MySQLURLBuilder;
import students.Student;
import util.MenuSystem;
import util.UserInput;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class EthicsAICLI {

    private static String firstName;
    private static String lastName;
    private static String DOB;
    private static Double midTerm1;
    private static Double midTerm2;
    private static Double assignment1;
    private static Double assignment2;
    private static Double assignment3;
    private static Double assignment4;
    private static Double assignment5;

    public static void main(String[] args) {
        Controller controller;
        System.out.println("Ethics AI CLI");
        controller = new JDBCController();
        controller.setURLBuilder(new MySQLURLBuilder());
        controller.setDataBase("localhost", "3306", "ethicsai").addConnectionURLProperty("serverTimezone", "UTC").addConnectionURLProperty("useUnicode", "true").setCredentials("root", "root");
        controller.connect();

        MenuSystem menu = new MenuSystem();
        UserInput input = new UserInput();
        String menuText = "";
        int choice = 0;
        boolean loopProgram = true;

        while (loopProgram) {
            menuText = menu.optionsList();
            System.out.println(menuText);
            choice = input.menuInput(0, 10);

            switch (choice) {
                case 0:
                    loopProgram = false;
                    break;
                case 1:
                    ArrayList<Student> studentsMarks = controller.getStudents();
                    System.out.println("Application output --> List of Students");
                    for (int i = 0; i < studentsMarks.size(); i++) {
                        Student std = studentsMarks.get(i);
                        System.out.println(String.format("ID: %s  Names: %s %s  DOB: %s  Email: %s  Midterm1: %.2f  Midterm2: %.2f  Assignment1: %.2f  Assignment2: %.2f  Assignment3: %.2f  Assignment4: %.2f  Assignment5: %.2f  FinalScore: %.2f  FinalGrade: %s",
                                std.getStudentId(), std.getFirstName(), std.getLastName(), std.getDOB(), std.getEmailAddress(), std.getMidTerm1(), std.getMidTerm2(), std.getAssignment1(),
                                std.getAssignment2(), std.getAssignment3(), std.getAssignment4(), std.getAssignment5(), std.getFinalScore(), std.getFinalGrade()));
                    }
                    break;

                case 2:
                    System.out.println("User input --> Enter student's ID:");
                    int id = input.integerInput();
                    Student sstd = controller.getStudent(id);
                    if (sstd.getStudentId() != 0) {
                        System.out.println("Application output --> Student information");
                        System.out.println(String.format("ID: %s  Names: %s %s  DOB: %s  Email: %s  Midterm1: %.2f  Midterm2: %.2f  Assignment1: %.2f  Assignment2: %.2f  Assignment3: %.2f  Assignment4: %.2f  Assignment5: %.2f  FinalScore: %.2f  FinalGrade: %s",
                                sstd.getStudentId(), sstd.getFirstName(), sstd.getLastName(), sstd.getDOB(), sstd.getEmailAddress(), sstd.getMidTerm1(), sstd.getMidTerm2(), sstd.getAssignment1(),
                                sstd.getAssignment2(), sstd.getAssignment3(), sstd.getAssignment4(), sstd.getAssignment5(), sstd.getFinalScore(), sstd.getFinalGrade()));
                    }

                    break;

                case 3:
                    inputStudentInfo(1);
                    Student addedStudent = controller.addStudent(firstName, lastName, DOB, midTerm1, midTerm2, assignment1, assignment2, assignment3, assignment4, assignment5);
                    System.out.println("Application output --> Student " + addedStudent.getFirstName() + " " + addedStudent.getLastName() + "Added Successfully");
                    break;

                case 4:
                    System.out.println("User input --> Enter student's ID");
                    int updateID = input.integerInput();
                    Student currentStudent = controller.getStudent(updateID);
                    if (currentStudent.getStudentId() != 0) {
                        System.out.println("Application output --> Enter the updated data for " + currentStudent.getFirstName() + " " + currentStudent.getLastName());
                        inputStudentInfo(2);
                        Student updatedStudent = controller.updateStudent(currentStudent.getStudentId(), firstName, lastName, DOB, midTerm1, midTerm2, assignment1, assignment2, assignment3, assignment4, assignment5);
                        if (updatedStudent.getStudentId() != 0)
                            System.out.println("Application output --> Student" + updatedStudent.getFirstName() + " " + updatedStudent.getLastName() + "Added");
                    }
                    break;

                case 5:
                    System.out.println("User input --> Enter student's ID");
                    int deleteID = input.integerInput();
                    Student deletedStudent = controller.deleteStudent(deleteID);
                    if (deletedStudent.getStudentId() != 0)
                        System.out.println("Application output --> Student " + deletedStudent.getFirstName() + " " + deletedStudent.getLastName() + " Added");
                    break;

                case 6:
                    System.out.println("Application output --> Class Average Score:");
                    LinkedHashMap<String, Double> stats = controller.deliverablesStats();
                    for (String key : stats.keySet()) {
                        System.out.println(String.format("%s: %.2f   ", key, stats.get(key)));
                    }

                    System.out.println("----------------------------------------- \n");
                    System.out.println("Application output --> Students to class Statistics:");

                    LinkedHashMap<String, Double[]> averageStats = controller.deliverablesStudentsAverageStats();
                    for (String key : averageStats.keySet()) {
                        System.out.println(String.format("%s: \n Above average: %.0f Below average: %.0f", key, averageStats.get(key)[0], averageStats.get(key)[1]));
                    }

                    System.out.println("----------------------------------------- \n");
                    System.out.println("Application output --> Overall Statistics:");

                    LinkedHashMap<String, Double> finalScoreStats = controller.overallFinalScoreStats();
                    System.out.print(String.format("Average overall final score: %.2f \n Highest score: %.2f, Lowest score: %.2f \n Passed students: %.0f, failed Students: %.0f  "
                            , finalScoreStats.get("average"), finalScoreStats.get("high"), finalScoreStats.get("low"), finalScoreStats.get("pass"), finalScoreStats.get("fail")));
                    break;
                case 7:
                    loopProgram = false;
                    System.out.println("Quitting... Have a nice Day!");
                    break;
            }
            System.out.println();
        }
    }

    private static void inputStudentInfo(int type) {
        String updateInput = type == 2 ? " or press * to skip the input" : "";
        UserInput input = new UserInput();

        System.out.println("Input the student's first name: " + updateInput);
        firstName = input.stringInput();
        System.out.println("Input the student's last name: " + updateInput);
        lastName = input.stringInput();
        System.out.println("Input the student's Date of birth (YYYY-MM-DD): " + updateInput);
        DOB = input.stringInput();
        System.out.println("Input the student's Mid Term 1 Marks: " + updateInput);
        midTerm1 = input.doubleInput();
        System.out.println("Input the student's Mid Term 2 Marks: " + updateInput);
        midTerm2 = input.doubleInput();
        System.out.println("Input the student's Assignment 1 Marks: " + updateInput);
        assignment1 = input.doubleInput();
        System.out.println("Input the student's Assignment 2 Marks: " + updateInput);
        assignment2 = input.doubleInput();
        System.out.println("Input the student's Assignment 3 Marks: " + updateInput);
        assignment3 = input.doubleInput();
        System.out.println("Input the student's Assignment 4 Marks: " + updateInput);
        assignment4 = input.doubleInput();
        System.out.println("Input the student's Assignment 5 Marks: " + updateInput);
        assignment5 = input.doubleInput();
    }
}
