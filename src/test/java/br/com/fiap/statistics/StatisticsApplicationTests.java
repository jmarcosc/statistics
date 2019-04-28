package br.com.fiap.statistics;

import br.com.fiap.statistics.Controller.TransactionController;
import br.com.fiap.statistics.Entity.ErrorHandler;
import br.com.fiap.statistics.Entity.Statistic;
import br.com.fiap.statistics.Entity.Transaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
public class StatisticsApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private Transaction transaction;
	private Statistic statistic;
	private ErrorHandler errorHandler;

	@Test
	public void registerValidTransaction() throws Exception {

		transaction = new Transaction();
		transaction.setTimestamp(System.currentTimeMillis());
		transaction.setAmount(50.0);

		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String json = mapper.writeValueAsString(transaction);

		mvc.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());

	}

	@Test
	public void retrieveStatistiscs() throws Exception {

		transaction = new Transaction();
		transaction.setTimestamp(System.currentTimeMillis());
		transaction.setAmount(50.0);

		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String json = mapper.writeValueAsString(transaction);

		mvc.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isCreated());

		mvc.perform(get("/statistics")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	public void invalidTransaction() throws Exception {

		transaction = new Transaction();
		transaction.setTimestamp(1556472845);
		transaction.setAmount(50.0);

		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String json = mapper.writeValueAsString(transaction);

		mvc.perform(post("/transactions")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(json))
				.andExpect(status().isNoContent());

	}

}
