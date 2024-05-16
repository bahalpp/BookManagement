package com.example.BookLibraryManagement.bookmodel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@org.springframework.web.bind.annotation.RestController
public class RestController {
    @Autowired
    private final BookRepository BookRepository;

    public RestController(BookRepository BookRepository) {
        this.BookRepository = BookRepository;
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<DataBookClass>> getAllBooks(){
            try {
                List<DataBookClass> booklist = new ArrayList<>();
                BookRepository.findAll().forEach(booklist::add);

                if (booklist.isEmpty()){

                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(booklist, HttpStatus.OK);
            }
            catch (Exception ex){
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }

    }

    @GetMapping("/getBookById/{id}")
    public ResponseEntity<DataBookClass> getBookById(@PathVariable long id){
        Optional<DataBookClass> bookdata = BookRepository.findById(id);

        if (bookdata.isPresent()){
            return new ResponseEntity<>(bookdata.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public ResponseEntity<DataBookClass> addBook(@RequestBody DataBookClass book){

        DataBookClass bookobj = BookRepository.save(book);
        return new ResponseEntity<>(bookobj,HttpStatus.OK);

    }
    @PostMapping("/updateBookbyid/{id}")
    public ResponseEntity<DataBookClass> updateBookbyid(@PathVariable Long id , @RequestBody DataBookClass newbookdata){

        Optional<DataBookClass> oldbookdata = BookRepository.findById(id);

        if (oldbookdata.isPresent()){
            DataBookClass updatedbook = oldbookdata.get();
            updatedbook.setTitle(newbookdata.getTitle());
            updatedbook.setAuthor(newbookdata.getAuthor());

            DataBookClass bookobj=    BookRepository.save(updatedbook);
            return new ResponseEntity<>(bookobj,HttpStatus.OK);

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/deleteBookbyid/{id}")
    public ResponseEntity<Object> deleteBookbyid(@PathVariable long id){

        BookRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
