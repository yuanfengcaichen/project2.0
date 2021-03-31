package club.codeqi.project.Service.Impl;

import club.codeqi.project.Mapper.UserMapper;
import club.codeqi.project.Service.UserService;
import club.codeqi.project.pojo.Permission;
import club.codeqi.project.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:53
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userMapper.findByName(s);
    }

}
