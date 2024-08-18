package api;

public class SuccessRegister extends Register {
    private Integer id;
    private String token;

    public SuccessRegister() {

    }

    public SuccessRegister(Integer id, String token) {
        super();
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
