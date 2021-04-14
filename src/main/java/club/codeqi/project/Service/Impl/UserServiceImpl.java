package club.codeqi.project.Service.Impl;

import club.codeqi.project.Mapper.UserMapper;
import club.codeqi.project.Service.UserService;
import club.codeqi.project.pojo.Permission;
import club.codeqi.project.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:53
 */
@Service
public class UserServiceImpl implements UserService {

    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userMapper.findByName(s);
    }

    @Override
    public int insert(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userMapper.insert(user);
    }

    @Override
    public int update(User user) {
        User olduser = userMapper.selectByid(user.getId());
        olduser.setUsername(user.getUsername());
        olduser.setPhone(user.getPhone());
        olduser.setRoleId(user.getRoleId());
        return userMapper.update(olduser);
    }

    @Override
    public int resetpass(Integer uid) {
        User olduser = userMapper.selectByid(uid);
        olduser.setPassword(bCryptPasswordEncoder.encode("123456"));
        return userMapper.update(olduser);
    }

    @Override
    public ArrayList<User> selectAll() {
        return userMapper.selectAll();
    }

    @Override
    public User selectByid(Integer id) {
        return userMapper.selectByid(id);
    }

    @Override
    public int delete(int id) {
        return userMapper.delete(id);
    }

    @Override
    public ArrayList<User> select_roleAll() {
        return userMapper.select_roleAll();
    }

    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}
