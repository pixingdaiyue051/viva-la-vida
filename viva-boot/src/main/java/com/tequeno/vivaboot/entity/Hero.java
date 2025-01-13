package com.tequeno.vivaboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@TableName("hero")
public class Hero {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("nick_name")
    private String nickName;

    @TableField("constellation")
    private String constellation;

    @TableField("short_constellation")
    private String shortConstellation;

    @TableField("weaponry")
    private String weaponry;

    @TableField("position")
    private String position;

    @TableField("finale")
    private String finale;

    @TableField("type")
    private Integer type;

    @TableField("rank")
    private Integer rank;

    @TableField("full_rank")
    private Integer fullRank;
}