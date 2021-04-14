package club.codeqi.project.Service;

import club.codeqi.project.VO.TaskVO;
import club.codeqi.project.VO.UserTravelVO;
import club.codeqi.project.pojo.Task;
import club.codeqi.project.pojo.User;

import java.util.ArrayList;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/16 0016 13:46
 */
public interface TaskService {
    public ArrayList<TaskVO> getAlltasks(User user);//甘特图首页进行请求时处理函数
    public ArrayList<TaskVO> getUsertasks(String username);//使用人员筛选时请求时的处理函数
    public ArrayList<TaskVO> getSingleTask();
    public ArrayList<TaskVO> gettaskByid(Integer id);
    public ArrayList<UserTravelVO> getTravel(long time);
    public Integer insertTask(Task task);
    public Integer insertConsultTask(Task task);
    public Integer updateTask(Task task);
    public Integer deleteTask(Integer id);
}
