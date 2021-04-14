package club.codeqi.project.Service;

import club.codeqi.project.pojo.Role;

import java.util.ArrayList;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:51
 */
public interface RoleService {
    public int insert(Role role);
    public int update(Role role);
    public ArrayList<Role> selectAll();
    public Role selectByid(Integer id);
    public int delete(int id);
}
