package com.container.models.dao;

import java.util.List;
import com.container.models.beans.Group;

public interface GroupDAO 
{
	public boolean find(String id);
	
	public List<Group> findAll();
	
	public boolean create(Group group);
	
	public boolean delete(String id);
	
	public boolean updateDescription(String id, String newdescription);
}
