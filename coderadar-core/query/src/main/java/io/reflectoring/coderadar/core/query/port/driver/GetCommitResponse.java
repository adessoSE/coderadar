package io.reflectoring.coderadar.core.query.port.driver;

import lombok.Data;

@Data
public class GetCommitResponse {
    private String name;
    private String author;
    private String timestamp;
    private Boolean analyzed;
}
