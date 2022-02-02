package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;

public class MemberApp {

    public static void main(String[] args) {
        final AppConfig appConfig = new AppConfig();
        final MemberService memberService = appConfig.memberService();
//        final MemberServiceImpl memberService = new MemberServiceImpl();
        final Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        final Member findMember = memberService.findMember(member.getId());
        System.out.println("new Member = " + member.getName());
        System.out.println("find Member = " + findMember.getName());
    }
}
