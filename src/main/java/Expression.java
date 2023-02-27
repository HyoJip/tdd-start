public interface Expression {
  Money reduce(Bank bank, Money.Currency currency);

  Expression times(int multiplier);

  default Expression plus(Expression addend) {
    return new Sum(this, addend);
  }
}
