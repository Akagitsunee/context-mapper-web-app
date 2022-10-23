package org.contextmapper.web.service;

import org.contextmapper.web.util.TestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class ExportServiceTest {

    private ExportService exportService;

    @BeforeEach
    public void setUp() {
        exportService = new ExportService();
        TestUtils.createTempFiles();
    }

    @Test
    public void exportAsPng_200() throws IOException {
        TestUtils.createTempData();
        String extension = "png";

        Resource png = exportService.exportAsResource(extension);

        // Get actual file from directory
        final String DEFAULT_GEN_DIR = "./src-gen";
        File srcGenDir = new File(DEFAULT_GEN_DIR);
        File generatedPng = Arrays.stream(Objects.requireNonNull(srcGenDir.listFiles()))
                .filter(f -> f.getName()
                        .contains(extension))
                .toList().get(0);

        assertEquals(generatedPng.length(), png.contentLength());
    }

    @Test
    public void exportAsPuml_404() {
        String extension = "puml";
        Exception ex = assertThrows(IOException.class, () -> {
            exportService.exportAsResource(extension);
        });

        String expected = "No such file existing!";
        assertEquals(ex.getMessage(), expected);
    }

    @AfterEach
    public void tearDown() throws Exception {
        TestUtils.cleanOldData();
    }

    @AfterAll
    public static void destroy() throws Exception {
        TestUtils.deleteDirectories();
    }
}
