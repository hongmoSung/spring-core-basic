package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepo;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Override
    public void join(Member member) {
        memberRepo.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepo.findById(memberId);
    }

    // 테스트용
    public MemberRepository getMemberRepo() {
        return memberRepo;
    }
}
