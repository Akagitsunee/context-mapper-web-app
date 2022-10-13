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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.contextmapper.web.util.TestUtils.asJsonString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GeneratorControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        TestUtils.createTempFiles();
    }

    @Test
    public void givenGeneratorData_thenItProvidesJsonResponse() throws Exception {
        GenerateRequest req = new GenerateRequest(GeneratorType.CONTEXTMAP, TestUtils.cmlAsString());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/generate")
                        .content(asJsonString(req))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possibleExportFormats[0:3]", containsInAnyOrder("gv", "png", "svg")));
    }

    @Test
    public void givenGeneratorData_thenItThrows_400() throws Exception {
        GenerateRequest req = new GenerateRequest(GeneratorType.CONTEXTMAP, "Invalid string");

        this.mockMvc.perform(MockMvcRequestBuilders.post("/generate")
                        .content(asJsonString(req))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
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
