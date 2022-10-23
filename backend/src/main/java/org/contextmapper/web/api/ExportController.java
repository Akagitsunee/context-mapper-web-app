package org.contextmapper.web.api;

import io.swagger.v3.oas.annotations.Operation;
import org.contextmapper.web.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/export")
public class ExportController {

    private final ExportService service;

    @Autowired
    public ExportController(ExportService service) {
        this.service = service;
    }

    @GetMapping(path = "/{extension}")
    @Operation(summary = "Exports a file-format",
            description = "Exports the selected file-format and returns it to make it downloadable.")
    public ResponseEntity<Resource> export(@PathVariable String extension) throws IOException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(service.exportAsResource(extension));
    }
}
