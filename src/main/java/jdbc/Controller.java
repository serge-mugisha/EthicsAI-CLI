package jdbc;
import jdbc.url.JDBCUrl;
import students.Student;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface Controller extends AutoCloseable {

    public boolean loginWith( String username, String password);

    public Controller setURLBuilder( JDBCUrl builder);

	public Controller setCredentials(String user, String pass);

    public Controller setDataBase( String address, String port, String catalog);

    public Controller addConnectionURLProperty( String key, String value);

	public Controller connect();

    public ArrayList<Student> getStudents();

	public Student getStudent( int studentID);

	public Student addStudent(String firstName, String lastName, String DOB, Double midTerm1, Double midTerm2, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double assignment5);

	public Student updateStudent(int studentId, String firstName, String lastName, String DOB, Double midTerm1, Double midTerm2, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double assignment5);

	public Student deleteStudent( int studentID);

	public LinkedHashMap<String, Double> deliverablesStats();

	public LinkedHashMap<String, Double[]> deliverablesStudentsAverageStats();

	public LinkedHashMap<String, Double> overallFinalScoreStats();

}
