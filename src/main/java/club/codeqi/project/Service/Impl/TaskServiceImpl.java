package club.codeqi.project.Service.Impl;

import club.codeqi.project.Mapper.TaskMapper;
import club.codeqi.project.Mapper.UserMapper;
import club.codeqi.project.Service.TaskService;
import club.codeqi.project.Service.UserHelper;
import club.codeqi.project.VO.TaskVO;
import club.codeqi.project.VO.UserTravelVO;
import club.codeqi.project.pojo.Task;
import club.codeqi.project.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/16 0016 13:47
 */
@Transactional(readOnly = true,propagation = Propagation.SUPPORTS)
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;

    @Autowired
    UserMapper userMapper;
    @Override
    public ArrayList<TaskVO> getAlltasks(User userinfo) {
        boolean b = UserHelper.checkPre("selectAll", userinfo);
        if(b){
            ArrayList<Task> tasks = taskMapper.selectAll();
            return trantTasks(tasks);
        }
        else{
            ArrayList<Task> tasks = taskMapper.selectAllByUsername(userinfo.getUsername());
            tasks = addParent(tasks);
            tasks = addParent(tasks);
            tasks = addParent(tasks);
            tasks = addParent(tasks);
            return trantTasks(tasks);
        }
    }

    @Override
    public ArrayList<TaskVO> getUsertasks(String username) {
        ArrayList<Task> tasks = taskMapper.selectAllByUsername(username);
        tasks = addParent(tasks);
        tasks = addParent(tasks);
        tasks = addParent(tasks);
        tasks = addParent(tasks);
        return trantTasks(tasks);
    }

    public ArrayList<Task> addParent(ArrayList<Task> tasks){
        for(int i=0;i<tasks.size();i++){
            Task task = tasks.get(i);
            Integer parentId = task.getParentId();
            if(parentId!=null){
                boolean checkparentid = checkparentid(parentId, tasks);
                if(checkparentid){
                    continue;
                }
                else {
                    Task parentTask = taskMapper.selectByid(parentId);
                    tasks.add(parentTask);
                }
            }
        }
        //ArrayList<Task> tasks1 = addParent(tasks);
        return tasks;
    }

    public boolean checkparentid(int parentid,ArrayList<Task> tasks){
        for(int i=0;i<tasks.size();i++){
            if(tasks.get(i).getId()==parentid){
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<TaskVO> getSingleTask() {
        ArrayList<Task> Parenttasks = taskMapper.selectParent();
        ArrayList<TaskVO> taskVOS = new ArrayList<>();
        for(Task Parenttask:Parenttasks){
            TaskVO taskVO = trantTask(Parenttask);
            ArrayList<TaskVO> childrenTasks = getSingleTask(taskVO);
            taskVO.setChildren(childrenTasks);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }

    public ArrayList<TaskVO> getSingleTask(TaskVO taskVO){
        ArrayList<Task> children = taskMapper.selectBypid(taskVO.getId());
        ArrayList<TaskVO> taskVOS = trantTasks(children);
        for(int i=0;i<taskVOS.size();i++){
            ArrayList<TaskVO> singleTask = getSingleTask(taskVOS.get(i));
            taskVOS.get(i).setChildren(singleTask);
        }
        return taskVOS;
    }

    @Override
    public ArrayList<TaskVO> gettaskByid(Integer id) {
        Task task = taskMapper.selectByid(id);
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task);
        ArrayList<Task> tasksBypid = getchildrenTask(tasks,id);
        return trantTasks(tasksBypid);
    }

    public ArrayList<Task> getchildrenTask(ArrayList<Task> tasks,Integer pid){
        ArrayList<Task> children = taskMapper.selectBypid(pid);
        tasks.addAll(children);
        for(int i=0;i<children.size();i++){
            ArrayList<Task> tasks1 = getchildrenTask(tasks, children.get(i).getId());
            //tasks.addAll(tasks1);
        }
        return tasks;
    }


    @Override
    public ArrayList<UserTravelVO> getTravel(long time) {
        ArrayList<User> users = userMapper.selectAll();
        ArrayList<UserTravelVO> userTravelVOS = new ArrayList<>();
        for(User user:users){
            UserTravelVO userTravelVO = new UserTravelVO();
            userTravelVO.setUser(user);
            ArrayList<Task> arrayList=taskMapper.getTravel(user.getUsername(),new Date(time));
            ArrayList<String> locations = new ArrayList<>();
            if(arrayList.size()!=0){
                Task parentTask = taskMapper.selectByid(arrayList.get(0).getParentId());
                locations.add(parentTask.getLabel());
                parentTask = taskMapper.selectByid(parentTask.getParentId());
                locations.add(parentTask.getLabel());
                parentTask = taskMapper.selectByid(parentTask.getParentId());
                locations.add(parentTask.getLabel());
                userTravelVO.setLocation(locations.get(locations.size()-1));
//                for(int i=0;i<locations.size();i++){
//                    userTravelVO.setLocation(userTravelVO.getLocation()+locations.get(locations.size()-1-i)+"<br/>");
//                }
            }
            else {
                userTravelVO.setLocation("暂无出差任务");
            }
            userTravelVOS.add(userTravelVO);
        }
        return userTravelVOS;
    }

    public TaskVO trantTask(Task task){
        TaskVO taskVO = new TaskVO();
        taskVO.setId(task.getId());
        taskVO.setValue(task.getId());
        taskVO.setLabel(task.getLabel());
        taskVO.setUser(task.getUser());
        taskVO.setStartTime(task.getStartTime().getTime());
        taskVO.setEndTime(task.getEndTime().getTime());
        taskVO.setPercent(task.getPercent());
        taskVO.setType(task.getType());
        taskVO.setParentId(task.getParentId());
        taskVO.setCollapsed(task.getCollapsed()==1?true:false);
        Map base = new HashMap();
        base.put("fill",task.getFill());
        base.put("stroke",task.getStroke());
        taskVO.getStyle().put("base",base);
        return taskVO;
    }

    public ArrayList<TaskVO> trantTasks(ArrayList<Task> tasks){
        ArrayList<TaskVO> taskVOS = new ArrayList<>();
        for (Task task : tasks){
            TaskVO taskVO = trantTask(task);
            taskVOS.add(taskVO);
        }
        return taskVOS;
    }

    @Override
    public Integer insertTask(Task task) {
        return taskMapper.insert(task);
    }

    @Override
    public Integer insertConsultTask(Task task) {
        int insert = taskMapper.insert(task);//添加项目
        task.setParentId(task.getId());//将阶段的父id设置为项目id
        task.setType("task");
        task.setPercent(0);
        Date startTime = task.getStartTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        for(int i=0;i<5;i++){
            if(i==0){
                task.setLabel("项目启动");
            }else if(i==1){
                task.setLabel("项目调研");
            }else if(i==2){
                task.setLabel("项目实施");
            }else if(i==3){
                task.setLabel("项目改进");
            }else if(i==4){
                task.setLabel("项目验证");
            }
            task.setStartTime(calendar.getTime());
            calendar.add(Calendar.MONTH,1);
            task.setEndTime(calendar.getTime());
            taskMapper.insert(task);
        }
        return insert;
    }

    @Override
    public Integer updateTask(Task task) {
        return taskMapper.update(task);
    }

    @Override
    public Integer deleteTask(Integer id) {
        return taskMapper.delete(id);
    }
}
