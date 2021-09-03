package services;

import com.gallery.controllers.exception.InvalidCredentials;
import com.gallery.dao.UserDao;
import com.gallery.query.Input;

public class RegistrationService {


    private RegistrationService() {
    }

    public static RegistrationService getInstance() {
        return new RegistrationService();
    }

    public void registerUser(String username, String password, String password2, String role) {
        if (password.equals(password2)) {
            Input input = new Input();
            input.fields.put("role", role);
            input.fields.put("username", username);
            input.fields.put("password", password);
            UserDao userDao = UserDao.getInstance();
            userDao.create(input);
        } else {
            throw new InvalidCredentials();
        }
    }

}
