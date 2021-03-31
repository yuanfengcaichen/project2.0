package club.codeqi.project.Mapper;

import club.codeqi.project.pojo.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/29 0029 9:21
 */
@Mapper
public interface PermissionMapper {
    public int insert(Permission permission);
    public int update(Permission permission);
    public ArrayList<Permission> selectAll();
    public Permission selectByid(Integer perid);
    public ArrayList<Permission> selectByrid(Integer rid);
    public int delete(int perid);
    public int deleteByrid(int rid);
}
