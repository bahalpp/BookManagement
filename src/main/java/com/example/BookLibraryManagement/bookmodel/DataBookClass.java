package com.example.BookLibraryManagement.bookmodel;


import jakarta.persistence.*;
import lombok.*;


@Table(name = "Books")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity


public class DataBookClass {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private long id;
@Column
private String title;
@Column
private String author;

    public DataBookClass(String title, String author) {
        this.title = title;
        this.author = author;
    }
}


