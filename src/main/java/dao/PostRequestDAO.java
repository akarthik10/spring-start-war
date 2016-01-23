package dao;

import models.Post;
import models.PostRequest;
import models.User;

import java.util.List;

public interface PostRequestDAO {
    public PostRequest get(Long id);
    public void saveOrUpdate(PostRequest postRequest) throws Exception;
    public void delete(Long id);
    public List<PostRequest> getByUser(User user);
}