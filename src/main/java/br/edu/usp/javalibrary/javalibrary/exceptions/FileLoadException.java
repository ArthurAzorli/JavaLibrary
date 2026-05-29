package br.edu.usp.javalibrary.javalibrary.exceptions;

public class FileLoadException extends RuntimeException  {
    public FileLoadException(String filePath){
        super("Error on load data file from "+filePath);
    }
}
