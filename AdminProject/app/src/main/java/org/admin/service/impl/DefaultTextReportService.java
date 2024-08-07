package org.admin.service.impl;

import lombok.RequiredArgsConstructor;
import org.admin.domain.Report;
import org.admin.repository.TextReportDao;
import org.admin.service.TextReportService;
import org.springframework.stereotype.Service;
import org.admin.util.Translator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultTextReportService implements TextReportService {

    private final TextReportDao textReportDao;
    @Override
    public List<Report> getAllBy(String type) {
        List<Report> reports = textReportDao.findAllBy(type);
        for (Report report : reports) {
            report.setStateStr(Translator.dealState.get(report.getState()));
        }
        return reports;
    }

    @Override
    public Report getBy(String type, long no, long memberNo) {

        Report report = textReportDao.findBy(type,no,memberNo);
        report.setStateStr(Translator.dealState.get(report.getState()));
        return report;
    }

    @Override
    public int updateState(long reportNo) {
        return textReportDao.updateState(reportNo);
    }

    @Override
    public int updateBoardState(Report report, String boardState) {
        return textReportDao.updateBoardState(report, boardState);
    }
}
