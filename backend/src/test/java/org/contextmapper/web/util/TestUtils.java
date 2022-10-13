package org.contextmapper.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class TestUtils {

    private static final String DEFAULT_GEN_DIR = "./src-gen";
    private static final String TEMP_DIR_GRAPHVIZ = "./tmp/GraphvizJava";
    private static final String TEMP_DIR = "./tmp";
    private static final File srcGenDir = new File(DEFAULT_GEN_DIR);
    private static final File tmpDirGraphviz = new File(TEMP_DIR_GRAPHVIZ);
    private static final File tmpDir = new File(TEMP_DIR);

    public static void createTempFiles() {
        File tmp = new File(TEMP_DIR_GRAPHVIZ);
        tmp.mkdirs();
        File srcGen = new File(DEFAULT_GEN_DIR);
        srcGen.mkdirs();
    }

    public static void cleanOldData() throws IOException {
        FileUtils.cleanDirectory(srcGenDir);
        FileUtils.cleanDirectory(tmpDirGraphviz);
    }

    public static void deleteDirectories() throws IOException {
        FileUtils.deleteDirectory(srcGenDir);
        FileUtils.deleteDirectory(tmpDir);
    }

    public static String cmlAsString() throws IOException {
        String resourceName = "Insurance-Example-Model.cml";

        ClassLoader classLoader = TestUtils.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
