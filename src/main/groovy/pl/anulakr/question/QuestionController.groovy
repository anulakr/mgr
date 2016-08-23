package pl.anulakr.question

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping(path = "/questions")
@CompileStatic
class QuestionController {

    private final QuestionRepository repository

    @Autowired
    QuestionController(QuestionRepository repository) {
        this.repository = repository
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    List<Question> findAll() {
        return repository.findAll()
    }
}
