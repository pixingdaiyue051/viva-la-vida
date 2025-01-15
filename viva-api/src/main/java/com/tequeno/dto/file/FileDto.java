package com.tequeno.dto.file;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class FileDto {

    private Long id;

    private String name;

    private String path;

    private Long size;

    private String suffix;
}
