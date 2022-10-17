package org.contextmapper.web.service;

import org.contextmapper.dsl.generator.exception.NoContextMapDefinedException;
import org.contextmapper.web.models.GenerateResponse;
import org.contextmapper.web.util.TestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ContextMapperGeneratorServiceTest {

    private ContextMapperGeneratorService service;

    @BeforeEach
    public void setUp() {
        service = new ContextMapperGeneratorService();
        TestUtils.createTempFiles();
    }

    @Test
    public void generateContextMapperPNG_200() throws IOException {
        GenerateResponse res = service.generate(TestUtils.cmlAsString(), GeneratorType.CONTEXTMAP);

        Set<String> export = res.getPossibleExportFormats();
        assertTrue(export.contains("gv"));
        assertTrue(export.contains("png"));
        assertTrue(export.contains("svg"));
    }

    @Test
    public void generateContextMapperPNG_400() {
        String invalidCML = "BoundedContext{type: testtype}";
        Exception ex = assertThrows(NoContextMapDefinedException.class, () -> {
            service.generate(invalidCML, GeneratorType.CONTEXTMAP);
        });

        String expected = "No Context Map defined in this model. Please select a file which contains a context map.";
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
