package ru.home.srvmsg.model.state;

public class UserState {
    private Integer id;
    private String message;
    private Byte error;

    public UserState() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte getError() {
        return error;
    }

    public void setError(Byte error) {
        this.error = error;
    }
}
