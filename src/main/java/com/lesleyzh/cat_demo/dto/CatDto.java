package com.lesleyzh.cat_demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value.Immutable;

@Immutable
@JsonSerialize(as = ImmutableCatDto.class)
@JsonDeserialize(as = ImmutableCatDto.class)
public interface CatDto {

    String getName();
    int getAgeYear();
    CatBreed getBreed();

}
