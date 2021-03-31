package club.codeqi.project.Controller;

import club.codeqi.project.Mapper.PermissionMapper;
import club.codeqi.project.pojo.Permission;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:55
 */
@RestController
public class PermissionController {
    @Autowired
    PermissionMapper permissionMapper;

    @GetMapping("/permisses")
    public PageInfo admin_permisses(@RequestParam(required = false,name = "pageNum") Integer pageNum, @RequestParam(required = false,name = "pageSize") Integer pageSize){
        if(pageNum == null) pageNum = 1;
        if(pageSize == null) pageSize = 100;
        PageHelper.startPage(pageNum, pageSize);
        List<Permission> list = permissionMapper.selectAll();
        PageInfo page = new PageInfo(list);
        return page;
    }

    @GetMapping("/role_permisses")
    public PageInfo role_permisses(@RequestParam(required = false,name = "pageNum") Integer pageNum, @RequestParam(required = false,name = "pageSize") Integer pageSize, @RequestParam(required = false,name = "rid") Integer rid){
        if(pageNum == null) pageNum = 1;
        if(pageSize == null) pageSize = 100;
        PageHelper.startPage(pageNum, pageSize);
        List<Permission> list = permissionMapper.selectByrid(rid);
        PageInfo page = new PageInfo(list);
        return page;
    }

    @PostMapping("/permisses")
    public Map addrolepermisses(@RequestBody Map map){
        Integer rid = (Integer) map.get("rid");
        ArrayList arrayList = (ArrayList) map.get("rpermisses");
        if(rid!=1){
            permissionMapper.deleteByrid(rid);
            List<Permission> list = permissionMapper.selectAll();
            for(Permission per : list){
                if(arrayList.contains(per.getPermissionInfo())){
                    //System.out.println(per);
                    Permission permission = new Permission();
                    permission.setRid(rid);
                    permission.setPermissionCode(per.getPermissionCode());
                    permission.setPermissionInfo(per.getPermissionInfo());
                    permission.setCreateTime(new Date());
                    permissionMapper.insert(permission);
                }
            }
        }
        HashMap resultMap = new HashMap();
        resultMap.put("result",200);
        return resultMap;
    }
}
