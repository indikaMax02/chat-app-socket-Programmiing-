package server.dto;

public class UserDetailsDTO {
    private String userName;

    public UserDetailsDTO() {
    }

    public UserDetailsDTO(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
