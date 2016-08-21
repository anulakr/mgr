package pl.anulakr

import com.mongodb.Mongo
import com.mongodb.MongoClientOptions
import de.flapdoodle.embed.mongo.MongodExecutable
import de.flapdoodle.embed.mongo.MongodProcess
import de.flapdoodle.embed.mongo.config.Net
import groovy.transform.CompileStatic
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.test.context.junit4.SpringRunner
import pl.anulakr.answer.SurveyService

import static org.assertj.core.api.Assertions.assertThat

@RunWith(SpringRunner)
@SpringBootTest
@CompileStatic
class SurveyServiceTest {

    @Autowired
    SurveyService service

    @Test
    void doesNotSaveForUnknownCompany() {

        def result = service.post("test", [])

        assertThat(result.present).isFalse()
    }

    @Configuration
    class TestConfiguration {

        @Autowired
        MongoProperties properties

        @Autowired
        MongoClientOptions options

        @Autowired
        Environment environment

        @Bean(destroyMethod = "close")
        public Mongo mongo(MongodProcess mongodProcess) throws IOException {
            Net net = mongodProcess.config.net()
            properties.setHost(net.serverAddress.hostName)
            properties.setPort(net.port)
            return properties.createMongoClient(options, environment)
        }

        @Bean(destroyMethod = "stop")
        public MongodProcess mongodProcess(MongodExecutable mongodExecutable) throws IOException {
            return mongodExecutable.start()
        }
    }
}