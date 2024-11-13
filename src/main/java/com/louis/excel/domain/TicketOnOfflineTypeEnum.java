package com.louis.excel.domain;

import lombok.Getter;

import java.util.Objects;

public enum TicketOnOfflineTypeEnum {

    //0:Virtual 1:In-Person 2:Hybrid 100:All

    VIRTUAL(0),
    IN_PERSON(1),
    HYBRID(2),
    ALL(100),
    ;

    @Getter
    private int code;

    TicketOnOfflineTypeEnum(int code) {
        this.code = code;
    }

    public static TicketOnOfflineTypeEnum fromCode(int code) {
        for (TicketOnOfflineTypeEnum item : TicketOnOfflineTypeEnum.values()) {
            if (Objects.equals(item.code, code)) {
                return item;
            }
        }
        return null;
    }
}
