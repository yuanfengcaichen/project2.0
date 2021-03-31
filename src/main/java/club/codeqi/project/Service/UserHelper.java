package club.codeqi.project.Service;

import club.codeqi.project.Common.domain.Payload;
import club.codeqi.project.Common.utils.JsonUtils;
import club.codeqi.project.Common.utils.JwtUtils;
import club.codeqi.project.Config.RsaKeyProperties;
import club.codeqi.project.pojo.Permission;
import club.codeqi.project.pojo.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/30 0030 12:04
 */
public class UserHelper {
    private static String pubKeyFile= "D:\\亚龙展旗\\项目2021\\项目管理\\project\\auth_key\\rsa_key.pub";
    private static String priKeyFile= "D:\\亚龙展旗\\项目2021\\项目管理\\project\\auth_key\\rsa_key";
    private static RsaKeyProperties prop = new RsaKeyProperties(pubKeyFile,priKeyFile);

    public static boolean checkPre(String Permisscode, User user) {
        List<Permission> permissions = user.getPermissions();
        for(int i=0;i<permissions.size();i++){
            if(permissions.get(i).getPermissionCode().equals(Permisscode)){
                return true;
            }
        }
        return false;
    }

    public static User getUserInfo(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        String token = header.replace("Bearer ","");
        Payload<User> payload = JwtUtils.getInfoFromToken(token,prop.getPublicKey(), User.class);
        User user = payload.getUserInfo();
        //user.getPermissions();//1. 转为json，2. JsonUtil.tolist(json,permission.class)
        user.setPermissions(JsonUtils.toList(JsonUtils.toString(user.getPermissions()), Permission.class));
        return user;
    }
}
