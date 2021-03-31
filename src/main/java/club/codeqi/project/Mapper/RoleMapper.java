package club.codeqi.project.Mapper;

import club.codeqi.project.pojo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CachePut;

import java.util.ArrayList;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/29 0029 9:21
 */
@Mapper
public interface RoleMapper {
    public int insert(Role role);
    public int update(Role role);
    public ArrayList<Role> selectAll();
    public Role selectByid(Integer id);
    public int delete(int id);
}
