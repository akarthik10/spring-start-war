package restapicontroller;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import config.MailConfig;
import config.SevenElevenConstants;
import dao.*;
import models.*;
import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;


/**
 * Created by akarthik10 on 11/12/2015.
 */
@RestController
@RequestMapping("/api/v1")
public class RestApiController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PostDAO postDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private SubcategoryDAO subcategoryDAO;

    @Autowired
    private ProfileDAO profileDAO;

    @Autowired
    private SkillPrefDAO skillPrefDAO;

    @Autowired
    private PostRequestDAO postRequestDAO;


    @RequestMapping(value = "/register")
    public Response register(@RequestParam(value="first_name") String first_name,
                             @RequestParam(value="last_name") String last_name,
                             @RequestParam(value="email") String email,
                             @RequestParam(value="phone") String phone,
                             @RequestParam(value="password") String password) throws Exception{
        //Do registration
        List<User> userList;

        if(email.isEmpty() ) {
            return new ExceptionResponse(SevenElevenConstants.API_USER_EMAIL_BAD_FORMAT_ERROR,
                                         SevenElevenConstants.API_USER_EMAIL_BAD_FORMAT_DESC);
        }

        if(first_name.trim().length() < 3 || last_name.trim().length() < 3) {
            return new ExceptionResponse(SevenElevenConstants.API_USER_NAME_BAD_FORMAT_ERROR,
                                         SevenElevenConstants.API_USER_NAME_BAD_FORMAT_DESC);
        }


        userList = userDAO.getUserByEmail(email);
        if(userList.size() > 0) {
            return new ExceptionResponse(SevenElevenConstants.API_USER_EMAIL_ALREADY_USED_ERROR,
                                         SevenElevenConstants.API_USER_EMAIL_ALREADY_USED_DESC);
        }

        User user = new User(first_name, last_name, email, phone, password);
        userDAO.saveOrUpdate(user);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(MailConfig.class);
        ctx.refresh();
        JavaMailSenderImpl mailSender = ctx.getBean(JavaMailSenderImpl.class);
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
        mailMsg.setFrom(SevenElevenConstants.MAIL_FROM);
        mailMsg.setTo(email);
        mailMsg.setSubject("Activation code");
        mailMsg.setText("Your activation code is "+ user.getActivationCode());
        mailSender.send(mimeMessage);


        return new Response(user);
    }

    @RequestMapping(value = "/login")
    public Response login(@RequestParam(value = "email") String email,
                          @RequestParam(value = "password") String password) {
        List<User> userList;
        userList = userDAO.getUserByEmail(email.trim());
        if(userList.size() == 0) {
            return new ExceptionResponse(SevenElevenConstants.API_USER_LOGIN_EMAIL_INVALID_ERROR,
                                         SevenElevenConstants.API_USER_LOGIN_EMAIL_INVALID_DESC);
        }
        User user = userList.get(0);
        if(!user.getPassword().contentEquals(password.trim())) {
            return new ExceptionResponse(SevenElevenConstants.API_USER_LOGIN_BAD_CREDENTIALS_ERROR,
                                         SevenElevenConstants.API_USER_LOGIN_BAD_CREDENTIALS_DESC);
        }
        return new Response(user);

    }

    @RequestMapping(value = "/activate")
    public Response activate(@RequestParam(value = "email") String email,
                             @RequestParam(value = "activation_code") String activationCode) throws Exception{
        List<User> userList;
        userList = userDAO.getUserByEmail(email.trim());
        if(userList.size() == 0) {
            return new ExceptionResponse(SevenElevenConstants.API_USER_LOGIN_EMAIL_INVALID_ERROR,
                    SevenElevenConstants.API_USER_LOGIN_EMAIL_INVALID_DESC);
        }
        User user = userList.get(0);
        if(user.isActivated()) {
            return new ExceptionResponse(SevenElevenConstants.API_USER_LOGIN_BAD_ACTIVATION_DONE_ERROR,
                    SevenElevenConstants.API_USER_LOGIN_BAD_ACTIVATION_DONE_DESC);
        }

        if(!user.getActivationCode().contentEquals(activationCode.trim())) {
            return new ExceptionResponse(SevenElevenConstants.API_USER_LOGIN_BAD_ACTIVATION_CODE_ERROR,
                    SevenElevenConstants.API_USER_LOGIN_BAD_ACTIVATION_CODE_DESC);
        }
        user.setActivated(true);
        userDAO.saveOrUpdate(user);
        return new Response(user);

    }

    @RequestMapping(value = "/newpost")
    public Response newpost(@RequestParam(value = "user") Long user_id,
                            @RequestParam(value = "title") String title,
                            @RequestParam(value = "description") String description,
                            @RequestParam(value = "latitude") Double latitude,
                            @RequestParam(value = "longitude") Double longitude,
                            @RequestParam(value = "category") String category,
                            @RequestParam(value = "subcategory") String subcategory,
                            @RequestParam(value = "rate") String rate) throws Exception
    {
        User user = userDAO.get(user_id);

        Calendar c = Calendar.getInstance();
        Date expiry = new Date();
        c.setTime(expiry);
        c.add(Calendar.DATE, SevenElevenConstants.POST_EXPIRY_DAYS);
        expiry = c.getTime();

        Category cat = categoryDAO.get(category);
        Subcategory sub = subcategoryDAO.get(subcategory);


        Post post = new Post(title, description, latitude, longitude, new Date(), expiry, user, cat, sub, rate, false);
        postDAO.saveOrUpdate(post);
        return new Response(post);
    }

    @RequestMapping(value = "/search")
    public Response search(@RequestParam(value = "user") Long user_id,
                            @RequestParam(value = "query") String query,
                            @RequestParam(value = "latitude") Double latitude,
                            @RequestParam(value = "longitude") Double longitude) throws Exception
    {

        List<Post> result = postDAO.search(latitude, longitude, "%"+query+"%");
       /* for(int i=0; i<result.size(); i++) {
            result.get(i).getCategory().setSubcategories(null);
        }*/
        return new Response(result);
    }

    @RequestMapping(value = "/populate_categories")
    public Response populateCategories()
    {
        Writer writer;
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            InputStream is =
                    new FileInputStream( classLoader.getResource("/categories.json").getFile());
            writer = new StringWriter();
            char[] buffer = new char[1024];
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();

        } catch (Exception e) {
            return new ExceptionResponse(e.getMessage());
        }


        String jsonString = writer.toString();


        try {
            JSONObject CategoryTypes = new JSONObject(jsonString);
            JSONArray Categories = CategoryTypes.getJSONArray("types");
            for(int i=0; i<Categories.length(); i++) {
                JSONObject category = Categories.getJSONObject(i);
                Category c = new Category( );
                c.setName(category.getString("name"));
                c.setKey(category.getString("id"));
                Set<Subcategory> sc = new HashSet<Subcategory>();
                categoryDAO.saveOrUpdate(c);
                JSONArray subcategories = category.getJSONArray("subtypes");
                for(int j=0; j<subcategories.length(); j++)
                {
                    JSONObject subcategory = subcategories.getJSONObject(j);
                    Subcategory sco = new Subcategory(subcategory.getString("name"), subcategory.getString("id"), c);
                    sc.add(sco);
                    subcategoryDAO.saveOrUpdate(sco);
                }
                c.setSubcategories(sc);
                categoryDAO.saveOrUpdate(c);
            }




        } catch (Exception e) {
            return new ExceptionResponse(e.getMessage());
        }
        return new Response(categoryDAO.getAll());

    }

    @RequestMapping(value = "/get_categories")
    public Response getCategories() throws Exception
    {

        return new Response(categoryDAO.getAll());
    }

    @RequestMapping(value = "/saveprofile")
    public Response newpost(@RequestParam(value = "user") Long user_id,
                            @RequestParam(value = "profile") String jsonProfile) throws Exception
    {
        User user = userDAO.get(user_id);
        Profile profile = user.getProfile();
        if(profile != null) {
            profile.getSkillPrefs().clear();
        } else {
            profile = new Profile();
        }
        String location;
        Set<SkillPref> skillPrefs = profile.getSkillPrefs();

        if(skillPrefs == null) {
            skillPrefs = new HashSet<SkillPref>();
        }

        try {
            JSONObject joprofile = new JSONObject(jsonProfile);
            location = joprofile.getString("location");
            JSONArray skills = joprofile.getJSONArray("skills");

            //profile.setUser(user);
            profile.setLocation(location);

            profileDAO.saveOrUpdate(profile);

            for(int i=0; i<skills.length(); i++) {
                JSONObject skill = skills.getJSONObject(i);
                SkillPref s = new SkillPref();
                Category c = categoryDAO.get(skill.getString("category"));
                s.setCategory(c);
                Subcategory sc = subcategoryDAO.get(skill.getString("subcategory"));
                s.setSubcategory(sc);
                s.setStartTime(skill.getString("startTime"));
                s.setEndTime(skill.getString("endTime"));
                skillPrefs.add(s);
                skillPrefDAO.saveOrUpdate(s);
            }
            profile.setSkillPrefs(skillPrefs);
            profileDAO.saveOrUpdate(profile);
            user.setProfile(profile);
            userDAO.saveOrUpdate(user);
            return new Response(profile);

        } catch (Exception e) {

            return new ExceptionResponse("An error occured saving data", e.toString());
        }




    }

    @RequestMapping(value = "/get_profile")
    public Response search(@RequestParam(value = "user") Long user_id) throws Exception
    {

        User user = userDAO.get(user_id);
        if(user == null) {
            return new Response(null);
        }
        return new Response(user.getProfile());
    }


    @RequestMapping(value = "/get_post")
    public Response get_post(@RequestParam(value = "post_id")Long  post_id,
                             @RequestParam(value = "user") Long user_id) throws Exception
    {

        User user = userDAO.get(user_id);
        if(user == null) {
            return new Response(null);
        }
        Post post = postDAO.get(post_id);
//        post.getUser().setPhone(null);
//        post.getUser().setEmail(null);
//        post.getUser().setProfile(null);
//        post.getCategory().setSubcategories(null);

        return new Response(post);
    }

    @RequestMapping(value = "/post_request")
    public Response post_request(@RequestParam(value = "user") Long user_id, @RequestParam(value = "post_id") Long post_id) throws Exception
    {

        User user = userDAO.get(user_id);
        Post p = postDAO.get(post_id);

        PostRequest pr = new PostRequest();
        pr.setByUser(user);
        pr.setWhen(new Date());
        postRequestDAO.saveOrUpdate(pr);

        p.getPostRequests().add(pr);
        postDAO.saveOrUpdate(p);

        User postOwner = p.getUser();
        Message msg = new Message.Builder()
                .addData("type", "POST_REQUEST_TO_POSTER")
                .addData("notification_title", "New request")
                .addData("notification_long_title", "You have a new request")
                .addData("notification_long", postOwner.getFirstName()+", your job post '"+p.getTitle()+"' has got a request from "+ user.getFirstName() + " "+ user.getLastName()+". Click here to approve or reject the request.")
                .addData("notification_short", "Your job post '"+p.getTitle()+"' has a new request")
                .addData("user_id", postOwner.getId()+"")
                .addData("post_id", p.getId()+"")
                .addData("action", "open_request_approve")
                .build();
        SevenElevenConstants.sendGCM(postOwner, msg);

//        pr.getByUser().setProfile(null);
//        pr.getByUser().setPhone(null);
//        pr.getByUser().setEmail(null);
//
//        p.getPostRequests().clear();
//        p.getPostRequests().add(pr);
//        p.getCategory().setSubcategories(null);
//        p.getUser().setProfile(null);
//        p.setAcceptedUser(null);

        return new Response(p);
    }


    @RequestMapping(value = "/has_posrtequest")
    public Response has_post_request(@RequestParam(value = "user") Long user_id, @RequestParam(value = "post_id") Long post_id) throws Exception
    {

        User user = userDAO.get(user_id);
        Post p = postDAO.get(post_id);






        return new Response(postDAO.hasRequestBy(user,p));
    }

    @RequestMapping(value = "/register_gcm")
    public Response register_gcm(@RequestParam(value = "user") Long user,
                          @RequestParam(value = "id") String id) {
        User u = userDAO.get(user);
        u.setGcm_id(id);
        try {
            userDAO.saveOrUpdate(u);



        } catch (Exception e) {
            return new ExceptionResponse(e.toString());
        }
        return new Response(user);


    }

    @RequestMapping(value = "/getpostbyuser")
    public Response getpostbyuser(@RequestParam(value = "user") Long user_id) throws Exception
    {

        User user = userDAO.get(user_id);
        List<Post> p = postDAO.getPostByUser(user);



        return new Response(p);
    }

    @RequestMapping(value = "/getpostrequests")
    public Response getpostrequests(@RequestParam(value = "post_id") Long post_id) throws Exception
    {

        Post p = postDAO.get(post_id);



        return new Response(p.getPostRequests());
    }

    @RequestMapping(value = "/accept_request_post")
    public Response accept_request_post(@RequestParam(value = "post_id") Long post_id, @RequestParam(value = "accept_user_id") Long accept_user_id) throws Exception
    {

        Post p = postDAO.get(post_id);
        User u = userDAO.get(accept_user_id);

        p.setAcceptedUser(u);
        p.setRequest_accepted(true);
        postDAO.saveOrUpdate(p);

        Message msg = new Message.Builder()
                .addData("type", "POST_REQUEST_ACCEPT")
                .addData("notification_title", "Request accepted")
                .addData("notification_long_title", "Your request has been accepted")
                .addData("notification_long", u.getFirstName()+", your request for job '"+p.getTitle()+"' has been accepted. Click here to contact the job poster.")
                .addData("notification_short", "Your job post '"+p.getTitle()+"' has a new request")
                .addData("user_id", u.getId()+"")
                .addData("post_id", p.getId()+"")
                .addData("action", "request_approve")
                .build();
        SevenElevenConstants.sendGCM(u, msg);

        return new Response(p);
    }


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ExceptionResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest request) {
        return new ExceptionResponse(SevenElevenConstants.API_REQUIRED_PARAM_MISSING_ERROR,
                                     SevenElevenConstants.API_REQUIRED_PARAM_MISSING_DESC,
                                     ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleException(Exception ex, HttpServletRequest request) {
        ex.printStackTrace();
        return new ExceptionResponse(SevenElevenConstants.API_GENERIC_EXCEPTION_ERROR,
                                     SevenElevenConstants.API_GENERIC_EXCEPTION_DESC,
                                     ex.getMessage());
    }

}
