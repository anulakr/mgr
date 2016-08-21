package pl.anulakr.company

import groovy.transform.CompileStatic
import org.springframework.data.mongodb.repository.MongoRepository

@CompileStatic
interface CompanyRepository extends MongoRepository<Company, String> {

    Optional<Company> findByNameIgnoreCase(String name)
}
