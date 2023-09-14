package com.pet.ques.dto;

import java.time.LocalDateTime;

import com.pet.ques.domain.QuesComment;

import lombok.Getter;
@Getter
public class QuesCommentListViewResponse {

    private final Long quesCmtNo;
    private final Long quesNo;
    private final String quesCmtContent;
    private final LocalDateTime quesCmtRdate;
    private final LocalDateTime quesCmtUdate;

    public QuesCommentListViewResponse(QuesComment quesComment) {
        this.quesNo = quesComment.getQuesBoard().getQuesNo();
        this.quesCmtNo = quesComment.getQuesCmtNo();
        this.quesCmtContent = quesComment.getQuesCmtContent();
        this.quesCmtRdate = quesComment.getQuesCmtRdate();
        this.quesCmtUdate = quesComment.getQuesCmtUdate();
    }
}

