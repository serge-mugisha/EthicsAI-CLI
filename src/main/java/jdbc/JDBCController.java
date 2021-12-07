package jdbc;
import jdbc.url.JDBCUrl;
import students.Student;
import util.GradeCalculator;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JDBCController implements Controller {

    private JDBCUrl builder;
    private JDBCModel model;
    private boolean isLoggedIn;
    private int activeAccountId;
    private ArrayList<Student> students;
    private Student student = new Student();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    public JDBCController() {
        this.model = new JDBCModel();
        this.isLoggedIn = false;
    }

    public Controller setURLBuilder(JDBCUrl builder) {
        this.builder = builder;
        return this;
    }

    public Controller setCredentials(String user, String pass) {
        model.setCredentials(user, pass);
        return this;
    }

    public Controller setDataBase(String address, String port, String catalog) {
        builder.setURL(address, port, catalog);
        return this;
    }

    public Controller addConnectionURLProperty(String key, String value) {
        builder.addURLProperty(key, value);
        return this;
    }

    public Controller connect() {
        try {
            model.connectTo(builder.getURL());
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return this;
    }

    public void close() {
        try {
            model.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public boolean loginWith(String username, String password) {
        int loginStatus = 0;
        try {
            loginStatus = this.activeAccountId = model.loginWith(username, password);
            this.isLoggedIn = true;

        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return loginStatus != -1;
    }

    public ArrayList<Student> getStudents() {
        try {
            // System.out.println("Getting Students... JDBC Controller status: " + model.isConnected());
            if (model.isConnected()) {
                this.students = model.getStudents();
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return this.students;
    }

    public Student getStudent(int studentID) {
        try {
            if (model.isConnected()) {
                //students.clear();
                this.student = model.getStudent(studentID);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return this.student;
    }

    public Student addStudent(String firstName, String lastName, String dob, Double midTerm1, Double midTerm2, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double assignment5) {
        try {
            if (model.isConnected()) {
                Double finalScore = GradeCalculator.calculateFinalScore(midTerm1, midTerm2, assignment1, assignment2, assignment3, assignment4, assignment5);
                String finalGrade = GradeCalculator.calculatefinalGrade(finalScore);
                String emailAddress = generateEmail(firstName, lastName, dob);
                Date DOB = format.parse(dob);
                Student newStudent = new Student(0, firstName, lastName, DOB, emailAddress, midTerm1, midTerm2, assignment1, assignment2, assignment3, assignment4, assignment5, finalScore, finalGrade);
                student = model.addStudent(newStudent);
            }
        } catch (SQLException | ParseException e) {
            throw new IllegalStateException(e);
        }
        return student;
    }

    public Student updateStudent(int studentId, String firstName, String lastName, String DOB, Double midTerm1, Double midTerm2, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double assignment5) {
        try {
            if (model.isConnected()) {
                students.clear();
                Double finalScore = GradeCalculator.calculateFinalScore(midTerm1, midTerm2, assignment1, assignment2, assignment3, assignment4, assignment5);
                String finalGrade = GradeCalculator.calculatefinalGrade(finalScore);

                Student updatedStudent = model.getStudent(studentId);
                if (firstName != null) updatedStudent.setFirstName(firstName);
                if (lastName != null) updatedStudent.setLastName(lastName);
                if (DOB != null) updatedStudent.setFirstName(DOB);
                if (midTerm1 != -1) updatedStudent.setMidTerm1(midTerm1);
                if (midTerm2 != -1) updatedStudent.setMidTerm2(midTerm2);
                if (assignment1 != -1) updatedStudent.setAssignment1(assignment1);
                if (assignment2 != -1) updatedStudent.setAssignment2(assignment2);
                if (assignment3 != -1) updatedStudent.setAssignment3(assignment3);
                if (assignment4 != -1) updatedStudent.setAssignment4(assignment4);
                if (assignment5 != -1) updatedStudent.setAssignment5(assignment5);
                updatedStudent.setFinalScore(finalScore);
                updatedStudent.setFinalGrade(finalGrade);

                student = model.updateStudent(updatedStudent);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return student;
    }

    public Student deleteStudent(int studentID) {
        try {
            if (model.isConnected()) {
                students.clear();
                this.student = model.deleteStudent(studentID);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return this.student;
    }

    public LinkedHashMap<String, Double> deliverablesStats() {
        LinkedHashMap<String, Double> stats = new LinkedHashMap<>();
        ArrayList<Student> students = getStudents();
        Double midTerm1 = 0.0;
        Double midTerm2 = 0.0;
        Double assignment1 = 0.0;
        Double assignment2 = 0.0;
        Double assignment3 = 0.0;
        Double assignment4 = 0.0;
        Double assignment5 = 0.0;
        for (int i = 0; i < students.size(); i++) {
            midTerm1 += students.get(i).getMidTerm1();
            midTerm2 += students.get(i).getMidTerm2();
            assignment1 += students.get(i).getAssignment1();
            assignment2 += students.get(i).getAssignment2();
            assignment3 += students.get(i).getAssignment3();
            assignment5 += students.get(i).getAssignment4();
            assignment4 += students.get(i).getAssignment5();
        }
        stats.put("midTerm1", midTerm1);
        stats.put("midTerm2", midTerm2);
        stats.put("assignment1", assignment1);
        stats.put("assignment2", assignment2);
        stats.put("assignment3", assignment3);
        stats.put("assignment4", assignment4);
        stats.put("assignment5", assignment5);
        stats = calculateAverage(stats, students.size());
        return stats;
    }

    public LinkedHashMap<String, Double[]> deliverablesStudentsAverageStats() {
        LinkedHashMap<String, Double[]> stats = new LinkedHashMap<>();
        LinkedHashMap<String, Double> deliverableStats = deliverablesStats();
        ArrayList<Student> studentsMarks = getStudents();
        Double aboveAverage = 0.0;
        Double belowAverage = 0.0;

        for (String key : deliverableStats.keySet()) {
            for (Student student : studentsMarks) {
                switch (key) {
                    case "midTerm1":
                        if (student.getMidTerm1() > deliverableStats.get(key)) aboveAverage++;
                        else belowAverage++;
                        break;
                    case "midTerm2":
                        if (student.getMidTerm2() > deliverableStats.get(key)) aboveAverage++;
                        else belowAverage++;
                        break;
                    case "assignment1":
                        if (student.getAssignment1() > deliverableStats.get(key)) aboveAverage++;
                        else belowAverage++;
                        break;
                    case "assignment2":
                        if (student.getAssignment2() > deliverableStats.get(key)) aboveAverage++;
                        else belowAverage++;
                        break;
                    case "assignment3":
                        if (student.getAssignment3() > deliverableStats.get(key)) aboveAverage++;
                        else belowAverage++;
                        break;
                    case "assignment4":
                        if (student.getAssignment4() > deliverableStats.get(key)) aboveAverage++;
                        else belowAverage++;
                        break;
                    case "assignment5":
                        if (student.getAssignment5() > deliverableStats.get(key)) aboveAverage++;
                        else belowAverage++;
                        break;
                }

            }
            Double[] res = {aboveAverage, belowAverage};
            stats.put(key, res);
            aboveAverage = 0.0;
            belowAverage = 0.0;
        }
        return stats;
    }

    public LinkedHashMap<String, Double> overallFinalScoreStats() {
        LinkedHashMap<String, Double> stats = new LinkedHashMap<>();
        ArrayList<Student> students = getStudents();
        ArrayList<Double> finalScores = new ArrayList<>();
        Double sum = 0.0, average = 0.0, high = 0.0, low = 0.0, passed = 0.0, failed = 0.0;

        for(Student student : students) {
            finalScores.add(student.getFinalScore());
        }

        Collections.sort(finalScores, (a, b) -> Double.compare(b, a));

        for(Double mark : finalScores) {
            sum += mark;
            if(mark > 50) passed++;
            else failed++;
        }
        average = sum / finalScores.size();

        low = finalScores.get(finalScores.size() -1);
        high = finalScores.get(0);

        stats.put("average", average);
        stats.put("high", high);
        stats.put("low", low);
        stats.put("pass", passed);
        stats.put("fail", failed);

        return stats;
    }

    private LinkedHashMap<String, Double> calculateAverage(LinkedHashMap<String, Double> stats, int size) {
        LinkedHashMap<String, Double> averageStats = new LinkedHashMap<>();
        averageStats.put("midTerm1", stats.get("midTerm1") / size);
        averageStats.put("midTerm2", stats.get("midTerm2") / size);
        averageStats.put("assignment1", stats.get("assignment1") / size);
        averageStats.put("assignment2", stats.get("assignment2") / size);
        averageStats.put("assignment3", stats.get("assignment3") / size);
        averageStats.put("assignment4", stats.get("assignment4") / size);
        averageStats.put("assignment5", stats.get("assignment5") / size);
        return averageStats;
    }

    private String generateEmail(String firstName, String lastName, String dob) {
        String date[] = dob.split("-"); // format: 1994-03-11
        String email = firstName + date[1] + lastName + "@algomail.com";
        return email;
    }

}
