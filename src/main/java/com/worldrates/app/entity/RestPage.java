package com.worldrates.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor(onConstructor = @__(@JsonCreator))
@Getter
@ToString
public class RestPage<T> {
    @JsonProperty("content")
    private final List<T> content;
    @JsonProperty("totalElements")
    private final Integer totalElements;

}
