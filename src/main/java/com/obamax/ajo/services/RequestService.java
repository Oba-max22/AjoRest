package com.obamax.ajo.services;

import com.obamax.ajo.dto.RequestDTO;
import com.obamax.ajo.models.Request;
import java.util.List;

public interface RequestService {
    Request makeRequest(RequestDTO request, Long cycleId, String email);
    List<Request> getAllCycleRequests(Long cycleId);
    List<Request> getAllMemberRequests(String email);
    void approveRequest(Long requestId);
}
