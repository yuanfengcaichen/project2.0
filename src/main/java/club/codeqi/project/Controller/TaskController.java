package club.codeqi.project.Controller;

import club.codeqi.project.Service.TaskService;
import club.codeqi.project.Service.UserHelper;
import club.codeqi.project.VO.TaskVO;
import club.codeqi.project.VO.UserTravelVO;
import club.codeqi.project.pojo.Task;
import club.codeqi.project.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/16 0016 13:36
 */

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @GetMapping("/test")
    public String test(){
        return "hello";
    }

    //@PreAuthorize("hasAuthority('selectAll')")
    @GetMapping("/tasks")
    public Result getAll(HttpServletRequest request,@RequestParam(required = false) Long time){
        User userInfo = UserHelper.getUserInfo(request);
        ArrayList<TaskVO> alltasks = taskService.getAlltasks(userInfo);
        Result result = new Result();
        result.setCode("200");
        result.setData(alltasks);
        return result;
    }

    /**
     * 根据用户名查询用户任务
     * @param username
     * @return
     */
    @GetMapping("/taskByname/{username}")
    public Result getUserTask(@PathVariable String username){
        ArrayList<TaskVO> alltasks = taskService.getUsertasks(username);
        Result result = new Result();
        result.setCode("200");
        result.setData(alltasks);
        return result;
    }

    @GetMapping("/parents")
    public Result parents(){
        ArrayList<TaskVO> alltasks = taskService.getSingleTask();
        Result result = new Result();
        result.setCode("200");
        result.setData(alltasks);
        return result;
    }

    @GetMapping("/task/{id}")
    public Result getOne(@PathVariable Integer id){
        ArrayList<TaskVO> taskVOS = taskService.gettaskByid(id);
        Result result = new Result();
        result.setCode("200");
        result.setData(taskVOS);
        return result;
    }

    /**
     * 根据传入的时间获取当前日期全部用户的出差信息
     * @param time
     * @return
     */
    @GetMapping("/travel")
    public Result getTravel(@RequestParam(required = false) Long time){
        ArrayList<UserTravelVO> userTravelVOS = taskService.getTravel(time);
        Result result = new Result();
        result.setCode("200");
        result.setData(userTravelVOS);
        return result;
    }

    @PostMapping("/task")
    public Result addOne(@RequestBody Task task){
        Integer integer = taskService.insertTask(task);
        Result result = new Result();
        result.setCode("200");
        result.setData(task);
        return result;
    }

    @PostMapping("/consultTask")
    public Result addconsultTask(@RequestBody Task task){
        Integer integer = taskService.insertConsultTask(task);
        Result result = new Result();
        result.setCode("200");
        result.setData(task);
        return result;
    }

    @PutMapping("/task")
    public Result update(@RequestBody Task task){
        Integer integer = taskService.updateTask(task);
        Result result = new Result();
        result.setCode("200");
        result.setData(task);
        return result;
    }

    @DeleteMapping("/task")
    public Result update(@RequestBody Map map){
        Integer id = (Integer) map.get("id");
        Integer integer = taskService.deleteTask(id);
        Result result = new Result();
        result.setCode("200");
        result.setData(integer);
        return result;
    }
}
