package club.codeqi.project.Mapper;

import club.codeqi.project.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/29 0029 9:21
 */
@Mapper
//@CacheConfig(cacheNames = "users")
public interface UserMapper {
    public int insert(User user);
    //@CachePut(key = "#p0")
    public int update(User user);
    public ArrayList<User> selectAll();
    //@Cacheable(key ="#p0")
    public User selectByid(Integer id);
    //@CacheEvict(key ="#p0",allEntries=true)
    public int delete(int id);

    public ArrayList<User> select_roleAll();

    public User findByName(String name);
}
