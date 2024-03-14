package com.userrequests.service.impl;

import com.userrequests.dto.RequestDto;
import com.userrequests.dto.RequestResponse;
import com.userrequests.dto.RequestStatus;
import com.userrequests.dto.UpdateRequestDto;
import com.userrequests.exceptions.PermissionDeniedException;
import com.userrequests.exceptions.UnableToEditRequestException;
import com.userrequests.models.Request;
import com.userrequests.models.UserEntity;
import com.userrequests.repositories.RequestRepository;
import com.userrequests.repositories.UserRepository;
import com.userrequests.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class DefaultRequestService implements RequestService {

    private static final int PAGE_SIZE = 5;
    
    private static final String DATE_COL = "timestamp";
    private final UserRepository userRepository;

    private final RequestRepository requestRepository;

    @Autowired
    public DefaultRequestService(UserRepository userRepository, RequestRepository requestRepository) {
        this.userRepository = userRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public RequestDto createRequest(RequestDto requestDto, UserEntity userEntity) {
        var request = toRequest(requestDto, userEntity);
        var resRequest = requestRepository.save(request);
        return toDto(resRequest);
    }

    @Override
    public RequestDto createDraftRequest(RequestDto requestDto, UserEntity userEntity) {
        var request = toRequest(requestDto, userEntity);
        request.setStatus(RequestStatus.DRAFT.name());
        var resRequest = requestRepository.save(request);
        return toDto(resRequest);
    }

    @Override
    public RequestDto updateRequest(UpdateRequestDto updateRequestDto, UserEntity userEntity) {
        var request = requestRepository.getReferenceById(updateRequestDto.getRequestId());
        if (!RequestStatus.DRAFT.name().equals(request.getStatus())) {
            throw new UnableToEditRequestException(
                    String.format(
                            "Can't edit this request, because it's not in draft status: requestId=%d",
                            updateRequestDto.getRequestId()
                    ));
        }
        if (request.getUserEntity().getId() != userEntity.getId()) {
            throw new PermissionDeniedException(
                    String.format(
                            "You have no permission to edit this request, it's not yours: requestId=%d",
                            updateRequestDto.getRequestId()
                    ));
        }
        request.setName(updateRequestDto.getName());
        request.setPhone(updateRequestDto.getPhone());
        request.setMessage(updateRequestDto.getMessage());
        request.setTimestamp(Instant.now().toEpochMilli());
        var updatedRequest = requestRepository.save(request);

        return toDto(updatedRequest);
    }

    @Override
    public RequestDto sendToOperator(int requestId, UserEntity userEntity) {
        var request = requestRepository.getReferenceById(requestId);

        if (request.getUserEntity().getId() != userEntity.getId()) {
            throw new PermissionDeniedException(
                    String.format(
                            "You have no permission to send this request to operator, it's not yours: requestId=%d",
                            requestId
                    ));
        }

        request.setStatus(RequestStatus.SENT.name());
        var updatedRequest = requestRepository.save(request);
        return toDto(updatedRequest);
    }

    @Override
    public RequestDto acceptRequestById(int requestId) {
        var request = requestRepository.getReferenceById(requestId);
        request.setStatus(RequestStatus.APPROVED.name());
        requestRepository.save(request);
        return toDto(request);
    }

    @Override
    public RequestDto declineRequestById(int requestId) {
        var request = requestRepository.getReferenceById(requestId);
        request.setStatus(RequestStatus.DECLINED.name());
        requestRepository.save(request);
        return toDto(request);
    }

    @Override
    public RequestResponse getUserRequests(String username, int pageNo, boolean isAsc) {
        return getRequests(username, "", pageNo, isAsc, true);
    }

    @Override
    public RequestResponse getAllRequests(int pageNo, boolean isAsc) {
        return getRequests("", "", pageNo, isAsc, false);
    }

    @Override
    public RequestResponse getLikeUserRequests(String username, int pageNo, boolean isAsc) {
        return getRequests(username, "", pageNo, isAsc, false);
    }

    @Override
    public RequestResponse getByStatusRequests(String status, String username, int pageNo, boolean isAsc) {
        return getRequests(username, status, pageNo, isAsc, false);
    }

    private Page<Request> getPageableRequests(String username, String status, Pageable pageable, boolean isFullUsername) {
        if ("".equals(username) && "".equals(status)) {
            return requestRepository.findAll(pageable);
        }

        if ("".equals(status)) {
            return isFullUsername ?
                    requestRepository.findAllByUsername(username, pageable) :
                    requestRepository.findAllUsernameContaining(username, pageable);
        }

        if ("".equals(username)) {
            return requestRepository.findAllByStatus(status, pageable);
        }

        return isFullUsername ?
                requestRepository.findAllByUsernameAndStatus(username, status, pageable) :
                requestRepository.findAllUsernameContainingAndStatus(username, status, pageable);
    }

    @Override
    public RequestDto getRequestById(int requestId) {
        var request = requestRepository.getReferenceById(requestId);
        return toDto(request);
    }

    private RequestResponse getRequests(String username, String status, int pageNo, boolean isAsc, boolean isFullUsername) {
        var sort = isAsc ? Sort.Order.asc(DATE_COL) : Sort.Order.desc(DATE_COL);
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, Sort.by(sort));

        Page<Request> requests = getPageableRequests(username, status, pageable, isFullUsername);
        List<RequestDto> content = requests.getContent().stream().map(DefaultRequestService::toDto).toList();

        return new RequestResponse(
                content,
                pageNo,
                requests.getTotalElements(),
                requests.getTotalPages(),
                requests.isLast()
        );
    }

    private static RequestDto toDto(Request request) {
        var requestDto = new RequestDto();

        requestDto.setName(request.getName());
        requestDto.setMessage(request.getMessage());
        requestDto.setPhone(request.getPhone());

        return requestDto;
    }

    private Request toRequest(RequestDto requestDto, UserEntity userEntity) {
        var request = new Request();

        request.setName(requestDto.getName());
        request.setMessage(requestDto.getMessage());
        request.setPhone(requestDto.getPhone());
        request.setUserEntity(userEntity);
        request.setStatus(RequestStatus.DEFAULT.name());
        request.setTimestamp(Instant.now().toEpochMilli());

        return request;
    }
}
