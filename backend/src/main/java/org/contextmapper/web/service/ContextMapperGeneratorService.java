package org.contextmapper.web.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.contextmapper.dsl.cml.CMLResource;
import org.contextmapper.dsl.cml.exception.ResourceIsNoCMLModelException;
import org.contextmapper.dsl.generator.ContextMapGenerator;
import org.contextmapper.dsl.generator.exception.NoContextMapDefinedException;
import org.contextmapper.dsl.standalone.ContextMapperStandaloneSetup;
import org.contextmapper.dsl.standalone.StandaloneContextMapperAPI;
import org.contextmapper.web.models.GenerateResponse;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ContextMapperGeneratorService implements GeneratorService {
    private final StandaloneContextMapperAPI contextMapper;
    private final String DEFAULT_GEN_DIR_PATH = "./src-gen";
    private final String TEMP_DIR_PATH = "./tmp/GraphvizJava";
    private final File SRC_GEN_DIR = new File(DEFAULT_GEN_DIR_PATH);
    private final File TEMP_DIR = new File(TEMP_DIR_PATH);

    public ContextMapperGeneratorService() {
        this.contextMapper = ContextMapperStandaloneSetup.getStandaloneAPI();
    }

    @Override
    public GenerateResponse generate(String content, GeneratorType type) {
        try {
            // Clean tmp and src-gen dir
            Path tempPath = getPath(content);
            CMLResource resource = contextMapper.loadCML(tempPath.toFile());

            if (resource.getContextMappingModel().getMap() == null) {
                throw new NoContextMapDefinedException();
            }

            // Create the generator
            ContextMapGenerator generator = new ContextMapGenerator();

            // Generate the diagrams into 'src-gen'
            contextMapper.callGenerator(resource, generator);

            // Delete temp files and old files after generation
            cleanOldData(tempPath);

            return new GenerateResponse(getListOfPossibleExtensions());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ResourceIsNoCMLModelException re) {
            throw new ResourceIsNoCMLModelException();
        }

    }

    private Path getPath(String content) throws IOException {
        String prefix = "code";
        String suffix = ".cml";
        Path tempPath = Files.createTempFile(prefix, suffix);
        Files.writeString(tempPath, content);
        return tempPath;
    }

    private void cleanOldData(Path tempPath) throws IOException {
        // Delete files older than the newest 10
        // 10 as Buffer if the server isn't fast enough for now
        List<File> fileList = List.of(Objects.requireNonNull(SRC_GEN_DIR.listFiles()));
        Arrays.stream(Objects.requireNonNull(SRC_GEN_DIR.listFiles()))
                .sorted(Comparator.comparingLong(File::lastModified).reversed())
                .skip(10)
                .forEach(f -> {
                    try {
                        Files.deleteIfExists(f.toPath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

        FileUtils.cleanDirectory(TEMP_DIR);
        Files.delete(tempPath);
    }

    private Set<String> getListOfPossibleExtensions() {
        return Arrays.stream(Objects.requireNonNull(SRC_GEN_DIR.listFiles()))
                .map(file -> FilenameUtils.getExtension(file.getName()))
                .collect(Collectors.toSet());
    }
}
