package pl.anulakr.answer

import groovy.transform.CompileStatic
import org.springframework.data.mongodb.repository.MongoRepository

@CompileStatic
interface SurveyRepository extends MongoRepository<Survey, String> {
}