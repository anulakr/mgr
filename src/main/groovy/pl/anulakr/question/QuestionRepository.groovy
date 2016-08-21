package pl.anulakr.question

import groovy.transform.CompileStatic
import org.springframework.data.mongodb.repository.MongoRepository

@CompileStatic
interface QuestionRepository extends MongoRepository<Question, String> {

    Optional<Question> findByLabelIgnoreCase(String label)

}