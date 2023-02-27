record Money(
  int amount,
  Currency currency
) implements Expression {

  protected Money(int amount, Currency currency) {
    this.amount = amount;
    this.currency = currency;
  }

  public static Money dollar(int amount) {
    return new Money(amount, Currency.DOLLAR);
  }

  public static Money franc(int amount) {
    return new Money(amount, Currency.FRANC);
  }

  public Money plus(int addend) {
    return new Money(this.amount + addend, this.currency);
  }

  @Override
  public Expression plus(Expression addend) {
    if (addend instanceof Money m && m.currency == this.currency) {
      return this.plus(m.amount);
    }
    return Expression.super.plus(addend);
  }

  @Override
  public Expression times(int multiplier) {
    return new Money(this.amount * multiplier, this.currency);
  }

  @Override
  public Money reduce(Bank bank, Currency currency) {
    double rate = bank.rate(this.currency, currency);
    return new Money((int) (this.amount / rate), currency);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Money money = (Money) o;

    if (amount != money.amount) return false;
    return currency == money.currency;
  }

  @Override
  public int hashCode() {
    int result;
    long temp;
    temp = Double.doubleToLongBits(amount);
    result = (int) (temp ^ (temp >>> 32));
    result = 31 * result + (currency != null ? currency.hashCode() : 0);
    return result;
  }

  enum Currency {
    DOLLAR,
    FRANC;
  }
}
