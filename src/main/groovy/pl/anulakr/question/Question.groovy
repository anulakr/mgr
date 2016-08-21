package pl.anulakr.question

import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
@CompileStatic
class Question {

    @Id
    String id

    @Indexed
    String label

    String text

    List<QuestionOption> options

}

@CompileStatic
class QuestionOption {

    @Indexed
    String label

    String text

}
