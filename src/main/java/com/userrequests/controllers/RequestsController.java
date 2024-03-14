package com.userrequests.controllers;

import com.userrequests.dto.RequestDto;
import com.userrequests.dto.RequestResponse;
import com.userrequests.dto.UpdateRequestDto;
import com.userrequests.models.UserEntity;
import com.userrequests.repositories.UserRepository;
import com.userrequests.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
public class RequestsController {

    private final RequestService requestService;

    private final UserRepository userRepository;

    @Autowired
    public RequestsController(RequestService requestService, UserRepository userRepository) {
        this.requestService = requestService;
        this.userRepository = userRepository;
    }

    private String getUsername() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private UserEntity getUser() {
        return userRepository.findByUsername(getUsername()).get();
    }

    @PostMapping("user/create")
    public ResponseEntity<RequestDto> create(@RequestBody RequestDto requestDto) {
        return new ResponseEntity<>(requestService.createRequest(requestDto, getUser()), HttpStatus.CREATED);
    }

    @PostMapping("user/createDraft")
    public ResponseEntity<RequestDto> createDraft(@RequestBody RequestDto requestDto) {
        return new ResponseEntity<>(requestService.createDraftRequest(requestDto, getUser()), HttpStatus.CREATED);
    }

    @GetMapping("user/requests")
    public ResponseEntity<RequestResponse> getUserRequests(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "isAsc", defaultValue = "true", required = false) boolean isAsc
    ) {
        return new ResponseEntity<>(requestService.getUserRequests(getUsername(), pageNo, isAsc), HttpStatus.OK);
    }

    @PostMapping("user/edit")
    public ResponseEntity<RequestDto> editDraft(@RequestBody UpdateRequestDto updateRequestDto) {
        return new ResponseEntity<>(requestService.updateRequest(updateRequestDto, getUser()), HttpStatus.OK);
    }

    @GetMapping("user/sendToOperator")
    public ResponseEntity<RequestDto> sendToOperator(
            @RequestParam(value = "requestId") int requestId
    ) {
        return new ResponseEntity<>(requestService.sendToOperator(requestId, getUser()), HttpStatus.OK);
    }

    @GetMapping("operator/get/all")
    public ResponseEntity<RequestResponse> getAllRequests(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "isAsc", defaultValue = "true", required = false) boolean isAsc
    ) {
        return new ResponseEntity<>(requestService.getAllRequests(pageNo, isAsc), HttpStatus.OK);
    }

    @GetMapping("operator/get/{requestId}")
    public ResponseEntity<RequestDto> getRequestById(@PathVariable int requestId) {
        return new ResponseEntity<>(requestService.getRequestById(requestId), HttpStatus.OK);
    }

    @GetMapping("operator/getByUser/{username}")
    public ResponseEntity<RequestResponse> getRequestByUsername(
            @PathVariable String username,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "isAsc", defaultValue = "true", required = false) boolean isAsc
    ) {
        return new ResponseEntity<>(requestService.getLikeUserRequests(username, pageNo, isAsc), HttpStatus.OK);
    }

    @GetMapping("admin/getByStatus")
    public ResponseEntity<RequestResponse> getRequestByStatus(
            @RequestParam(value = "status") String status,
            @RequestParam(value = "username", defaultValue = "", required = false) String username,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "isAsc", defaultValue = "true", required = false) boolean isAsc
    ) {
        return new ResponseEntity<>(requestService.getByStatusRequests(status, username, pageNo, isAsc), HttpStatus.OK);
    }

    @GetMapping("operator/accept/{requestId}")
    public ResponseEntity<RequestDto> acceptRequestById(@PathVariable int requestId) {
        return new ResponseEntity<>(requestService.acceptRequestById(requestId), HttpStatus.OK);
    }

    @GetMapping("operator/decline/{requestId}")
    public ResponseEntity<RequestDto> declineRequestById(@PathVariable int requestId) {
        return new ResponseEntity<>(requestService.declineRequestById(requestId), HttpStatus.OK);
    }
}
