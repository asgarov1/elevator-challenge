package com.asgarov.elevator_challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Request {
    private int floorFrom;
    private int floorTo;
}
