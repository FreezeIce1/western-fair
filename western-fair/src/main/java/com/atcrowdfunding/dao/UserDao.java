package com.atcrowdfunding.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.atcrowdfunding.bean.User;

public interface UserDao {

	@Select("select * from t_user")
	List<User> queryAll();

	@Select("select * from t_user where loginacct = #{loginacct}  and userpswd = #{userpswd}")
	User query4Login(User user);

	List<User> pageQueryData(Map<String, Object> map);

	int pageQueryCount(Map<String, Object> map);

	void insertUser(User user);

	@Select("select * from t_user where id = #{id}")
	User queryById(Integer id);

	void updateUser(User user);

	void deleteUserById(Integer id);

	void deleteUsers(Map<String, Object> map);

	void insertUserRoles(Map<String, Object> map);

	void deleteUsersRoles(Map<String, Object> map);

	List<Integer> queryAssignRoleIdsByUserId(Integer id);

	//修改用户信息
	void updateUserOwnInfo(User user);

	//修改用户登录信息
	void updateUserLoginInfo(User user);

	User queryUserByLoginacct(String loginacct);

}
