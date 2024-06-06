package Models;

import java.time.LocalDate;

import Controllers.Enum.Department;
import Controllers.Enum.Roles;
import Controllers.Enum.Status;
import Controllers.Services.PersistsService;

public class Applicant extends User {

    private LocalDate interviewDate;
    private String createdBy;
    private String createdAt;
    private String createdBranch;
    private String status;
    private String edu;
    private String department;
    private String workingEx;
    private String other;
    private String secondaryDepartments;
    private String oneTimeToken;

    public Applicant(
            LocalDate interviewDate, String edu) {
        this.interviewDate = interviewDate;
        this.edu = edu;
    }

    public Applicant(
            LocalDate interviewDate, String createdBy, String createdAt, String createdBranch, String status,
            String edu) {
        this.interviewDate = interviewDate;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.createdBranch = createdBranch;
        this.status = status;
        this.edu = edu;
    }

    public Applicant(String firstName, String lastName, String address, String email, int phone, String username,
            String password, LocalDate interviewDate, String edu) {
        super(firstName, lastName, address, email, phone, username, password, Roles.APPLICANT.getValue());
        this.interviewDate = interviewDate;
        this.edu = edu;
    }

    public Applicant(String firstName, String lastName, String address, String email, int phone, String username,
            String password, LocalDate interviewDate, String createdBy, String createdAt, String createdBranch,
            String status) {
        super(firstName, lastName, address, email, phone, username, password, Roles.APPLICANT.getValue());
        this.interviewDate = interviewDate;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.createdBranch = createdBranch;
        this.status = status;
    }

    public String getSecondaryDepartments() {
        return secondaryDepartments;
    }

    public void setSecondaryDepartments(String secondaryDepartments) {
        this.secondaryDepartments = secondaryDepartments;
    }

    public String getWorkingEx() {
        return workingEx;
    }

    public void setWorkingEx(String workingEx) {
        this.workingEx = workingEx;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public LocalDate getInterviewDate() {
        return interviewDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedBranch() {
        return createdBranch;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getEdu() {
        return edu;
    }

    public void setEdu(String edu) {
        this.edu = edu;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setInterviewDate(LocalDate interviewDate) {
        this.interviewDate = interviewDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setCreatedBranch(String createdBranch) {
        this.createdBranch = createdBranch;
    }

    public Boolean isApproved() {
        return (status).trim().equals((Status.APPROVED.getValue()).trim());
    }

    public String getOneTimeToken() {
        return oneTimeToken;
    }

    public void setOneTimeToken(String oneTimeToken) {
        if (oneTimeToken != null) {
            this.oneTimeToken = oneTimeToken.trim();
        }
    }

    public static String stats() {
        int swd = 0;
        int ars = 0;
        int elc = 0;
        int mec = 0;

        for (User user : PersistsService.get().applicantsData()) {
            String dept = ((Applicant) user).getDepartment();
            if (Department.SWD.getValue().equals(dept)) {
                swd++;
            } else if (Department.ARS.getValue().equals(dept)) {
                ars++;
            } else if (Department.MEC.getValue().equals(dept)) {
                mec++;
            } else if (Department.ELEC.getValue().equals(dept)) {
                elc++;
            }

        }

        return "Assign to " + Department.SWD.getValue() + ": " + swd + "\n"
                + "Assign to " + Department.ARS.getValue() + ": " + ars + "\n"
                + "Assign to " + Department.ELEC.getValue() + ": " + elc + "\n"
                + "Assign to " + Department.MEC.getValue() + ": " + mec + "\n";
    }

    @Override
    public String getCSV() {
        return super.getCSV()
                + ", " + getDepartment()
                + ", " + getEdu()
                + ", " + getInterviewDate()
                + ", " + getCreatedBranch()
                + ", " + getCreatedBy()
                + ", " + getCreatedAt()
                + ", " + getStatus()
                + ", " + getWorkingEx()
                + ", " + getOther()
                + ", " + getSecondaryDepartments()
                + ", " + getOneTimeToken();
    }

}
