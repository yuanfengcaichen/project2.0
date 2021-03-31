package club.codeqi.project.Controller;

import club.codeqi.project.Common.domain.Payload;
import club.codeqi.project.Common.utils.JsonUtils;
import club.codeqi.project.Common.utils.JwtUtils;
import club.codeqi.project.Config.RsaKeyProperties;
import club.codeqi.project.Mapper.UserMapper;
import club.codeqi.project.pojo.Permission;
import club.codeqi.project.pojo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    UserMapper userMapper;
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
        List<User> list = userMapper.select_roleAll();//group by uid
        PageInfo page = new PageInfo(list);
        return page;
    }

    @GetMapping("/Allusers")
    public Result Allusers(){
        List<User> list = userMapper.select_roleAll();//group by uid
        Result result = new Result();
        result.setCode("200");
        result.setData(list);
        return result;
    }
}
