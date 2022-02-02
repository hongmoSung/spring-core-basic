package hello.core.member;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepo;

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
}
