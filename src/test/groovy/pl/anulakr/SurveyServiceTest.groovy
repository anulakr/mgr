package pl.anulakr

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import pl.anulakr.answer.SurveyService
import spock.lang.Specification

@ContextConfiguration
@SpringBootTest
class SurveyServiceTest extends Specification {

    @Autowired
    SurveyService service

    def "does not save for unknown company"() {
        when:
            def result = service.post("test", [])
        then:
            !result.present
    }

    def "will save for known company"() {
        when:
            def result = service.post("impaq", [])
        then:
            result.present
    }
}
