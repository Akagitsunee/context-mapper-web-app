package org.contextmapper.web.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.contextmapper.dsl.cml.CMLResource;
import org.contextmapper.dsl.cml.exception.ResourceIsNoCMLModelException;
import org.contextmapper.dsl.generator.ContextMapGenerator;
import org.contextmapper.dsl.standalone.ContextMapperStandaloneSetup;
import org.contextmapper.dsl.standalone.StandaloneContextMapperAPI;
import org.contextmapper.web.models.GenerateResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ContextMapperGeneratorService implements  GeneratorService {
    private final StandaloneContextMapperAPI contextMapper;
    private final String DEFAULT_GEN_DIR = "./src-gen";
    private final String TEMP_DIR = "./tmp/GraphvizJava";
    private final File srcGenDir = new File(DEFAULT_GEN_DIR);
    private final File tmpDir = new File(TEMP_DIR);

    public ContextMapperGeneratorService() {
        this.contextMapper = ContextMapperStandaloneSetup.getStandaloneAPI();
    }

    @Override
    public GenerateResponse generate(String content, GeneratorType type) {
        try {
            // Clean tmp and src-gen dir
            cleanOldData();

            // Create the generator
            ContextMapGenerator generator = new ContextMapGenerator();

            Path tempPath = getPath(content);
            CMLResource resource = contextMapper.loadCML(tempPath.toFile());

            // Generate the diagrams into 'src-gen'
            contextMapper.callGenerator(resource, generator);

            // Delete file after generation
            Files.delete(tempPath);

            return new GenerateResponse(getListOfPossibleExtensions());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ResourceIsNoCMLModelException re) {
            throw new ResourceIsNoCMLModelException();
        }
    }

    private static Path getPath(String content) throws IOException {
        String prefix = "code";
        String suffix = ".cml";
        Path tempPath = Files.createTempFile(prefix, suffix);
        Files.writeString(tempPath, content);
        return tempPath;
    }

    private void cleanOldData() throws IOException {
        FileUtils.cleanDirectory(srcGenDir);
        FileUtils.cleanDirectory(tmpDir);
    }

    private List<String> getListOfPossibleExtensions() {
        return Arrays.stream(Objects.requireNonNull(srcGenDir.listFiles()))
                .map(file -> FilenameUtils.getExtension(file.getName()))
                .toList();
    }
}
