package com.lesleyzh.cat_demo.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableCatLocation.class)
@JsonDeserialize(as = ImmutableCatLocation.class)
public interface CatLocation {
    String catId();
    double locationX();
    double locationY();
}
