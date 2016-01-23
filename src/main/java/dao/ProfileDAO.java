package dao;

import models.Category;
import models.Profile;

import java.util.List;

/**
 * Created by akarthik10 on 12/30/2015.
 */
public interface ProfileDAO {
    public Profile get(String id);
    public List<Profile> getAll();
    public void saveOrUpdate(Profile profile) throws Exception;
    public void delete(String id);
}
