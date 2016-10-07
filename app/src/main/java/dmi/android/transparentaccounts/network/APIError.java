package dmi.android.transparentaccounts.network;

/**
 * Created by DZCDMAY on 15.9.2016.
 */
public class APIError {

    private String message;
    private int code;

    public APIError(String s, int c) {
        message = s;
        code = c;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
