package com.test.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Tuple<T> {

    T first;
    T second;

}
