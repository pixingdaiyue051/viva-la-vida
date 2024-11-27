package com.tequeno.vivaboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@TableName("emp")
public class Emp {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("no")
    private String no;

    @TableField("name")
    private String name;

    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("dept_id")
    private Long deptId;
}
