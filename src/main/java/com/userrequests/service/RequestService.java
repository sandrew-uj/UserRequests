package com.userrequests.service;

import com.userrequests.dto.RequestDto;
import com.userrequests.dto.RequestResponse;
import com.userrequests.dto.UpdateRequestDto;
import com.userrequests.models.Request;
import com.userrequests.models.UserEntity;

import java.util.List;

public interface RequestService {
    RequestDto createRequest(RequestDto requestDto, UserEntity userEntity);

    RequestDto createDraftRequest(RequestDto requestDto, UserEntity userEntity);

    RequestResponse getUserRequests(String username, int pageNo, boolean isAsc);

    RequestDto getRequestById(int requestId);

    RequestDto acceptRequestById(int requestId);

    RequestDto declineRequestById(int requestId);

    RequestResponse getAllRequests(int pageNo, boolean isAsc);

    RequestResponse getLikeUserRequests(String username, int pageNo, boolean isAsc);

    RequestResponse getByStatusRequests(String status, String username, int pageNo, boolean isAsc);

    RequestDto updateRequest(UpdateRequestDto updateRequestDto, UserEntity userEntity);

    RequestDto sendToOperator(int requestId, UserEntity userEntity);
}
