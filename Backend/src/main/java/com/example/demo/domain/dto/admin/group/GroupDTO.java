package com.example.demo.domain.dto.admin.group;

import java.time.Instant;

public record GroupDTO(
        String groupName,
        String groupCode,
        Instant createdDate,
        String createdBy
) {

}
