package pt.sardoalware.gabrikid.hardcoreadventureblog.dto;

import java.util.List;
import java.util.Map;

public record ErrorDto(String errorMessage, Map<String, List<String>> fieldErrorsMessages) {

    public ErrorDto(String errorMessage) {
        this(errorMessage, null);
    }

    public ErrorDto(Map<String, List<String>> fieldErrorsMessages) {
        this(null, fieldErrorsMessages);
    }

}