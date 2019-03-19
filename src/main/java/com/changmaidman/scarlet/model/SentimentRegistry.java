package com.changmaidman.scarlet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

@AllArgsConstructor
@Builder
@Data
public class SentimentRegistry {

    Class<?> clazz;
    Method method;
}
