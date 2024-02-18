package br.com.spring.integrationtests.controller.withyaml;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import br.com.spring.configs.TestConfigs;
import br.com.spring.data.vo.v1.security.TokenVO;
import br.com.spring.integrationtests.controller.withyaml.mapper.YMLMapper;
import br.com.spring.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.spring.integrationtests.vo.AccountCredentialsVO;
import br.com.spring.integrationtests.vo.PersonVO;
import br.com.spring.integrationtests.vo.pagedModels.PagedModelPerson;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class PersonControllerYamlTest extends AbstractIntegrationTest {

	private static RequestSpecification specification;
	private static YMLMapper objectMapper;
	
	private static PersonVO person;
	
	@BeforeAll
	public static void setup() {
		objectMapper = new YMLMapper();
		
		person = new PersonVO();
	}
	
	@Test
	@Order(0)
	public void authorization() throws JsonMappingException, JsonProcessingException {
		AccountCredentialsVO user = new AccountCredentialsVO("leandro", "admin123");
		
		var accessToken = given()
				.config(
					RestAssuredConfig.
						config()
						.encoderConfig(
							EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, 
									ContentType.TEXT)))
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_YAML)
					.accept(TestConfigs.CONTENT_TYPE_YAML)
				.body(user, objectMapper)
					.when()
				.post()
				.then()
					.statusCode(200)
					.extract()
					.body()
						.as(TokenVO.class, objectMapper)
					.getAccessToken();
		
		specification = new RequestSpecBuilder()
				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}
	
	@Test
	@Order(1)
	public void testCreate() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		var content = given().spec(specification)
				.config(
						RestAssuredConfig
							.config()
							.encoderConfig(EncoderConfig.encoderConfig()
								.encodeContentTypeAs(
									TestConfigs.CONTENT_TYPE_YAML,
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YAML)
				.accept(TestConfigs.CONTENT_TYPE_YAML)
					.body(person, objectMapper)
					.when()
					.post()
				.then()
					.statusCode(201)
						.extract()
						.body()
							.as(PersonVO.class, objectMapper);
		
		PersonVO createdPerson = content;
		person = createdPerson;
		
		assertNotNull(createdPerson);
		
		assertNotNull(createdPerson.getId());
		assertNotNull(createdPerson.getFirstName());
		assertNotNull(createdPerson.getLastName());
		assertNotNull(createdPerson.getAddress());
		assertNotNull(createdPerson.getGender());
		assertTrue(createdPerson.getEnabled());


		assertTrue(createdPerson.getId() > 0);
		
		assertEquals("Nelson", createdPerson.getFirstName());
		assertEquals("Piquet", createdPerson.getLastName());
		assertEquals("Brasília - DF", createdPerson.getAddress());
		assertEquals("Masculino", createdPerson.getGender());
	}
	
	@Test
	@Order(2)
	public void testUpdate() throws JsonMappingException, JsonProcessingException {
		person.setLastName("Piquet update");
		
		var content = given().spec(specification)
				.config(
					RestAssuredConfig.
						config()
						.encoderConfig(
							EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, 
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YAML)
				.accept(TestConfigs.CONTENT_TYPE_YAML)
					.body(person, objectMapper)
						.when()
					.post()
				.then()
					.statusCode(201)
				.extract()
					.body()
						.as(PersonVO.class, objectMapper);
		
		PersonVO persistedPerson = content;
		person = persistedPerson;
		
		assertNotNull(persistedPerson);
		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertTrue(persistedPerson.getEnabled());

		assertEquals(person.getId(), persistedPerson.getId());
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet update", persistedPerson.getLastName());
		assertEquals("Brasília - DF", persistedPerson.getAddress());
		assertEquals("Masculino", persistedPerson.getGender());
	}
	
	@Test
	@Order(3)
	public void testDisablePersonById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		
		var content = given().spec(specification)
				.config(
					RestAssuredConfig.
						config()
						.encoderConfig(
							EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, 
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YAML)
				.accept(TestConfigs.CONTENT_TYPE_YAML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
					.pathParam("id", person.getId())
					.when()
					.patch("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PersonVO.class, objectMapper);
		
		PersonVO persistedPerson = content;
		person = persistedPerson;
		
		assertEquals(person.getId(), persistedPerson.getId());

		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet update", persistedPerson.getLastName());
		assertEquals("Brasília - DF", persistedPerson.getAddress());
		assertEquals("Masculino", persistedPerson.getGender());
	}
	
	@Test
	@Order(4)
	public void testFindById() throws JsonMappingException, JsonProcessingException {
		mockPerson();
		
		
		var content = given().spec(specification)
				.config(
					RestAssuredConfig.
						config()
						.encoderConfig(
							EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, 
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YAML)
				.accept(TestConfigs.CONTENT_TYPE_YAML)
					.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
					.pathParam("id", person.getId())
					.when()
					.get("{id}")
				.then()
					.statusCode(200)
						.extract()
						.body()
							.as(PersonVO.class, objectMapper);
		
		PersonVO persistedPerson = content;
		person = persistedPerson;
		
		assertEquals(person.getId(), persistedPerson.getId());

		
		assertNotNull(persistedPerson.getId());
		assertNotNull(persistedPerson.getFirstName());
		assertNotNull(persistedPerson.getLastName());
		assertNotNull(persistedPerson.getAddress());
		assertNotNull(persistedPerson.getGender());
		assertFalse(persistedPerson.getEnabled());
		
		assertTrue(persistedPerson.getId() > 0);
		
		assertEquals("Nelson", persistedPerson.getFirstName());
		assertEquals("Piquet update", persistedPerson.getLastName());
		assertEquals("Brasília - DF", persistedPerson.getAddress());
		assertEquals("Masculino", persistedPerson.getGender());
	}
	
	@Test
	@Order(5)
	public void testDelete() throws JsonMappingException, JsonProcessingException {
		
		given().spec(specification)
			.contentType(TestConfigs.CONTENT_TYPE_XML)
				.pathParam("id", person.getId())
				.when()
				.delete("{id}")
			.then()
				.statusCode(204);
	}
	
	@Test
	@Order(6)
	public void testFindAll() throws JsonMappingException, JsonProcessingException {
		
		var wrapper = given().spec(specification)
				.config(
					RestAssuredConfig.
						config()
						.encoderConfig(
							EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, 
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YAML)
				.accept(TestConfigs.CONTENT_TYPE_YAML)
				.queryParams("page", 3, "size", 10, "diection", "asc")
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.as(PagedModelPerson.class, objectMapper);
					//.as(new TypeRef<List<PersonVO>>() {});
		
		var people = wrapper.getContent();
		
		PersonVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());

		assertEquals(224, foundPersonOne.getId());
		
		assertEquals("Alisun", foundPersonOne.getFirstName());
		assertEquals("Pettipher", foundPersonOne.getLastName());
		assertEquals("6 Hallows Court", foundPersonOne.getAddress());
		assertEquals("Female", foundPersonOne.getGender());

		PersonVO foundPersonFive = people.get(4);
		
		assertNotNull(foundPersonFive.getId());
		assertNotNull(foundPersonFive.getFirstName());
		assertNotNull(foundPersonFive.getLastName());
		assertNotNull(foundPersonFive.getAddress());
		assertNotNull(foundPersonFive.getGender());
		
		assertEquals(422, foundPersonFive.getId());
		
		assertEquals("Allyson", foundPersonFive.getFirstName());
		assertEquals("Garrie", foundPersonFive.getLastName());
		assertEquals("29 Carberry Pass", foundPersonFive.getAddress());
		assertEquals("Female", foundPersonFive.getGender());
	}
	
	@Test
	@Order(7)
	public void testFindAllWithoutToken() throws JsonMappingException, JsonProcessingException {
		
		RequestSpecification specificationWithoutToken = new RequestSpecBuilder()
				.setBasePath("/api/person/v1")
				.setPort(TestConfigs.SERVER_PORT)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();;
				
		given().spec(specificationWithoutToken)
			.config(
				RestAssuredConfig.
					config()
					.encoderConfig(
						EncoderConfig.encoderConfig()
							.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, 
								ContentType.TEXT)))
			.contentType(TestConfigs.CONTENT_TYPE_YAML)
			.accept(TestConfigs.CONTENT_TYPE_YAML)
				.when()
				.get()
			.then()
				.statusCode(403)
			.extract()
				.body()
					.asString();
	}

	@Test
	@Order(6)
	public void testFindByName() throws JsonMappingException, JsonProcessingException {
		
		var wrapper = given().spec(specification)
				.config(
					RestAssuredConfig.
						config()
						.encoderConfig(
							EncoderConfig.encoderConfig()
								.encodeContentTypeAs(TestConfigs.CONTENT_TYPE_YAML, 
									ContentType.TEXT)))
				.contentType(TestConfigs.CONTENT_TYPE_YAML)
				.accept(TestConfigs.CONTENT_TYPE_YAML)
				.pathParam("firstName", "ana")
				.queryParams("page", 0, "size", 6, "diection", "asc")
				.when()
					.get("findPersonByName/{firstName}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.as(PagedModelPerson.class, objectMapper);
					//.as(new TypeRef<List<PersonVO>>() {});
		
		var people = wrapper.getContent();
		
		PersonVO foundPersonOne = people.get(0);
		
		assertNotNull(foundPersonOne.getId());
		assertNotNull(foundPersonOne.getFirstName());
		assertNotNull(foundPersonOne.getLastName());
		assertNotNull(foundPersonOne.getAddress());
		assertNotNull(foundPersonOne.getGender());

		assertTrue(foundPersonOne.getEnabled());

		assertEquals(926, foundPersonOne.getId());
		
		assertEquals("Anastasia", foundPersonOne.getFirstName());
		assertEquals("Wiszniewski", foundPersonOne.getLastName());
		assertEquals("09 Pond Avenue", foundPersonOne.getAddress());
		assertEquals("Female", foundPersonOne.getGender());

	}

	@Test
	@Order(7)
	public void testHATEOAS() throws JsonMappingException, JsonProcessingException {
		
		var content = given().spec(specification)
				.contentType(TestConfigs.CONTENT_TYPE_YAML)
				.accept(TestConfigs.CONTENT_TYPE_YAML)
				.queryParams("page", 3, "size", 10, "direction", "asc")
				.when()
					.get()
				.then()
					.statusCode(200)
				.extract()
					.body()
					.asString();
		
		System.out.println(""
				+ ""
				+ ""
				+ ""
				+ ""
				+ content + ""
				+ ""
				+ ""
				+ "");
		
		assertTrue(content.contains("links:\n"
				+ "  - rel: \"self\"\n"
				+ "    href: \"http://localhost:8888/api/person/v1/679\"\n"
				+ "  links: []"));
		assertTrue(content.contains("links:\n"
				+ "  - rel: \"self\"\n"
				+ "    href: \"http://localhost:8888/api/person/v1/246\"\n"
				+ "  links: []"));
		assertTrue(content.contains("links:\n"
				+ "  - rel: \"self\"\n"
				+ "    href: \"http://localhost:8888/api/person/v1/528\"\n"
				+ "  links: []"));
		
		assertTrue(content.contains("page:\n"
				+ "  size: 10\n"
				+ "  totalElements: 1005\n"
				+ "  totalPages: 101\n"
				+ "  number: 3"));
		assertTrue(content.contains("- rel: \"last\"\n"
				+ "  href: \"http://localhost:8888/api/person/v1?direction=asc&page=100&size=10&sort=firstName,asc\""));
		
	}
	
	private void mockPerson() {
		person.setFirstName("Nelson");
		person.setLastName("Piquet");
		person.setAddress("Brasília - DF");
		person.setGender("Masculino");
		person.setEnabled(true);
	}

}
