package org.admin.repository;

import org.admin.domain.Qna;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QnaDao {
    List<Qna> findAllQna();
    Qna findBy(@Param("questionNo") long questionNo);

    void addAnswer(Qna qna);

    int updateState(Qna qna);

}
