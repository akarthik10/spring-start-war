package dao;

import models.Category;
import models.SkillPref;

import java.util.List;

/**
 * Created by akarthik10 on 12/30/2015.
 */
public interface SkillPrefDAO {
    public SkillPref get(String id);
    public List<SkillPref> getAll();
    public void saveOrUpdate(SkillPref skillPref) throws Exception;
    public void delete(String id);
}
