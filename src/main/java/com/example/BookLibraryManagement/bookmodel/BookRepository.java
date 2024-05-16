package com.example.BookLibraryManagement.bookmodel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<DataBookClass, Long> {
    boolean existsByTitle(String title);
}
