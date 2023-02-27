import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class MoneyTest {

  static Bank bank = new Bank();

  @BeforeAll
  static void setup() {
    bank.addRate(Money.Currency.DOLLAR, Money.Currency.FRANC, 0.5);
    bank.addRate(Money.Currency.FRANC, Money.Currency.DOLLAR, 2);
  }

  @Test
  @DisplayName("곱하기를 할 수 있다.")
  void times_success() {
    Expression five = Money.dollar(5);
    assertThat(five.times(2)).isEqualTo(Money.dollar(10));
    assertThat(five.times(3)).isEqualTo(Money.dollar(15));
  }

  @Test
  @DisplayName("더하기를 할 수 있다.")
  void add_success() {
    Expression ten = Money.dollar(5).plus(5);
    assertThat(ten).isEqualTo(Money.dollar(10));
  }

  @Test
  @DisplayName("값 객체로서 값이 같으면 같다.")
  void equals_success() {
    assertThat(Money.dollar(5)).isEqualTo(Money.dollar(5));
    assertThat(Money.dollar(6)).isNotEqualTo(Money.dollar(5));
    assertThat(Money.dollar(5)).isNotEqualTo(Money.franc(5));
  }

  @Test
  @DisplayName("Sum Test")
  void sum_success() {
    Expression sum = new Sum(Money.dollar(5), Money.dollar(2));
    Money result = bank.exchange(sum, Money.Currency.DOLLAR);
    assertThat(result).isEqualTo(Money.dollar(7));
  }

  @Test
  @DisplayName("표현식 단위끼리 더할 수 있다.")
  void plus_whenBetweenExpression_success() {
    Expression ten = Money.dollar(10);
    Expression twenty = Money.franc(20);
    Expression thirty = new Sum(ten, twenty).plus(ten);
    Money result = bank.exchange(thirty, Money.Currency.DOLLAR);

    assertThat(result).isEqualTo(Money.dollar(30));
  }

  @Test
  @DisplayName("표현식 단위를 곱할 수 있다.")
  void times_whenExpression_success() {
    Expression ten = Money.dollar(10);
    Expression twenty = Money.franc(20);
    Expression forty = new Sum(ten, twenty).times(2);
    Money result = bank.exchange(forty, Money.Currency.DOLLAR);

    assertThat(result).isEqualTo(Money.dollar(40));
  }

  @Test
  @DisplayName("연산이 같은 화폐끼리 이뤄지면, Money 객체를 유지한다.")
  void operate_whenBetweenSameCurrency_returnMoneyType() {
    Expression plus = Money.dollar(10).plus(Money.dollar(10));
    Expression times = Money.dollar(10).times(2);

    assertThat(plus).isInstanceOf(Money.class);
    assertThat(times).isInstanceOf(Money.class);
  }

  @Test
  @DisplayName("같은 통화로 환전이 가능하다.")
  void exchange_whenFromToSame_returnSameMoney() {
    Money result = bank.exchange(Money.dollar(10), Money.Currency.DOLLAR);
    assertThat(result).isEqualTo(Money.dollar(10));
  }

  @Test
  @DisplayName("다른 통화로 환전이 가능하다.")
  void exchange_success() {
    Money result = bank.exchange(Money.dollar(10), Money.Currency.FRANC);
    assertThat(result).isEqualTo(Money.franc(20));
  }

  @Test
  @DisplayName("다른 화폐끼리 연산이 가능하다.")
  void operate_whenBetweenDifferentCurrencies_success() {
    Expression sum = Money.dollar(5).plus(Money.franc(10));
    Money reduced = bank.exchange(sum, Money.Currency.DOLLAR);
    assertThat(reduced).isEqualTo(Money.dollar(10));
  }

}
