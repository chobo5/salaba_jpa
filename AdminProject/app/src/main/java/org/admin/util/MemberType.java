package org.admin.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType {
    MEMBER(0),
    HOST(1);

    private final int value;
}
