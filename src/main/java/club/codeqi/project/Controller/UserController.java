package club.codeqi.project.Controller;

import club.codeqi.project.Common.domain.Payload;
import club.codeqi.project.Common.utils.JsonUtils;
import club.codeqi.project.Common.utils.JwtUtils;
import club.codeqi.project.Config.RsaKeyProperties;
import club.codeqi.project.Mapper.UserMapper;
import club.codeqi.project.Service.UserService;
import club.codeqi.project.pojo.Permission;
import club.codeqi.project.pojo.Role;
import club.codeqi.project.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:54
 */
@RestController
public class UserController {
    @Autowired
    RsaKeyProperties prop;

    @Autowired
    UserService userService;
    @GetMapping("/userinfo")
    public Map loginuserinfo(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.replace("Bearer ","");
        Payload<User> payload = JwtUtils.getInfoFromToken(token,prop.getPublicKey(), User.class);
        User user = payload.getUserInfo();
        //user.getPermissions();//1. 转为json，2. JsonUtil.tolist(json,permission.class)
        user.setPermissions(JsonUtils.toList(JsonUtils.toString(user.getPermissions()), Permission.class));
        Map map = new HashMap();
        map.put("result",200);
        map.put("userinfo",user);
        map.put("token",request.getHeader("Authorization"));
        map.put("expire",payload.getExpiration());
        return map;
    }
    @GetMapping("/users")
    public PageInfo userAll(@RequestParam(required = false,name = "pageNum") Integer pageNum, @RequestParam(required = false,name = "pageSize") Integer pageSize){
        if(pageNum == null) pageNum = 1;
        if(pageSize == null) pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userService.select_roleAll();//group by uid
        PageInfo page = new PageInfo(list);
        return page;
    }

    @PostMapping("/user")
    public Map addUser(@RequestBody Map map){
        HashMap resultMap = new HashMap();
        resultMap.put("result",200);
        Integer id = (Integer) map.get("id");
        String username = (String) map.get("username");
        String password = (String) map.get("password");
        String phone = (String) map.get("phone");
        Integer role_id = (Integer) map.get("roleId");
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setPhone(phone);
        user.setRoleId(role_id==null?2:role_id);
        if(id==0){
            int insert = userService.insert(user);
            resultMap.put("code",insert);
            resultMap.put("type","add");
        }
        else{
            int update = userService.update(user);
            resultMap.put("code",update);
            resultMap.put("type","edit");
        }
        return resultMap;
    }

    @DeleteMapping("/user")
    public Map deleteUser(@RequestBody Map map){
        List<Integer> uids = (List<Integer>) map.get("uids");
        for(Integer uid : uids){
            userService.delete(uid);
        }
        HashMap resultMap = new HashMap();
        resultMap.put("result",200);
        resultMap.put("type","delete");
        return resultMap;
    }

    @PostMapping("/resetpass/{id}")
    public Map resetUserPassword(@PathVariable Integer id){
        int resetpass = userService.resetpass(id);
        HashMap resultMap = new HashMap();
        resultMap.put("result",200);
        resultMap.put("code",resetpass);
        resultMap.put("type","resetpassword");
        return resultMap;
    }

    //创建新任务时需要获取全部人员（不使用分页）
    @GetMapping("/Allusers")
    public Result Allusers(){
        List<User> list = userService.select_roleAll();//group by uid
        Result result = new Result();
        result.setCode("200");
        result.setData(list);
        return result;
    }



}
