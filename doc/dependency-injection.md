## 의존관계 자동 주입

### 다양한 의존관계 주입 방법

1. 생성자 주입
2. 수정자 주입(setter 주입)
3. 필드 주입
4. 일반 메서드 주입

### 생성자 주입
- 이름 그대로 생성자를 통해서 의존 관계를 주입 받는 방법이다. 
- 지금까지 우리가 진행했던 방법이 바로 생성자 주입이다. 
- 특징
  - 생성자 호출시점에 딱 1번만 호출되는 것이 보장된다.
  - 불변, 필수 의존관계에 사용
```java
@Component
public class OrderServiceImpl implements OrderService {

    // final 은 무조건 값을 받게 강제한다.
    private final MemberRepository memberRepo;
    private final DiscountPolicy discountPolicy;

    // 중요! 생성자가 딱 1개만 있으면 @Autowired를 생략해도 자동 주입 된다. 물론 스프링 빈에만 해당한다.
    @Autowired
    public OrderServiceImpl(MemberRepository memberRepo, DiscountPolicy discountPolicy) {
        this.memberRepo = memberRepo;
        this.discountPolicy = discountPolicy;
    }

}
```
### 수정자 주입(setter 주입)
- setter라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입하는 방법이다. 
- 특징
  - 선택, 변경 가능성이 있는 의존관계에 사용
  - 자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법이다.
```java
@Component
public class OrderServiceImpl implements OrderService {

    private MemberRepository memberRepo;
    private DiscountPolicy discountPolicy;

    // 참고: @Autowired 의 기본 동작은 주입할 대상이 없으면 오류가 발생한다. 
    // 주입할 대상이 없어도 동작하게 하려면 @Autowired(required = false) 로 지정하면 된다.
    @Autowired(required = false)
    public void setMemberRepo(MemberRepository memberRepo) {
        this.memberRepo = memberRepo;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }
}
```
### 필드 주입
- 이름 그대로 필드에 바로 주입하는 방법이다.
- 특징
  - 코드가 간결해서 많은 개발자들을 유혹하지만 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.
  - DI 프레임워크가 없으면 아무것도 할 수 없다.
  - 사용하지 말자!
    - 애플리케이션의 실제 코드와 관계 없는 테스트 코드
    - 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용
```java
@Component
public class OrderServiceImpl implements OrderService {


    @Autowired
    private MemberRepository memberRepo;
    
    @Autowired 
    private DiscountPolicy discountPolicy;
    
}
```
### 일반 메서드 주입
- 일반 메서드를 통해서 주입 받을 수 있다. 
- 특징
  - 한번에 여러 필드를 주입 받을 수 있다. 
  - 일반적으로 잘 사용하지 않는다.
```java
@Component
public class OrderServiceImpl implements OrderService {

    private MemberRepository memberRepo;
    private DiscountPolicy discountPolicy;

    @Autowired
    public init(MemberRepository memberRepo, DiscountPolicy discountPolicy) {
        this.memberRepo = memberRepo;
        this.discountPolicy = discountPolicy;
    }
}
```

### 옵션 처리
주입할 스프링 빈이 없어도 동작해야 할 때가 있다.
그런데 ```@Autowired``` 만 사용하면 required 옵션의 기본값이 true 로 되어 있어서 자동 주입 대상이 없으면 오류가 발생한다.

자동 주입 대상을 옵션으로 처리하는 방법은 다음과 같다.
- ```@Autowired(required=false)``` : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
- ```org.springframework.lang.@Nullable``` : 자동 주입할 대상이 없으면 null이 입력된다.
- ```Optional<>``` : 자동 주입할 대상이 없으면 Optional.empty 가 입력된다.

```java
public class AutoWiredTest {

    @Test
    void autowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
        // Member는 스프링 빈이 아니다.
        // setNoBean1() 은 @Autowired(required=false) 이므로 호출 자체가 안된다.
    }

  static class TestBean { 
      // 참고: @Nullable, Optional은 스프링 전반에 걸쳐서 지원된다. 
      // 예를 들어서 생성자 자동 주입에서 특정 필드에만 사용해도 된다.
      @Autowired(required = false)
      public void setNoBean1(Member member) {
                  System.out.println("setNoBean1 = " + member);
        }

      @Autowired
      public void setNoBean2(@Nullable Member member) {
                  System.out.println("setNoBean2 = " + member);
        }

      @Autowired
      public void setNoBean3(Optional<Member> member) {
                  System.out.println("setNoBean3 = " + member);
        }

    }
}
```

```shell
# 출력 결과 
setNoBean2 = null
setNoBean3 = Optional.empty
```

### 생성자 주입을 선택해라!
과거에는 수정자 주입과 필드 주입을 많이 사용했지만, 최근에는 스프링을 포함한 DI 프레임워크 대부분이
생성자 주입을 권장한다. 그 이유는 다음과 같다.

__불변__
- 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존관계를 변경할 일이 없다. 오히려 대부분의 의존관계는 애플리케이션 종료 전까지 변하면 안된다.(불변해야 한다.)
- 수정자 주입을 사용하면, setXxx 메서드를 public으로 열어두어야 한다.
- 누군가 실수로 변경할 수 도 있고, 변경하면 안되는 메서드를 열어두는 것은 좋은 설계 방법이 아니다.
- 생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 따라서 불변하게 설계할 수 있다.

__final 키워드__  
생성자 주입을 사용하면 필드에 final 키워드를 사용할 수 있다. 그래서 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아준다. 다음 코드를 보자.
```java
@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepo;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepo, DiscountPolicy discountPolicy) {
        this.memberRepo = memberRepo;
    }
}
```
- 잘 보면 필수 필드인 discountPolicy 에 값을 설정해야 하는데, 이 부분이 누락되었다. 자바는 컴파일 시점에 다음 오류를 발생시킨다.
- ```java: variable discountPolicy might not have been initialized```
- 기억하자! 컴파일 오류는 세상에서 가장 빠르고, 좋은 오류다!
> 참고: 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로, 필드에 final 키워드를
사용할 수 없다. 오직 생성자 주입 방식만 final 키워드를 사용할 수 있다.

정리
- 생성자 주입 방식을 선택하는 이유는 여러가지가 있지만, 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 방법이기도 하다.
- 기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다. 생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
- 항상 생성자 주입을 선택해라! 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라. 필드 주입은 사용하지 않는게 좋다.