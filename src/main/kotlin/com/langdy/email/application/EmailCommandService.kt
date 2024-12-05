package com.langdy.email.application

import com.langdy.email.application.command.SendEmailCommand

interface EmailCommandService {
    fun sendEmail(command: SendEmailCommand) {
        /*
        이메일 프로토콜을 사용해 이메일을 보내는 로직이 있는 메서드 입니다.
        이메일 전송 실패를 고려해 서킷브레이커 패턴으로 재시도 로직을 구현하는것이 좋다고 생각합니다.
        최종적으로 이메일 전송에 실패하는 경우에 대해선 아직 경험이 부족하여 좋은 결정을 하지 못하였습니다.
        하지만 제가 생각한 방안은 아래와 같습니다.
        1. 이메일이 아닌 다른 방법으로 선생님께 알린다.
        2. 실패를 추적하는 테이블을 만들어 실패한 메일을 배치로 전송한다.
        3. 관리자 화면에서 전송 실패 메일 내역을 확인하여 관리자가 사후 처리하는 방식으로 해결한다.
        이렇게 세 가지 방법을 생각하였습니다.

        이외에 이메일 전송 성공 여부와 관계 없이 수업 신청은 정상적으로 완료되게 하는것이 좋다고 생각합니다.
         */
    }
}
