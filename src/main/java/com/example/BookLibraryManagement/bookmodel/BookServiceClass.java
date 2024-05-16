package com.example.BookLibraryManagement.bookmodel;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceClass {

        private final BookRepository bookRepository;

        @Autowired
        public BookServiceClass(BookRepository bookRepository) {
            this.bookRepository = bookRepository;
        }

        public DataBookClass addBook(DataBookClass book) {
            if (bookRepository.existsByTitle(book.getTitle())) {
                throw new DuplicateBookException("A book with this title already exists.");
            }
            return bookRepository.save(book);

        }

        public List<DataBookClass> getAllBooks() {
            return bookRepository.findAll();

        }

        public Optional<DataBookClass> getBookById(Long id) {
            return bookRepository.findById(id);
        }

        public void deleteBookbyid(Long id) {
            bookRepository.deleteById(id);
        }
}
