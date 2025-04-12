package io.cdap.wrangler.api.parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TokenGroup is a wrapper for a list of {@link Token} objects,
 * typically representing multiple arguments in a directive.
 */
public class TokenGroup implements Iterable<Token> {
  private final List<Token> tokens;

  public TokenGroup() {
    this.tokens = new ArrayList<>();
  }

  public void add(Token token) {
    tokens.add(token);
  }

  public Token get(int index) {
    return tokens.get(index);
  }

  public int size() {
    return tokens.size();
  }

  public boolean isEmpty() {
    return tokens.isEmpty();
  }

  @Override
  public Iterator<Token> iterator() {
    return tokens.iterator();
  }

  @Override
  public String toString() {
    return tokens.toString();
  }
}
