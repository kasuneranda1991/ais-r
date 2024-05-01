package Models;

import java.time.LocalDate;

import Enum.Roles;

public class Applicant extends User {
    private LocalDate interviewDate;

    public Applicant(String firstName, String lastName, String address, String email, int phone, String username,
            String password, LocalDate interviewDate) {
        super(firstName, lastName, address, email, phone, username, password, Roles.APPLICANT.getValue());
        this.interviewDate = interviewDate;
    }

    public Applicant(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    @Override
    public String getCSV() {
        return super.getCSV() + ", " + getInterviewDate();
    }

}
