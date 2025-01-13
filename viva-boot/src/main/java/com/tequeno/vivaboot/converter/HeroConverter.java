package com.tequeno.vivaboot.converter;

import com.tequeno.dto.hero.HeroDetailDto;
import com.tequeno.vivaboot.entity.Hero;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HeroConverter {

    HeroConverter INSTANCE = Mappers.getMapper(HeroConverter.class);

    HeroDetailDto entity2Detail(Hero item);
}
