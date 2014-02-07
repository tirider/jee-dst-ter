package com.container.models.dao;

import java.util.List;
import com.container.models.beans.User;

public interface UserDAO
{
	public User findById(String id);

	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	public boolean existsUsername(String username);
	
	public boolean existsEmail(String email);
	
	public boolean exists(String username, String password);	
	
	public List<User> findAll();
	
	public boolean create(User user);
	
	public boolean delete(String id);
	
	public boolean updatePasswordById(String uid, String newpassword);
	
	public boolean updatePasswordByEmail(String email, String newpassword);
}
