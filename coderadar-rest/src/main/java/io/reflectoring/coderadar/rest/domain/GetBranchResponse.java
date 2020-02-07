package io.reflectoring.coderadar.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GetBranchResponse {
    private long id;
    private String name;
}
