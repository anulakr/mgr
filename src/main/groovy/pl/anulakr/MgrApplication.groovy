package pl.anulakr

import groovy.json.JsonSlurper
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import pl.anulakr.company.Company
import pl.anulakr.company.CompanyRepository
import pl.anulakr.question.Question
import pl.anulakr.question.QuestionRepository

@SpringBootApplication
@CompileStatic
class MgrApplication {

	static void main(String[] args) {
		SpringApplication.run MgrApplication, args
	}
}

@Component
class Initializer implements CommandLineRunner {

	private final ResourceLoader loader
	private final JsonSlurper json = new JsonSlurper()
	private final CompanyRepository companies
	private final QuestionRepository questions

	@Autowired
	Initializer(ResourceLoader loader, CompanyRepository companies, QuestionRepository questions) {
		this.loader = loader
		this.companies = companies
		this.questions = questions
	}

	@Override
	void run(String... strings) throws Exception {
		json
			.parse(loader.getResource("classpath:/companies.json").inputStream)
			.collect { new Company(it) }
			.findAll { !companies.findByNameIgnoreCase(it.name).isPresent() }
			.each { companies.save(it) }

		json
			.parse(loader.getResource("classpath:/questions.json").inputStream)
			.collect { new Question(it) }
			.findAll { !questions.findByLabelIgnoreCase(it.label).isPresent() }
			.each { questions.save(it) }
	}
}
