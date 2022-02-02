package hello.core.member;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest {

    MemberService memberService = new MemberServiceImpl();

    @Test
    void join() {
        // given
        final Member member = new Member(1L, "memberA", Grade.VIP);

        // when
        memberService.join(member);
        final Member findMember = memberService.findMember(member.getId());

        // then
        assertThat(member).isEqualTo(findMember);
    }
}
