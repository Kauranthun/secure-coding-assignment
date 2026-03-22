package hawk.service;

import hawk.entity.User;
import hawk.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    public User findUser(String name) {
        return this.userRepo.findByName(name);
    }

    public List<User> findUsersByName(String  name) { return this.userRepo.findAllByNameIsLike('%' + name + '%'); }
}
