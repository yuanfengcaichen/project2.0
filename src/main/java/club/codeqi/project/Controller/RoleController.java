package club.codeqi.project.Controller;

import club.codeqi.project.Mapper.RoleMapper;
import club.codeqi.project.Service.RoleService;
import club.codeqi.project.pojo.Role;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:55
 */
@RestController
public class RoleController {
    @Autowired
    RoleService roleService;

    //不分页
    @GetMapping("/allroles")
    public HashMap allroles(){
        List<Role> list = roleService.selectAll();
        HashMap resultMap = new HashMap();
        resultMap.put("roleList",list);
        resultMap.put("result",200);
        resultMap.put("type","delete");
        return resultMap;
    }

    @GetMapping("/roles")
    public PageInfo roleAll(@RequestParam(required = false,name = "pageNum") Integer pageNum, @RequestParam(required = false,name = "pageSize") Integer pageSize){
        if(pageNum == null) pageNum = 1;
        if(pageSize == null) pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        List<Role> list = roleService.selectAll();
        PageInfo page = new PageInfo(list);
        return page;
    }

    @PostMapping("/role")
    public Map addrole(@RequestBody Map map){
        HashMap resultMap = new HashMap();
        resultMap.put("result",200);
        Integer rid = (Integer) map.get("id");
        String role_name = (String) map.get("roleName");
        String role_info = (String) map.get("roleInfo");
        if(rid==0){
            Role role = new Role();
            role.setRoleName(role_name);
            role.setRoleInfo(role_info);
            role.setCreateTime(new Date());
            roleService.insert(role);
            resultMap.put("type","add");
        }
        else{
            Role role = roleService.selectByid(rid);
            role.setRoleName(role_name);
            role.setRoleInfo(role_info);
            roleService.update(role);
            resultMap.put("type","edit");
        }
        return resultMap;
    }

    @DeleteMapping("/role")
    public Map deleterole(@RequestBody Map map){
        List<Integer> rids = (List<Integer>) map.get("rids");
        for(Integer rid : rids){
            roleService.delete(rid);
        }
        HashMap resultMap = new HashMap();
        resultMap.put("result",200);
        resultMap.put("type","delete");
        return resultMap;
    }
}
