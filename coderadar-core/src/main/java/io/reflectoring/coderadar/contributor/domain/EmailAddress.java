package io.reflectoring.coderadar.contributor.domain;

import lombok.Data;

@Data
public class EmailAddress {
    private String value;

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof EmailAddress)) return false;
        return value.equalsIgnoreCase(((EmailAddress) o).getValue());
    }

    @Override
    public int hashCode() {
        int result = 17;
        int c = value.toLowerCase().hashCode();
        result = 31 * result + c;
        return result;
    }
}
