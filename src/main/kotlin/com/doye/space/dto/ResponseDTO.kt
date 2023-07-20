package com.doye.space.dto

import com.fasterxml.jackson.annotation.JsonInclude

class ResponseDTO (
    var code: String,
    var message: String,
    var data: Any?
) {
    constructor(code: String, message: String) : this(code, message, null)
    constructor() : this("", "", "")
}