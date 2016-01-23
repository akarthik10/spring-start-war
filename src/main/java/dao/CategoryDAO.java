package dao;

import models.Category;
import models.Post;

import java.util.List;

/**
 * Created by akarthik10 on 12/30/2015.
 */
public interface CategoryDAO {
    public Category get(String id);
    public List<Category> getAll();
    public void saveOrUpdate(Category category) throws Exception;
    public void delete(String id);
}
