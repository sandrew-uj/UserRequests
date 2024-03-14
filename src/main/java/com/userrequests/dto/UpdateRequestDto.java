package com.userrequests.dto;

import lombok.Data;

@Data
public class UpdateRequestDto {
    private String name;

    private String message;

    private String phone;

    private int requestId;
}
