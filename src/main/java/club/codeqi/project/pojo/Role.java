package club.codeqi.project.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:43
 */
@Data
public class Role {
    private Integer id;
    private String roleName;
    private String roleInfo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private List<Permission> permissions;
}
