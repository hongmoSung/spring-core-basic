package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {

    /**
     * @param member 유저
     * @param price  가격
     * @return 할인 대상 금액
     */
    int discount(Member member, int price);
}
