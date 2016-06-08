package org.wickedsource.coderadar.analyzer.match;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class FileWalker {

    private Logger logger = LoggerFactory.getLogger(FileWalker.class);

    private AntPathMatcher matcher = new AntPathMatcher();

    public void walk(final FileMatchingPattern pattern) throws IOException {
        Files.walkFileTree(pattern.getStartFolder(), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                // TODO: we might skip whole folders that don't match the ant pattern (minus the filename part)
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (matches(pattern, file)) {
                    processFile(file);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                logger.warn("skipping file {} because of IOException: {}", file, exc);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private boolean matches(FileMatchingPattern pattern, Path file) {
        boolean match = false;

        // we have a match if at least one include pattern matches
        for (String includePattern : pattern.getIncludePatterns()) {
            String relativeFilePath = relativize(pattern.getStartFolder(), file);
            if (matcher.match(includePattern, relativeFilePath)) {
                match = true;
                break;
            }
        }

        // if we have a matching exclude pattern, we don't have a match
        if (match) {
            for (String excludePattern : pattern.getExcludePatterns()) {
                String relativeFilePath = relativize(pattern.getStartFolder(), file);
                if (matcher.match(excludePattern, relativeFilePath)) {
                    match = false;
                    break;
                }
            }
        }

        return match;

    }

    private String relativize(Path startFolder, Path file) {
        String relativePath = startFolder.relativize(file).toString();
        return relativePath.replaceAll("\\\\", "/");
    }

    public abstract void processFile(Path file);

}
