package org.contextmapper.web.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ExportService {

    private final String DEFAULT_GEN_DIR = "./src-gen";
    File srcGenDir = new File(DEFAULT_GEN_DIR);

    public Resource exportAsResource(String extension) throws IOException {
        File file = getFileByExtension(extension);

        if (file != null) {
            return new UrlResource(file.toURI());
        }

        return null;
    }

    private File getFileByExtension(String extension) throws IOException {
        List<File> files = Arrays.stream(Objects.requireNonNull(srcGenDir.listFiles()))
                .filter(f -> f.getName()
                        .contains(extension))
                .toList();

        if (files.size() <= 0) {
            throw new FileNotFoundException("No such file existing!");
        }
        else if (files.size() > 1) {
            throw new IOException("Too many files found!");
        }

        return files.get(0);
    }
}
