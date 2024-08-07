package org.admin.service;

import org.admin.domain.Qna;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface QnaService {
    List<Qna> getAllQna();
    Qna getBy(long qnaNo);

    void addAnswer(Qna qna);

}
