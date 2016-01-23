package dao;

import models.Category;
import models.Post;
import models.Subcategory;

import java.util.List;

/**
 * Created by akarthik10 on 12/30/2015.
 */
public interface SubcategoryDAO {
    public Subcategory get(String id);
    public void saveOrUpdate(Subcategory subcategory) throws Exception;
    public void delete(String id);
}