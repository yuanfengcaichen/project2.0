package club.codeqi.project.Service;

import club.codeqi.project.pojo.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:51
 */
public interface UserService extends UserDetailsService {
    public int insert(User user);
    public int update(User user);
    public int resetpass(Integer uid);
    public ArrayList<User> selectAll();
    public User selectByid(Integer id);
    public int delete(int id);

    public ArrayList<User> select_roleAll();

    public User findByName(String name);
}
