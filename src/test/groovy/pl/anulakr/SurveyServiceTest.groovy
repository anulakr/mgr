package pl.anulakr

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import pl.anulakr.answer.Answer
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

    @DirtiesContext
    def "will save for known company"() {
        when:
            def result = service.post("impaq", [])
        then:
            result.present
    }

    @DirtiesContext
    def "will return csv"() {
        given:
            service.post("IMPAQ", [
                new Answer(question: "1", current: ["A": 10, "B": 20, "C": 30, "D": 40], expected: ["A": 40, "B": 30, "C": 20, "D": 10]),
                new Answer(question: "2", current: ["A": 20, "B": 30, "C": 40, "D": 10], expected: ["A": 30, "B": 20, "C": 10, "D": 40])
            ])
            service.post("IMPAQ", [
                new Answer(question: "1", current: ["A": 30, "B": 40, "C": 10, "D": 20], expected: ["A": 20, "B": 10, "C": 40, "D": 30]),
                new Answer(question: "2", current: ["A": 40, "B": 10, "C": 20, "D": 30], expected: ["A": 10, "B": 40, "C": 30, "D": 20])
            ])
        when:
            def result = service.csv(",").readLines()
        then:
            result.size() == 2
            result[0] ==~ /.*,IMPAQ,1,10,20,30,40,40,30,20,10,2,20,30,40,10,30,20,10,40/
            result[1] ==~ /.*,IMPAQ,1,30,40,10,20,20,10,40,30,2,40,10,20,30,10,40,30,20/

    }
}
