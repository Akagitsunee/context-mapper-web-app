package org.contextmapper.web.api;

import io.swagger.v3.oas.annotations.Operation;
import org.contextmapper.web.models.GenerateRequest;
import org.contextmapper.web.models.GenerateResponse;
import org.contextmapper.web.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/generate")
public class GeneratorController {
    private final GeneratorService service;

    @Autowired
    public GeneratorController(GeneratorService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Generates a diagram and return it",
            description = "Generates a diagram and return the file format corresponding to the generator.")
    public ResponseEntity<GenerateResponse> generate(@RequestBody @Valid GenerateRequest request) {
        return ResponseEntity.ok(
                service.generate(request.getCode(), request.getGeneratorType()));
    }

}
