package com.carlosrvff.bsreader.helper;

import com.carlosrvff.bsreader.domain.statement.Credit;
import com.carlosrvff.bsreader.domain.statement.Debit;

public class KbcStatementUtils implements BankStatementUtils {

  public static final String SEPARATOR = "\t";

  public static final char CURRENCY_SYMBOL = '€';
  public static final char DEBIT_SYMBOL = '-';

  private static final String DATE_FIXTURE = "30 Jul 2019";
  private static final String TRANSACTION_FIXTURE = "ANY STORE";
  private static final String FIVE_THOUSAND_AMOUNT_FIXTURE = "5,000.00";

  public String createDebitStatementText() {
    return new StringBuilder(DATE_FIXTURE)
        .append(SEPARATOR)
        .append(SEPARATOR)
        .append(TRANSACTION_FIXTURE)
        .append(SEPARATOR)
        .append(DEBIT_SYMBOL)
        .append(CURRENCY_SYMBOL)
        .append(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .append(SEPARATOR)
        .append(CURRENCY_SYMBOL)
        .append(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .toString();
  }

  public Debit createDebitStatement(String textFixture) {
    return Debit.builder()
        .originalText(textFixture)
        .value(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .store(TRANSACTION_FIXTURE)
        .date(DATE_FIXTURE)
        .build();
  }

  public String createCreditStatementText() {
    return new StringBuilder(DATE_FIXTURE)
        .append(SEPARATOR)
        .append(SEPARATOR)
        .append(TRANSACTION_FIXTURE)
        .append(SEPARATOR)
        .append(CURRENCY_SYMBOL)
        .append(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .append(SEPARATOR)
        .append(SEPARATOR)
        .append(CURRENCY_SYMBOL)
        .append(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .toString();
  }

  public Credit createCreditStatement(String textFixture) {
    return Credit.builder()
        .originalText(textFixture)
        .from(TRANSACTION_FIXTURE)
        .date(DATE_FIXTURE)
        .value(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .build();
  }

  public static String createOriginalDebitStatementText() {
    return new StringBuilder(DATE_FIXTURE)
        .append(SEPARATOR)
        .append(SEPARATOR)
        .append(System.lineSeparator())
        .append(TRANSACTION_FIXTURE)
        .append(SEPARATOR)
        .append(System.lineSeparator())
        .append(DEBIT_SYMBOL)
        .append(CURRENCY_SYMBOL)
        .append(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .append(SEPARATOR)
        .append(CURRENCY_SYMBOL)
        .append(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .append(System.lineSeparator())
        .toString();
  }

  public static String createOriginalCreditStatementText() {
    return new StringBuilder(DATE_FIXTURE)
        .append(SEPARATOR)
        .append(SEPARATOR)
        .append(System.lineSeparator())
        .append(TRANSACTION_FIXTURE)
        .append(SEPARATOR)
        .append(System.lineSeparator())
        .append(CURRENCY_SYMBOL)
        .append(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .append(SEPARATOR)
        .append(SEPARATOR)
        .append(CURRENCY_SYMBOL)
        .append(FIVE_THOUSAND_AMOUNT_FIXTURE)
        .append(System.lineSeparator())
        .toString();
  }

  public static String createTwoStatementsText() {
    String debitText = KbcStatementUtils.createOriginalDebitStatementText();
    String creditText = KbcStatementUtils.createOriginalCreditStatementText();
    return mergeStrings(debitText, creditText);
  }

  private static String mergeStrings(String... texts) {
    StringBuilder result = new StringBuilder();
    for (String text : texts) {
      result.append(text);
    }
    return result.toString();
  }
}
