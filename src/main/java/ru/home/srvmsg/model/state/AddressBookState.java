package ru.home.srvmsg.model.state;

public class AddressBookState {

    private Integer id;
    private String message;
    private Byte error;

    private Integer user_id;
    private String fio;
    private String login;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
