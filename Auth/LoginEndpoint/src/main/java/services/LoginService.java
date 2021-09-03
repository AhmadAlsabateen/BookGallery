package services;

import com.gallery.controllers.exception.InvalidCredentials;
import com.gallery.dao.UserDao;
import com.gallery.entities.User;
import com.gallery.query.Filter;

import java.util.ArrayList;

public class LoginService {

    private LoginService(){}
    public static LoginService getInstance(){
        return new LoginService();
    }
    public String getRole(String username,String password){
        Filter filter = new Filter();
        UserDao userDao= UserDao.getInstance();
        filter.fields.put("username",username);
        System.out.println(username);
        filter.fields.put("password",password);
        User user = userDao.get(filter);
        if (user ==null){
            throw new InvalidCredentials();
        }

       return user.getRole();
    }
}
