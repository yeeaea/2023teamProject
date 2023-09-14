package com.pet.free.dto;

import java.time.LocalDateTime;

import com.pet.free.domain.FreeComment;

import lombok.Getter;
@Getter
public class FreeCommentListViewResponse {

    private final Long freeCmtNo;
    private final Long freeNo;
    private final String freeCmtContent;
    private final LocalDateTime freeCmtRdate;
    private final LocalDateTime freeCmtUdate;

    public FreeCommentListViewResponse(FreeComment freeComment) {
        this.freeNo = freeComment.getFreeBoard().getFreeNo();
        this.freeCmtNo = freeComment.getFreeCmtNo();
        this.freeCmtContent = freeComment.getFreeCmtContent();
        this.freeCmtRdate = freeComment.getFreeCmtRdate();
        this.freeCmtUdate = freeComment.getFreeCmtRdate();
    }
}

