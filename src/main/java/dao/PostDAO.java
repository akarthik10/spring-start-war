package dao;

import models.Post;
import models.User;

import java.util.List;

public interface PostDAO {
    public Post get(Long id);
    public void saveOrUpdate(Post post) throws Exception;
    public void delete(Long id);
    public List<Post> search(double latitude, double longitude, String query);
    public boolean hasRequestBy(User user, Post post) ;
    public List<Post> getPostByUser(User user) ;
}