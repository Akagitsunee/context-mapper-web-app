package org.contextmapper.web.models;

import java.util.List;

public class GenerateResponse {
    private List<String> possibleExportFormats;

    public GenerateResponse() {
    }

    public GenerateResponse(List<String> possibleExportFormats) {
        this.possibleExportFormats = possibleExportFormats;
    }

    public List<String> getPossibleExportFormats() {
        return possibleExportFormats;
    }
}
