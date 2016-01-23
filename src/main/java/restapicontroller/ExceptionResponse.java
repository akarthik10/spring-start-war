package restapicontroller;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by akarthik10 on 11/12/2015.
 */
public class ExceptionResponse extends Response{

    boolean status = false;
    public String error = "NONE";
    public String description = "NONE";
    private String internalerror = "NONE";

    @JsonIgnore
    String data = "";


    public ExceptionResponse(String error, String description, String internalerror) {
        this.error = error;
        this.description = description;
        this.internalerror = internalerror;
    }

    public String getInternalerror() {

        return internalerror;
    }

    public void setInternalerror(String internalerror) {
        this.internalerror = internalerror;
    }

    public ExceptionResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExceptionResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
