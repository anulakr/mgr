package pl.anulakr.answer

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.web.bind.annotation.RequestMethod.POST

@RestController
@RequestMapping(path = "/{company}/surveys")
@CompileStatic
class SurveyController {

    private final SurveyService service

    @Autowired
    SurveyController(SurveyService service) {
        this.service = service
    }

    @RequestMapping(method = POST, consumes = APPLICATION_JSON_VALUE)
    void post(@PathVariable("company") String company, @RequestBody List<Answer> answers) {
        service.post(company, answers)
    }
}
