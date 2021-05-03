package orcawatch.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {
    public static List<Object> readInput(String filename, IFileReaderHelper dataHelper) {
        List<Object> list = null;

        try {
            Path path = Paths.get(ClassLoader.getSystemResource(filename).toURI());

            Stream<String> stream = Files.lines(path);
            list = stream
                    .filter(line -> StringUtils.isNotBlank(line) && !line.startsWith("#"))
                    .map(line -> dataHelper.convertInput(line))
                    .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            System.out.println(e);
        }

        return list;
    }

    public static String[] splitLine(String line) {
        return StringUtils.split(line, StringUtils.defaultString(","));
    }
}
