package org.contextmapper.web.api;

import org.contextmapper.web.models.GenerateRequest;
import org.contextmapper.web.service.GeneratorType;
import org.contextmapper.web.util.TestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.contextmapper.web.util.TestUtils.asJsonString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ExportControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        TestUtils.createTempFiles();
    }

    @Test
    public void givenExportDataType_thenItProvidesJsonResponse() throws Exception {
        GenerateRequest req = new GenerateRequest(GeneratorType.CONTEXTMAP, TestUtils.cmlAsString());
        String extension = "png";

        this.mockMvc.perform(MockMvcRequestBuilders.post("/generate")
                        .content(asJsonString(req))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        MvcResult res = this.mockMvc.perform(MockMvcRequestBuilders.get(String.format("/export/%s", extension))
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(res.getResponse().getContentLength() > 0);
    }

    @Test
    public void givenExportDataType_thenItThrows_404() throws Exception {
        String extension = "psx";

        this.mockMvc.perform(MockMvcRequestBuilders.get(String.format("/export/%s", extension))
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(status().isNotFound());
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
