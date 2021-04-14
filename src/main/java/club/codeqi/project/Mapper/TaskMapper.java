package club.codeqi.project.Mapper;

import club.codeqi.project.pojo.Task;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/16 0016 13:36
 */
@Mapper
@CacheConfig(cacheNames = "tasks")
public interface TaskMapper {
    @CacheEvict(key ="#p0",allEntries=true)
    public int insert(Task task);
    @CachePut(key = "#p0")
    public int update(Task task);

    public ArrayList<Task> selectAll();
    public ArrayList<Task> selectAllByUsername(String user);
    public ArrayList<Task> selectParent();
    @Cacheable(key ="'pid_'+#p0+'_children'")
    public ArrayList<Task> selectBypid(Integer pid);
    @Cacheable(key ="#p0")
    public Task selectByid(Integer id);

    public ArrayList<Task> getTravel(String username, Date time);

    @CacheEvict(key ="#p0",allEntries=true)
    public int delete(int id);
}
