package org.admin.service;

import org.admin.domain.Report;

import java.util.List;

public interface TextReportService {
    public List<Report> getAllBy(String type);

    public Report getBy(String type, long targetNo, long memberNo);

    public int updateState(long reportNo);

    public int updateBoardState(Report report, String boardState);
}
