package cn.idongjia.divine.db.mybatis.query;

import cn.idongjia.common.pagination.Pagination;
import cn.idongjia.common.query.BaseSearch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * 描述：客户表 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class CustomerQuery extends BaseSearch {

    /**
     * 客户id
     */
    private Integer id;

    /**
     * 主用户id
     */
    private Integer mainUserId;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 客户手机号码
     */
    private String mobile;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 客户头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 简短描述
     */
    private String brief;

    /**
     * 性别, 1 man 2 woman
     */
    private Integer gender;

    /**
     * 国际区号及短信模板
     */
    private Integer incode;

    /**
     *
     */
    private String background;

    /**
     * 状态, 0 正常 1 屏蔽 2 删除
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 用户id
     */
    private List<Long> customerIds;

    @Builder
    public CustomerQuery(String logPid,String orderBy, Integer limit, Integer page, Long start, Long end, Timestamp beginTime, Timestamp endTime, String order, Boolean needCount, Integer offset,
            Pagination pagination, Integer id, Integer mainUserId, String name, String mobile, String realName, String avatar, String email, String brief, Integer gender, Integer incode,
            String background, Integer status, Date createTime, Date updateTime, List<Long> customerIds) {
        super(orderBy, limit, page, start, end, beginTime, endTime, order, needCount,logPid,offset, pagination);
        this.id = id;
        this.mainUserId = mainUserId;
        this.name = name;
        this.mobile = mobile;
        this.realName = realName;
        this.avatar = avatar;
        this.email = email;
        this.brief = brief;
        this.gender = gender;
        this.incode = incode;
        this.background = background;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.customerIds = customerIds;
    }
}
