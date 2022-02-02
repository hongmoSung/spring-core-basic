package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.order.Order;
import hello.core.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {
//        final AppConfig appConfig = new AppConfig();
//        final OrderService orderService = appConfig.orderService();
//        final MemberService memberService = appConfig.memberService();

//        final MemberServiceImpl memberService = new MemberServiceImpl();
//        final OrderServiceImpl orderService = new OrderServiceImpl();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        final MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        final OrderService orderService = applicationContext.getBean("orderService", OrderService.class);

        Long memberId = 1L;
        final Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        final Order order = orderService.createOrder(memberId, "itemA", 10000);
        System.out.println(order.toString());
        System.out.println(order.calculatePrice());
    }
}
