package com.langdy.student.application

import com.langdy.auth.application.UserDetailsService
import com.langdy.auth.application.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class StudentDetailsService(
    private val studentRepository: StudentQueryRepository,
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        studentRepository.findByName(username)?.let {
            return UserDetails(it.id, it.name)
        }

        return null
    }
}
