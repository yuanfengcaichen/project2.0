package club.codeqi.project.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/16 0016 13:34
 */
@Data
public class Task implements Serializable {
    private Integer id;
    private String label;
    private String user;
    private Date startTime;
    private Date endTime;
    private Integer percent;
    private String type;
    private Integer parentId;
    private Integer collapsed;
    private String fill;
    private String stroke;
}
