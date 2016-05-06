package org.wickedsource.coderadar.analyzer.api;

import java.io.File;
import java.util.List;

/**
 * Interface for analyzer plugins that analyze Java bytecode files.
 */
public interface ByteCodeAnalyzerPlugin {

    /**
     * Analyzes a set of bytecode files.
     *
     * @param bytecodeFiles the files to analyze
     * @return a set of metric values calculated for each of the given files.
     */
    FileSetMetrics analyzeBytecodeFiles(List<File> bytecodeFiles) throws AnalyzerException;

}
