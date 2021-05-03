package orcawatch.utils;

/**
 * provides an interface to assist parsing of different data file types
 */
public interface IFileReaderHelper {
    Object convertInput(String line);
}
