package com.jenia.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class NoAuthorityException extends RuntimeException {

    public NoAuthorityException() {
        super("You have no authority to make operation with post");
    }
}
