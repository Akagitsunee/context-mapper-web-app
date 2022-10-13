package org.contextmapper.web.service;

import org.contextmapper.web.models.GenerateResponse;

public interface GeneratorService {

    /**
     * Generates a diagram with the chosen generator type
     *
     * @param content CML code segment
     * @param type Generator type can be one of the following:
     *     <li>CONTEXTMAP</li>
     *     <li>PLANTUML</li>
     *     <li>GENERIC</li>
     *     <li>MDSL</li>
     *
     * @return all possible export formats as JSON object
     */
    GenerateResponse generate(String content, GeneratorType type);
}
