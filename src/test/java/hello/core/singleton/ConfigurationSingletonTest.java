package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class ConfigurationSingletonTest {

    @Test
    void configurationTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        final MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        final OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        final MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);

        final MemberRepository memberRepo1 = memberService.getMemberRepo();
        final MemberRepository memberRepo2 = orderService.getMemberRepo();

        System.out.println("memberService -> memberRepo1 = " + memberRepo1);
        System.out.println("orderService -> memberRepo2 = " + memberRepo2);
        System.out.println("memberRepository = " + memberRepository);

        assertThat(memberService.getMemberRepo()).isSameAs(memberRepository);
        assertThat(orderService.getMemberRepo()).isSameAs(memberRepository);
    }

    @Test
    void configurationDeep() {
        //AppConfig 도 스프링 빈으로 등록된다.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);
        System.out.println("bean = " + bean.getClass());
        //출력: bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$4d07cd43
    }
}
