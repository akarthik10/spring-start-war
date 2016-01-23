package config;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import models.User;

/**
 * Created by akarthik10 on 11/12/2015.
 */
 public class SevenElevenConstants {
    public static final String API_SUCCESS_STRING = "ok";
    public static final String API_FAILURE_STRING = "error";

    public static final String MAIL_FROM = "akarthik101@gmail.com";
    public static final String GCM_SERVER_KEY = "AIzaSyAS-aFWeJe7r7X2UqgmN-qbLknKOZBunfw";

    public static final String API_REQUIRED_PARAM_MISSING_ERROR = "API_REQUIRED_PARAM_MISSING";
    public static final String API_REQUIRED_PARAM_MISSING_DESC = "Invalid request. One of the required parameters is missing";
    public static final String API_GENERIC_EXCEPTION_ERROR = "API_UNKNOWN_ERROR";
    public static final String API_GENERIC_EXCEPTION_DESC = "The server was unable to process this request. Please try again.";
    public static final String API_USER_EMAIL_BAD_FORMAT_ERROR = "API_USER_EMAIL_BAD_FORMAT";
    public static final String API_USER_EMAIL_BAD_FORMAT_DESC = "The specified email address is invalid.";

   public static final String API_USER_NAME_BAD_FORMAT_ERROR = "API_USER_NAME_BAD_FORMAT";
   public static final String API_USER_NAME_BAD_FORMAT_DESC = "The specified name is invalid.";

   public static final String API_USER_PHONE_BAD_FORMAT_ERROR = "API_USER_PHONE_BAD_FORMAT";
   public static final String API_USER_PHONE_BAD_FORMAT_DESC = "The specified phone number is invalid.";

   public static final String API_USER_PASS_BAD_FORMAT_ERROR = "API_USER_PASS_BAD_FORMAT";
   public static final String API_USER_PASS_BAD_FORMAT_DESC = "The specified password is invalid.";

    public static final String API_USER_EMAIL_ALREADY_USED_ERROR = "API_USER_EMAIL_ALREADY_USED";
    public static final String API_USER_EMAIL_ALREADY_USED_DESC = "The specified email address is already used.";

    public static final String API_USER_LOGIN_EMAIL_INVALID_ERROR = "API_USER_LOGIN_EMAIL_INVALID";
    public static final String API_USER_LOGIN_EMAIL_INVALID_DESC = "There is no account with this email address";

    public static final String API_USER_LOGIN_BAD_CREDENTIALS_ERROR = "API_USER_LOGIN_BAD_CREDENTIALS";
    public static final String API_USER_LOGIN_BAD_CREDENTIALS_DESC = "The password is wrong";

    public static final String API_USER_LOGIN_BAD_ACTIVATION_CODE_ERROR = "API_USER_LOGIN_BAD_ACTIVATION_CODE";
    public static final String API_USER_LOGIN_BAD_ACTIVATION_CODE_DESC = "Invalid activation code";

    public static final String API_USER_LOGIN_BAD_ACTIVATION_DONE_ERROR = "API_USER_LOGIN_BAD_ACTIVATION_DONE";
    public static final String API_USER_LOGIN_BAD_ACTIVATION_DONE_DESC = "Your account is activated.";


    public static final int USER_ACTIVATION_CODE_LENGTH = 5;
    public static final int POST_EXPIRY_DAYS = 15;


    public static void sendGCM(User user, Message msg) throws Exception{

        Sender sender = new Sender(SevenElevenConstants.GCM_SERVER_KEY);
        Result result = sender.send(msg, user.getGcm_id(), 5);
    }

}
