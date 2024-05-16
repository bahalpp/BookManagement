package com.example.BookLibraryManagement.bookmodel;

public class DuplicateBookException extends RuntimeException {
    public DuplicateBookException(String title) {
        super("The book with title " + title + " already exists");
    }
}
