package io.cdap.wrangler.api.parser;

/**
 * A DirectiveParser defines how to parse arguments for a directive.
 * Implementations of this interface are usually provided by Wrangler core,
 * not end users.
 */
public interface DirectiveParser {
  /**
   * Parses the given directive argument string and returns a TokenGroup
   * containing parsed tokens like ColumnName, Text, Integer, etc.
   *
   * @param args argument string from the recipe (e.g. ":colA :colB output_col1 output_col2")
   * @return TokenGroup containing parsed arguments
   * @throws Exception if parsing fails (syntax error, unrecognized token, etc.)
   */
  TokenGroup parse(String args) throws Exception;
}

