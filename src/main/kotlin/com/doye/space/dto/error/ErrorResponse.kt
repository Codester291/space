package com.doye.space.dto.error

data class ErrorResponse(
    var errors: List<ErrorDTO> = mutableListOf()
)