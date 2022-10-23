package org.contextmapper.web.models;

import org.contextmapper.web.service.GeneratorType;

public class GenerateRequest {
    private GeneratorType generatorType;
    private String code;

    public GenerateRequest() {
    }

    public GenerateRequest(GeneratorType generatorType, String code) {
        this.generatorType = generatorType;
        this.code = code;
    }

    public GeneratorType getGeneratorType() {
        return generatorType;
    }

    public String getCode() {
        return code;
    }
}
