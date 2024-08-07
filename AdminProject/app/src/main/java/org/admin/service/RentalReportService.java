package org.admin.service;

import org.admin.domain.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RentalReportService {
    public List<Report> getAll();
    public Report get(long rentalNo, long memberNo);

    public int updateState(long rentalNo, long memberNo);
}
