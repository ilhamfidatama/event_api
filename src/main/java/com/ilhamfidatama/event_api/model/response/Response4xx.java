package com.ilhamfidatama.event_api.model.response;

import com.ilhamfidatama.event_api.model.Response;

public enum Response4xx {

    USERNOTAUTHORIZED("User is not authorized for this data.", Status.FAILED),
    DATANOTFOUND("Data not found.", Status.FAILED);

    public final Response<Object> body;

    Response4xx(String message, Status status){
        this.body = Response.builder().message(message).status(status.value).build();
    }

}

