package club.codeqi.project.Mapper;

import club.codeqi.project.pojo.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/31 0031 11:19
 */
@SpringBootTest
class RoleMapperTest {
    @Autowired
    RoleMapper roleMapper;

    @Test
    void insert() {
        Role role = new Role();
        role.setRoleName("teste");
        role.setRoleInfo("test");
        role.setCreateTime(new Date());
        roleMapper.insert(role);
    }

    @Test
    void update() {
    }

    @Test
    void selectAll() {
    }

    @Test
    void selectByid() {
    }

    @Test
    void delete() {
    }
}