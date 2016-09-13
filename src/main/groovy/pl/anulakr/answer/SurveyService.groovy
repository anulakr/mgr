package pl.anulakr.answer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import pl.anulakr.company.Company
import pl.anulakr.company.CompanyRepository
import pl.anulakr.question.QuestionRepository

@Component
@CompileStatic
class SurveyService {

    private final CompanyRepository companies
    private final QuestionRepository questions
    private final SurveyRepository surveys

    @Autowired
    SurveyService(CompanyRepository companies, QuestionRepository questions, SurveyRepository surveys) {
        this.surveys = surveys
        this.questions = questions
        this.companies = companies
    }

    Optional<String> post(String companyName, List<Answer> answers) {
        companies.findByNameIgnoreCase(companyName)
            .map({Company company -> surveys.save(new Survey(company: company.name, answers: answers))})
            .map({Survey survey -> survey.id})
    }

    String csv(String separator) {
        return surveys.findAll()
            .collect { it.csvRow(separator) }
            .join("\n")
    }

}