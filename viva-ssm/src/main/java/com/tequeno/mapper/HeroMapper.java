package com.tequeno.mapper;

import com.tequeno.dto.hero.HeroDetailDto;
import com.tequeno.dto.hero.HeroQueryDto;
import com.tequeno.dto.hero.HeroUpsertDto;

import java.util.List;

public interface HeroMapper {

    HeroDetailDto selectById(Long id);

    List<HeroDetailDto> select(HeroQueryDto dto);

    int insert(HeroUpsertDto dto);

    int update(HeroUpsertDto dto);
}
