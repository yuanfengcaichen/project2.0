package club.codeqi.project.VO;

import club.codeqi.project.pojo.Task;
import club.codeqi.project.pojo.User;
import lombok.Data;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/4/6 0006 11:13
 */
@Data
public class UserTravelVO {
    private User user;
    private String location="";
}
