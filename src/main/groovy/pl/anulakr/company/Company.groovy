package pl.anulakr.company

import groovy.transform.CompileStatic
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
@CompileStatic
class Company {

    @Id
    String id

    @Indexed
    String name
}