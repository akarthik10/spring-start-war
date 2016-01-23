package restapicontroller;

/**
 * Created by akarthik10 on 11/12/2015.
 */
public class Response<T> {

    boolean status = true;
    T data;

    public Response() {}
    public Response(T d) { data = d; }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}