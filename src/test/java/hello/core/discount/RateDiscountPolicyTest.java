package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP 는 10% 할인이 적용 되어야 한다.")
    void vip_o() {
        // given
        final Member member = new Member(1L, "memberVip", Grade.VIP);

        // when
        final int discount = discountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isEqualTo(1000);

    }

    @Test
    @DisplayName("VIP 가 아니면 할인이 적용되지 않아야 한다.")
    void vip_x() {
        // given
        final Member member = new Member(1L, "memberVip", Grade.BASIC);

        // when
        final int discount = discountPolicy.discount(member, 10000);

        // then
        assertThat(discount).isNotEqualTo(1000);

    }
}