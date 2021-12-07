package students;

import java.util.Date;

public class Student {
    int studentId;
    String firstName;
    String lastName;
    Date DOB;
    String emailAddress;
    Double midTerm1;
    Double midTerm2;
    Double assignment1;
    Double assignment2;
    Double assignment3;
    Double assignment4;
    Double assignment5;
    Double finalScore;
    String finalGrade;

    public Student() {
        clear();
    }
    public Student(int studentId, String firstName, String lastName, Date DOB, String emailAddress, Double midTerm1, Double midTerm2, Double assignment1, Double assignment2, Double assignment3, Double assignment4, Double assignment5, Double finalScore, String finalGrade) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.emailAddress = emailAddress;
        this.midTerm1 = midTerm1;
        this.midTerm2 = midTerm2;
        this.assignment1 = assignment1;
        this.assignment2 = assignment2;
        this.assignment3 = assignment3;
        this.assignment4 = assignment4;
        this.assignment5 = assignment5;
        this.finalScore = finalScore;
        this.finalGrade = finalGrade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Double getMidTerm1() {
        return midTerm1;
    }

    public void setMidTerm1(Double midTerm1) {
        this.midTerm1 = midTerm1;
    }

    public Double getMidTerm2() {
        return midTerm2;
    }

    public void setMidTerm2(Double midTerm2) {
        this.midTerm2 = midTerm2;
    }

    public Double getAssignment1() {
        return assignment1;
    }

    public void setAssignment1(Double assignment1) {
        this.assignment1 = assignment1;
    }

    public Double getAssignment2() {
        return assignment2;
    }

    public void setAssignment2(Double assignment2) {
        this.assignment2 = assignment2;
    }

    public Double getAssignment3() {
        return assignment3;
    }

    public void setAssignment3(Double assignment3) {
        this.assignment3 = assignment3;
    }

    public Double getAssignment4() {
        return assignment4;
    }

    public void setAssignment4(Double assignment4) {
        this.assignment4 = assignment4;
    }

    public Double getAssignment5() {
        return assignment5;
    }

    public void setAssignment5(Double assignment5) {
        this.assignment5 = assignment5;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public void clear() {
        int studentId = 0;
        String firstName = null;
        String lastName = null;
        Date DOB = null;
        String emailAddress = null;
        Double midTerm1 = null;
        Double midTerm2 = null;
        Double assignment1 = null;
        Double assignment2 = null;
        Double assignment3 = null;
        Double assignment4 = null;
        Double assignment5 = null;
        Double finalScore = null;
        String finalGrade = null;
    }
}
