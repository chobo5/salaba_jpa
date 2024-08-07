package org.admin.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RentalType {
    WAITING_REG(0),
    REGISTERED(1);
    private final int value;
}
