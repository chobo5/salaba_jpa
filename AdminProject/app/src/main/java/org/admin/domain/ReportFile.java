package org.admin.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportFile {
    private long reportNo;
    private String originalName;
    private String uuidName;
}
