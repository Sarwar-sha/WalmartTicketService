package sarwar.sha.restfultest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
// import javax.annotation.Resource;
import javax.sql.DataSource;

import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.walmart.tikectservice.Application;
import com.walmart.tikectservice.conttroller.Controller;
import com.walmart.tikectservice.domain.Seathold;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { Application.class })
@WebAppConfiguration
public class RestfulTest {

	@Autowired
	private Controller controller;

	private MockMvc mockMvc;

	@Autowired
	private DataSource dataSource;

	@Before
	public void setUp() throws Exception {

		IDataSet dataSet = new FlatXmlDataSetBuilder()
				.build(new File("C:/Users/ssha/workspace/walmart/src/main/resources/dataSet.xml"));
		IDatabaseConnection dbConn = new DatabaseDataSourceConnection(dataSource);
		DatabaseOperation.CLEAN_INSERT.execute(dbConn, dataSet);
	}

	@PostConstruct
	public void setup() {

		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setMessageConverters(new MappingJackson2HttpMessageConverter(), new StringHttpMessageConverter())
				.build();

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void numSeatsAvailable() throws Exception {

		mockMvc.perform(get("/numSeatsAvailable?levelNum={levelNum}", 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").value(is(2)));
		mockMvc.perform(get("/numSeatsAvailable?levelNum=").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$").value(is(3)));
	}

	@Test
	public void findAndHoldSeatsTest() throws Exception {
		
        mockMvc.perform(
	get("/findAndHoldSeats?numSeats={numSeats}&customerEmail={customerEmail}&minLevel=&maxLevel=", 1,"ryan.lee@example.com")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.levelId").value(is(1)))
				.andExpect(jsonPath("$.levelName").value(is("Orchestra")))
				.andExpect(jsonPath("$.price").value(is("$100.00")))
				.andExpect(jsonPath("$.rowNum").value(is(6)))
				.andExpect(jsonPath("$.phoneNumber").value(is("212-232-5698")))
				.andExpect(jsonPath("$.emailAddress").value(is("ryan.lee@example.com")));
        
        mockMvc.perform(
        		get("/findAndHoldSeats?numSeats={numSeats}&customerEmail={customerEmail}&minLevel={minLevel}&maxLevel={maxLevel}", 7,"ryan.lee@example.com",1,4)
        					.accept(MediaType.APPLICATION_JSON))
        					.andExpect(status().isOk())
        					.andExpect(jsonPath("$.levelId").value(is(1)))
        					.andExpect(jsonPath("$.levelName").value(is("Orchestra")))
        					.andExpect(jsonPath("$.price").value(is("$100.00")))
        					.andExpect(jsonPath("$.rowNum").value(is(6)))
        					.andExpect(jsonPath("$.phoneNumber").value(is("212-232-5698")))
        					.andExpect(jsonPath("$.emailAddress").value(is("ryan.lee@example.com")));
	}
	
	@Test
	public void reserveSeatsTest() throws Exception{
		
		mockMvc.perform(
        		get("/reserveSeats?numSeats={numSeats}&customerEmail={customerEmail}", 23,"sanjay.mehta@example.com")
        					.accept(MediaType.APPLICATION_JSON))
        					.andExpect(status().isOk())
        					.andExpect(jsonPath("$").value(is("1, Orchestra, $100.00, 23")));
		
	}

}
