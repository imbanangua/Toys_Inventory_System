package ca.sheridancollege;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import ca.sheridancollege.beans.Toy;
import ca.sheridancollege.databases.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
class FinalExamYinanSongApplicationTests {
	
	@Autowired
	private DatabaseAccess da;
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLoadingHomePage() throws Exception {
		this.mockMvc.perform(get("/"))
			.andExpect(status().isOk()) //Status code 200
			.andExpect(view().name("home.html"));
	}
	
	@Test
	public void testLoadingLoginPage() throws Exception {
		this.mockMvc.perform(get("/login"))
			.andExpect(status().isOk()) //Status code 200
			.andExpect(view().name("login.html"));
	}
	
	@Test
	public void testViewToys() throws Exception {
		this.mockMvc.perform(get("/view"))
			.andExpect(status().isOk()) //Status code 200
			.andExpect(model().attributeExists("toyList"))  
			.andExpect(view().name("viewToys.html")); //return ...
	}
/*	
	@Test
	public void testLoadingEmail() throws Exception {
		this.mockMvc.perform(get("/sendEmailBoss"))
			.andExpect(status().isOk()) //Status code 200
			.andExpect(view().name("sendEmail.html")); //return ...
	}
*/
	
	@Test
	public void testAddToy() {
		ArrayList<Toy> toys = da.getToys();
		int initialSize = toys.size();
		da.addToy(new Toy());
		int newSize = da.getToys().size();
		assertThat(initialSize + 1).isEqualTo(newSize);
	}
	
	@Test
	public void testGetToyById() {
		Toy toy = da.getToyById(2);
		assertThat(toy).isNotEqualTo(null);
	}
	
	@Test
	public void testBadGetToyById() {
		Toy toy = da.getToyById(134);
		assertThat(toy).isEqualTo(null);
	}

}
