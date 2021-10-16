package com.api.youtube.sample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest
class ApplicationSpec extends Specification {

    @Autowired
    Application target

    @Unroll
    def "Main"() {
        when:
        target.main()
        then:
        noExceptionThrown()
    }
}
