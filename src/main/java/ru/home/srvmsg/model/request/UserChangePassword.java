package ru.home.srvmsg.model.request;

public class UserChangePassword {

    private Integer user_id;

    private String cur_password;

    private String new_password;

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getCur_password() {
        return cur_password;
    }

    public void setCur_password(String cur_password) {
        this.cur_password = cur_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

}
