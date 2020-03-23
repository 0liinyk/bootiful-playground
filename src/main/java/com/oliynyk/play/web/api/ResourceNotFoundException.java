package com.oliynyk.play.web.api;

import com.oliynyk.play.model.ApiException;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException() {
        super("Resource is not found", HttpStatus.NOT_FOUND);
    }
}
