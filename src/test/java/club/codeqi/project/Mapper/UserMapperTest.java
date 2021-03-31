package club.codeqi.project.Mapper;

import club.codeqi.project.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/29 0029 10:16
 */
@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;
    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void selectAll() {
        ArrayList<User> users = userMapper.selectAll();
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
    }

    @Test
    void selectByid() {
    }

    @Test
    void delete() {
    }

    @Test
    void select_roleAll() {
    }

    @Test
    void findByName() {
        User user = userMapper.findByName("高小龙");
        System.out.println(user);
    }
}