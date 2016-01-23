package dao;

import models.User;

import java.util.List;

public interface UserDAO {
    public User get(Long id);
    public void saveOrUpdate(User user) throws Exception;
    public void delete(Long id);
    public List getUserByEmail(String email);
}