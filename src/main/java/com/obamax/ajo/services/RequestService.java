package com.obamax.ajo.services;

import com.obamax.ajo.dto.RequestDTO;
import com.obamax.ajo.models.Request;

public interface RequestService {
    Request makeRequest(RequestDTO request, Long cycleId, String email);
}
