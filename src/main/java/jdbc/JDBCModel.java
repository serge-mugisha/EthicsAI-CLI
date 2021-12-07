package jdbc;

import students.Student;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class JDBCModel {

    private static final String QUERY_STUDENTS_SELECT = "SELECT * FROM students";
    private static final String QUERY_STUDENT_SELECT = "SELECT * FROM students where studentId=?";
    private static final String QUERY_STUDENT_DELETE = "DELETE FROM students where studentId=?";
    private static final String QUERY_ADD_STUDENT = "INSERT INTO students VALUES(DEFAULT,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String QUERY_VALIDATE = "SELECT AccountId FROM security where security.username=? and security.password=?";
    private static final String QUERY_STUDENT_UPDATE = "UPDATE students SET " +
            "firstName=?, " +
            "lastName=?, " +
            "DOB=?, " +
            "emailAddress=?, " +
            "midTerm1=?, " +
            "midTerm2=?, " +
            "assignment1=?, " +
            "assignment2=?, " +
            "assignment3=?, " +
            "assignment4=?, " +
            "assignment5=?, " +
            "finalScore=?, " +
            "finalGrade=? where studentId=?";

    private Connection connection;
    private String user;
    private String pass;
    private int studentId;
    private String firstName;
    private String lastName;
    private Date DOB;
    private String emailAddress;
    private Double midTerm1;
    private Double midTerm2;
    private Double assignment1;
    private Double assignment2;
    private Double assignment3;
    private Double assignment4;
    private Double assignment5;
    private Double finalScore;
    private String finalGrade;

    private Student student = new Student();

    public void setCredentials(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public void connectTo(String url) throws SQLException {
        if (isConnected()) {
            close();
        }
        connection = DriverManager.getConnection(url, user, pass);
    }

    public boolean isConnected() throws SQLException {
        return !(connection == null || connection.isClosed() || !connection.isValid(60));
    }

    private void hasValidConnection() throws SQLException {
        if (!isConnected())
            throw new SQLException("No connection to DB");
    }

    public int loginWith(String username, String password) throws SQLException {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");

        hasValidConnection();
        try (PreparedStatement ps = connection.prepareStatement(QUERY_VALIDATE)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    public ArrayList<Student> getStudents() throws SQLException {
        hasValidConnection();
        ArrayList<Student> students = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement(QUERY_STUDENTS_SELECT)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    studentId = rs.getInt(1);
                    firstName = rs.getString(2);
                    lastName = rs.getString(3);
                    DOB = rs.getDate(4);
                    emailAddress = rs.getString(5);
                    midTerm1 = rs.getDouble(6);
                    midTerm2 = rs.getDouble(7);
                    assignment1 = rs.getDouble(8);
                    assignment2 = rs.getDouble(9);
                    assignment3 = rs.getDouble(10);
                    assignment4 = rs.getDouble(11);
                    assignment5 = rs.getDouble(12);
                    finalScore = rs.getDouble(13);
                    finalGrade = rs.getString(14);
                    Student student = new Student(studentId, firstName, lastName, DOB, emailAddress, midTerm1, midTerm2, assignment1, assignment2, assignment3, assignment4, assignment5, finalScore, finalGrade);

                    students.add(student);
                }
            }
        }
        return students;
    }

    public Student getStudent(int studentId) throws SQLException {
        hasValidConnection();
        try (PreparedStatement ps = connection.prepareStatement(QUERY_STUDENT_SELECT)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("No data found.");
                    student.clear();
                } else {
                    while (rs.next()) {
                        firstName = rs.getString(2);
                        lastName = rs.getString(3);
                        DOB = rs.getDate(4);
                        emailAddress = rs.getString(5);
                        midTerm1 = rs.getDouble(6);
                        midTerm2 = rs.getDouble(7);
                        assignment1 = rs.getDouble(8);
                        assignment2 = rs.getDouble(9);
                        assignment3 = rs.getDouble(10);
                        assignment4 = rs.getDouble(11);
                        assignment5 = rs.getDouble(12);
                        finalScore = rs.getDouble(13);
                        finalGrade = rs.getString(14);
                        student = new Student(studentId, firstName, lastName, DOB, emailAddress, midTerm1, midTerm2, assignment1, assignment2, assignment3, assignment4, assignment5, finalScore, finalGrade);
                    }
                }

            }
        }
        return student;
    }

    public Student addStudent(Student newStudent) throws SQLException {
        hasValidConnection();
        Student addedStudent = null;
        try (PreparedStatement ps = connection.prepareStatement(QUERY_ADD_STUDENT, Statement.RETURN_GENERATED_KEYS)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date DOB = new java.sql.Date(newStudent.getDOB().getTime());
            ps.setString(1, newStudent.getFirstName());
            ps.setString(2, newStudent.getLastName());
            ps.setDate(3, DOB);
            ps.setString(4, newStudent.getEmailAddress());
            ps.setDouble(5, newStudent.getMidTerm1());
            ps.setDouble(6, newStudent.getMidTerm2());
            ps.setDouble(7, newStudent.getAssignment1());
            ps.setDouble(8, newStudent.getAssignment2());
            ps.setDouble(9, newStudent.getAssignment3());
            ps.setDouble(10, newStudent.getAssignment4());
            ps.setDouble(11, newStudent.getAssignment5());
            ps.setDouble(12, newStudent.getFinalScore());
            ps.setString(13, newStudent.getFinalGrade());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) addedStudent = getStudent(rs.getInt(1));

        }
        return addedStudent;
    }

    public Student updateStudent(Student updatedStudent) throws SQLException {
        hasValidConnection();
        try (PreparedStatement ps = connection.prepareStatement(QUERY_STUDENT_UPDATE, Statement.RETURN_GENERATED_KEYS)) {
            java.sql.Date DOB = new java.sql.Date(updatedStudent.getDOB().getTime());

            ps.setString(1, updatedStudent.getFirstName());
            ps.setString(2, updatedStudent.getLastName());
            ps.setDate(3, DOB);
            ps.setString(4, updatedStudent.getEmailAddress());
            ps.setDouble(5, updatedStudent.getMidTerm1());
            ps.setDouble(6, updatedStudent.getMidTerm2());
            ps.setDouble(7, updatedStudent.getAssignment1());
            ps.setDouble(8, updatedStudent.getAssignment2());
            ps.setDouble(9, updatedStudent.getAssignment3());
            ps.setDouble(10, updatedStudent.getAssignment4());
            ps.setDouble(11, updatedStudent.getAssignment5());
            ps.setDouble(12, updatedStudent.getFinalScore());
            ps.setString(13, updatedStudent.getFinalGrade());
            ps.setInt(14, updatedStudent.getStudentId());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) getStudent(rs.getInt(1));

        }
        return student;
    }

    public Student deleteStudent(int studentId) throws SQLException {
        hasValidConnection();
        Student deletedStudent = getStudent(studentId);
        if (deletedStudent.getStudentId() != 0) {
            try (PreparedStatement ps = connection.prepareStatement(QUERY_STUDENT_DELETE)) {
                ps.setInt(1, studentId);
                ps.executeUpdate();
            }
        }
        return deletedStudent;
    }

    public void close() throws SQLException {
        if (connection != null)
            connection.close();
    }

}
