package api;

public class UnSuccessRegister extends Register{
    private String error;

    public UnSuccessRegister() {

    }

    public UnSuccessRegister(String error) {
        super();
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
