package hello.login.web.session;

import hello.login.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class SessionManagerTest {
    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest() {
        //세션 생성(서버 -> 클라이언트)
        MockHttpServletResponse response = new MockHttpServletResponse(); //기능 테스트 할 수 있도록 가짜 request, response 사용
        Member member = new Member();
        sessionManager.createSession(member, response);

        //요청에 응답 쿠키 저장(클라이언트 -> 서버)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); //mySessionId=1231231-1231-qweqwe

        //세션 조회
        Object result = sessionManager.getSession(request);
        assertThat(result).isEqualTo(member);

        //세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        assertThat(expired).isNull();
    }
}