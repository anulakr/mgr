package pl.anulakr.company

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import static org.springframework.web.bind.annotation.RequestMethod.GET

@RestController
@RequestMapping(path = "/companies")
class CompanyController {

    private final CompanyRepository repository

    @Autowired
    CompanyController(CompanyRepository repository) {
        this.repository = repository
    }

    @RequestMapping(path = "/{name}", method = GET, produces = APPLICATION_JSON_VALUE)
    ResponseEntity<?> findOne(@PathVariable("name") String name) {
        return repository.findByNameIgnoreCase(name)
            .map({ ResponseEntity.ok(it) })
            .orElse(ResponseEntity.notFound().build())
    }
}
