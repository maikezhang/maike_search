package cn.idongjia.divine.db.mybatis.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import cn.idongjia.common.base.Base;
import cn.idongjia.divine.utils.DateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 描述：客户表 Database Object
 *
 * @author lc
 * @date 2018/07/31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDO extends Base {

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
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date createTime;

    /**
     * 修改时间
     */
    @JsonDeserialize(using = DateDeserializer.class)
    private java.util.Date updateTime;

}
