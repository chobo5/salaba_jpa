package org.admin.repository;

import org.admin.domain.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RentalReportDao {
    public List<Report> findAll();

    public Report findBy(@Param("rentalNo") long rentalNo,
                         @Param("memberNo") long memberNo);

    public int updateState(@Param("rentalNo") long rentalNo,
                         @Param("memberNo") long memberNo);

}
