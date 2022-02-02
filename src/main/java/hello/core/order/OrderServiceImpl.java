package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepo;
    private final DiscountPolicy discountPolicy;

    public OrderServiceImpl(MemberRepository memberRepo, DiscountPolicy discountPolicy) {
        this.memberRepo = memberRepo;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        final Member member = memberRepo.findById(memberId);
        final int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
