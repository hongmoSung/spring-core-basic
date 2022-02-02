package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        final MemberServiceImpl memberService = new MemberServiceImpl();
        final OrderServiceImpl orderService = new OrderServiceImpl();

        Long memberId = 1L;
        final Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        final Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println(order.toString());
        System.out.println(order.calculatePrice());
    }
}
