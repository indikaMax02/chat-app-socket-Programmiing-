package server.entity;

public class User {

    private String userName;
    private String password;
    private String fullName;
    private String email;
    private String gender;
    private String phoneNo;

    public User() {
    }

    public User(String userName, String password, String fullName, String email, String gender, String phoneNo) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.phoneNo = phoneNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
