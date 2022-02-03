package hello.core.beanfind;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApplicationContextInfoTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    void findBeanAll() {
        final String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            final Object bean = ac.getBean(beanDefinitionName);
            System.out.println("name = " + beanDefinitionName + "bean = " + bean);
        }
    }

    @Test
    @DisplayName("애플리 케이션 모든 빈 출력하기")
    void applicationFindBeanAll() {
        final String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            final BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                final Object bean = ac.getBean(beanDefinitionName);
                System.out.println("name = " + beanDefinitionName + "bean = " + bean);
            }
        }
    }
}
