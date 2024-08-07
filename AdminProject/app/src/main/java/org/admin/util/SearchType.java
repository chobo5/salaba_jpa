package org.admin.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SearchType {
    NAME("0"),
    EMAIL("1");

    private final String value;
}
