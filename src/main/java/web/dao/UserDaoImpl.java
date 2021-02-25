package web.dao;

import org.springframework.stereotype.Repository;
import web.models.Role;
import web.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private Map<String, User> userMap;

    @Override
    public User getUserByName(String name) {
        User user = entityManager.createQuery("select user from User user where user.name = :username", User.class).setParameter("username", name).getSingleResult();
        return user;
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);

    }

    @Override
    public void deleteUser(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        Role role = entityManager.find(Role.class, id);
        entityManager.remove(role);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public List<User> userList() {
        try {
            List<User> users = entityManager.createQuery("select user from User user").getResultList();
            return users;
        }catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public User getUserById(long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public List<Role> roles() {
        return entityManager.createQuery("select role from Role role").getResultList();
    }

    @Override
    public Role getRoleById(Long id) {
        Role role = entityManager.find(Role.class, id);
        return role;
    }

}

