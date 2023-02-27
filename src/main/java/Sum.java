record Sum (
  Expression augend,
  Expression addend
) implements Expression {
  @Override
  public Money reduce(Bank bank, Money.Currency currency) {
    int sum = augend.reduce(bank, currency).amount() + addend.reduce(bank, currency).amount();
    return new Money(sum, currency);
  }

  @Override
  public Expression times(int multiplier) {
    return new Sum(this.augend.times(multiplier), this.addend.times(multiplier));
  }
}
