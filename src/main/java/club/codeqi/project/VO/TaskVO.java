package club.codeqi.project.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: codeqi
 * @Description:
 * @Date: create in 2021/3/16 0016 13:49
 */
@Data
public class TaskVO implements Serializable {
    private Integer id;
    private Integer value;
    private String label;
    private String user;
    private Long startTime;
    private Long endTime;
    private Integer percent;
    private String type;
    private Integer parentId;
    private Boolean collapsed;
    private Map style = new HashMap();
    private ArrayList<TaskVO> children;
}
