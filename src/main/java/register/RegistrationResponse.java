package register;

/**
 * Created by akarthik10 on 11/12/2015.
 */
public class RegistrationResponse {
    String status;

    public RegistrationResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
