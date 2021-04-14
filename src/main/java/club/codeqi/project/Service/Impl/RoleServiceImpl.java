package club.codeqi.project.Service.Impl;

import club.codeqi.project.Mapper.RoleMapper;
import club.codeqi.project.Service.RoleService;
import club.codeqi.project.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:52
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public int insert(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int update(Role role) {
        return roleMapper.update(role);
    }

    @Override
    public ArrayList<Role> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public Role selectByid(Integer id) {
        return roleMapper.selectByid(id);
    }

    @Override
    public int delete(int id) {
        return roleMapper.delete(id);
    }
}
