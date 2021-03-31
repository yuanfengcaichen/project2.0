package club.codeqi.project.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/26 0026 16:45
 */
@Data
public class Permission implements GrantedAuthority, Serializable {
    private Integer id;
    private Integer rid;
    private String permissionCode;
    private String permissionInfo;
    private Date createTime;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return permissionCode;
    }
}
