package com.github.venkateshamurthy.designpatterns.builders.lombok;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Builder;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class Example {
    private int foo;
    private String bar;
}
