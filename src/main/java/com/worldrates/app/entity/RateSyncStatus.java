package com.worldrates.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor(onConstructor=@__(@JsonCreator))
@Getter
@ToString
public class RateSyncStatus {
    @JsonProperty("startedAt")
    private final LocalDateTime startedAt;
    @JsonProperty("completedAt")
    private final LocalDateTime completedAt;
    @JsonProperty("status")
    private final String status;
    @JsonProperty("info")
    private final String info;
    @JsonProperty("warn")
    private final String warn;
    @JsonProperty("error")
    private final String error;

}
