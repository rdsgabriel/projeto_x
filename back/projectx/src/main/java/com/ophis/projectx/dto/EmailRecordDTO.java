package com.ophis.projectx.dto;

public record EmailRecordDTO(Long id,
                             String emailTo,
                             String subject,
                             String text) {
}
