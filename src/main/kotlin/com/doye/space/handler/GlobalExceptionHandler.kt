package com.doye.space.handler

import com.doye.space.dto.ResponseDTO
import com.doye.space.dto.error.ErrorDTO
import com.doye.space.dto.error.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(e : MethodArgumentNotValidException) : ResponseEntity<ResponseDTO> {
        val errorResponse = ErrorResponse()
        
        for (fieldError: FieldError in e.bindingResult.fieldErrors) {
            errorResponse.errors.plus(ErrorDTO(fieldError.field, fieldError.defaultMessage))
        }
        return ResponseEntity.ok(ResponseDTO("99", "Validation Error", errorResponse))
    }
    
    @ExceptionHandler(BindException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBindException(e : BindException) : ResponseEntity<ResponseDTO> {
        val errorResponse = ErrorResponse()

        for (fieldError: FieldError in e.bindingResult.fieldErrors) {
            errorResponse.errors.plus(ErrorDTO(fieldError.field, fieldError.defaultMessage))
        }
        return ResponseEntity.ok(ResponseDTO("99", "Validation Error", errorResponse))
    }
}