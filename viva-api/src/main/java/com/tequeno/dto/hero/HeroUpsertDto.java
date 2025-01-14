package com.tequeno.dto.hero;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class HeroUpsertDto {

    private Long id;

    private String name;

    private String nickName;

    private String constellation;

    private String shortConstellation;

    private String weaponry;

    private String position;

    private String finale;

    private Integer type;

    private Integer rank;

    private Integer fullRank;
}
