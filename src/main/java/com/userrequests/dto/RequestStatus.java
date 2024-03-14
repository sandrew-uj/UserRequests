package com.userrequests.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum RequestStatus {
    DEFAULT("default"),
    DRAFT("draft"),
    APPROVED("approved"),
    DECLINED("declined"),
    SENT("sent");
    final String name;
}
