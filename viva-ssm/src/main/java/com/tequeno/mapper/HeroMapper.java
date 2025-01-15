package com.tequeno.mapper;

import com.tequeno.dto.hero.HeroDetailDto;
import com.tequeno.dto.hero.HeroQueryDto;
import com.tequeno.dto.hero.HeroUpsertDto;

import java.util.List;

public interface HeroMapper {

    HeroDetailDto selectById(Long id);

    long count(HeroQueryDto dto);

    List<HeroDetailDto> select(HeroQueryDto dto);

    int insert(HeroUpsertDto dto);

    int update(HeroUpsertDto dto);
}
