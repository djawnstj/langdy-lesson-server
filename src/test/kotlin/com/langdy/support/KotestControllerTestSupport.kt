package com.langdy.support

import com.langdy.student.infra.StudentRepository
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import io.mockk.clearAllMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
abstract class KotestControllerTestSupport : BehaviorSpec() {

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    @LocalServerPort
    protected var port: Int = 0

    @Autowired
    protected lateinit var studentRepository: StudentRepository

    @Autowired
    private lateinit var cleanUp: DbCleanUp

    init {
        beforeContainer {
            if (it.isWhen()) {
                clearAllMocks()
                cleanUp.all()
            }
        }
    }

    override fun extensions(): List<Extension> = listOf(SpringExtension)
}
