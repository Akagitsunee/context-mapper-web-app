package org.contextmapper.web.models;

import java.util.Set;

public class GenerateResponse {
    private Set<String> possibleExportFormats;

    public GenerateResponse() {
    }

    public GenerateResponse(Set<String> possibleExportFormats) {
        this.possibleExportFormats = possibleExportFormats;
    }

    public Set<String> getPossibleExportFormats() {
        return possibleExportFormats;
    }
}
