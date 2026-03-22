package hawk.service;

import hawk.entity.User;
import hawk.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public class UserService {

    public EntityManager entityManager;
    private UserRepo userRepo;

    @Autowired
    public UserService(EntityManager entityManager, UserRepo userRepo){
        this.entityManager=entityManager;
        this.userRepo=userRepo;
    }

    public User findUser(String name) {
        return this.userRepo.findByName(name);
    }

    public List<User> findUsersByName(String  name) { return this.userRepo.findAllByNameIsLike('%' + name + '%'); }
}
