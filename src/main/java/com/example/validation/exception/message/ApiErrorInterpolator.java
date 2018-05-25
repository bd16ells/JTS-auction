package com.example.validation.exception.message;

import org.springframework.context.support.DefaultMessageSourceResolvable;

public interface ApiErrorInterpolator {

    String interpolate(DefaultMessageSourceResolvable e);
}
