import java.util.HashMap;
import java.util.Map;

public class Bank {
  private final Map<Pair, Double> rates;

  public Bank() {
    this.rates = new HashMap<>();
  }

  public void addRate(Money.Currency from, Money.Currency to, double rate) {
    this.rates.put(new Pair(from, to), rate);
  }

  public Money exchange(Expression expression, Money.Currency currency) {
    return expression.reduce(this, currency);
  }

  public double rate(Money.Currency from, Money.Currency to) {
    return this.rates.getOrDefault(new Pair(from, to), 1.0);
  }

  private record Pair(Money.Currency from, Money.Currency to) {
  }
}
