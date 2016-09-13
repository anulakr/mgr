package pl.anulakr.answer

import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@CompileStatic
class Survey {

    @Id
    String id

    String company

    List<Answer> answers

    String csvRow(String separator) {
        return ([id, company] + answers.collect {it.csvRow(separator)}).join(separator)
    }

}

class Answer {

    String question

    Map<String, Integer> current

    Map<String, Integer> expected

    boolean isValid() {
        return current.values().sum() == 100 && expected.values().sum() == 100
    }

    String csvRow(String separator) {
        return ([question] + [current, expected].collect { map ->
            map.keySet().sort().collect { key -> map.getOrDefault(key, 0).toString() }
        }.inject([], {acc, el -> acc + el})).join(separator)
    }
}
