package io.reflectoring.coderadar.rest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetContributorResponse {
    private long id;
    private String name;
    private Set<String> emailAddresses;
}
