package com.userrequests.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestResponse {
    private List<RequestDto> content;
    private int pageNo;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
