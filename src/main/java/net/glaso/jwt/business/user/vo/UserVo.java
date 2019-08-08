package net.glaso.jwt.business.user.vo;

public class UserVo {

    String id;
    String password;
    String name;
    int authState;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAuthState() {
        return authState;
    }

    public void setAuthState(int authState) {
        this.authState = authState;
    }

    @Override
    public String toString() {
        return "KeyPairVo{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", authState=" + authState +
                '}';
    }
}
