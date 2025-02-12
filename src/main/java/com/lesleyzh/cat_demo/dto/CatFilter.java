package com.lesleyzh.cat_demo.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

import java.util.Optional;

// filter for cat: name, age, breed
@Value.Immutable //注意添加完这三个annotation后要手动点击build才会应用上
@JsonSerialize(as = ImmutableCatFilter.class)
@JsonDeserialize(as = ImmutableCatFilter.class)
public interface CatFilter {
    Optional<String> getName();
    Optional<Integer> getAge();
    Optional<CatBreed> getBreed();
}
