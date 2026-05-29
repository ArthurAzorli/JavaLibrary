package br.edu.usp.javalibrary.javalibrary.exceptions;

public class FileSaveException extends RuntimeException  {
    public FileSaveException(String filePath){
        super("Error on save data file from "+filePath);
    }
}
