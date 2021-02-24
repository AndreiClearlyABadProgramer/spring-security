package web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.models.Role;
import web.models.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public void addUser(User user);
    public void deleteUser(long id);
    public void updateUser(User user);
    public List<User> userList();
    public User getUserById(long id);
    public List<Role> roles();
    public Role getRoleById(Long id);
}
