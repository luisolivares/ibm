package com.ibm.domain.person.model.response;

import com.ibm.exceptions.ApiError;

public record ResponseApiDTO<T>(T data,
                                 ApiError error) {
}