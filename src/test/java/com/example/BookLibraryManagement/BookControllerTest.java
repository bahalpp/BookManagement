package com.example.BookLibraryManagement;

import com.example.BookLibraryManagement.bookmodel.DataBookClass;
import com.example.BookLibraryManagement.bookmodel.BookRepository;
import com.example.BookLibraryManagement.bookmodel.RestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private BookRepository bookRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllBooks() throws Exception {
		List<DataBookClass> books = Arrays.asList(
				new DataBookClass("Book 1", "Author 1"),
				new DataBookClass("Book 2", "Author 2")
		);

		when(bookRepository.findAll()).thenReturn(books);

		mockMvc.perform(MockMvcRequestBuilders.get("/getAllBooks"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Book 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].author").value("Author 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].title").value("Book 2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].author").value("Author 2"));
	}


	@Test
	public void testGetBookById() throws Exception {
		DataBookClass book = new DataBookClass("Book 1", "Author 1");
		long bookId = 1;

		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

		mockMvc.perform(MockMvcRequestBuilders.get("/getBookById/{id}", bookId))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Book 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Author 1"));
	}

	@Test
	public void testAddBook() throws Exception {
		DataBookClass book = new DataBookClass("Test Title", "Test Author");

		when(bookRepository.save(any(DataBookClass.class))).thenReturn(book);

		mockMvc.perform(MockMvcRequestBuilders.post("/addBook")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"Test Title\",\"author\":\"Test Author\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Test Title"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Test Author"));
	}

	@Test
	public void testUpdateBookbyid() throws Exception {
		DataBookClass book = new DataBookClass("Book 1", "Author 1");
		long bookId = 1;

		when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

		mockMvc.perform(MockMvcRequestBuilders.post("/updateBookbyid/{id}", bookId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"title\":\"Updated Title\",\"author\":\"Updated Author\"}"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Updated Title"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.author").value("Updated Author"));
	}

	@Test
	public void testDeleteBookbyid() throws Exception {
		long bookId = 1;

		mockMvc.perform(MockMvcRequestBuilders.delete("/deleteBookbyid/{id}", bookId))
				.andExpect(MockMvcResultMatchers.status().isOk());

		Mockito.verify(bookRepository, Mockito.times(1)).deleteById(bookId);
	}

}
