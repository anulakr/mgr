package pl.anulakr.answer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@CompileStatic
class SurveyController {

    private final SurveyService service

    @Autowired
    SurveyController(SurveyService service) {
        this.service = service
    }

    @RequestMapping(path = "/surveys", method = GET, produces = "text/csv")
    String csv() {
        return service.csv(",")
    }

    @RequestMapping(path = "/{company}/surveys", method = POST, consumes = APPLICATION_JSON_VALUE)
    void create(@PathVariable("company") String company, @RequestBody Survey survey) {
        service.post(company, survey.answers)
    }
}
