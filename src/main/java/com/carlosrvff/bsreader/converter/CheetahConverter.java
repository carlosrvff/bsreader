package com.carlosrvff.bsreader.converter;

import com.carlosrvff.bsreader.domain.Statement;
import com.carlosrvff.bsreader.domain.statement.Credit;
import com.carlosrvff.bsreader.domain.statement.Debit;
import org.apache.commons.lang3.StringUtils;

public class CheetahConverter extends BankConverter {

  public static final char FEE_MARKER_FIXTURE = '\"';
  public static final char CURRENCY_SYMBOL = '€';
  public static final char ADD_SYMBOL = '+';

  @Override
  protected boolean arePartsInvalid(String[] parts) {
    return parts.length != 6;
  }

  @Override
  protected Statement toStatement(String[] parts, String line) {
    String date = parts[0];
    String details = parts[2];
    String value = parts[3];
    String fee = parts[4];

    if (StringUtils.isBlank(details) || isDebitValue(value)) {
      if (StringUtils.isNotBlank(removeExtraSymbols(fee))) {
        value = new StringBuilder(value).append(ADD_SYMBOL).append(fee).toString();
      }
      return Debit.builder()
          .date(date)
          .store(details)
          .value(removeExtraSymbols(value))
          .originalText(line)
          .build();
    } else {
      return Credit.builder()
          .date(date)
          .from(details)
          .value(removeExtraSymbols(value))
          .originalText(line)
          .build();
    }
  }


  private String removeExtraSymbols(String value) {
    value = StringUtils.remove(value, FEE_MARKER_FIXTURE);
    value = StringUtils.remove(value, DEBIT_SYMBOL);
    value = StringUtils.remove(value, CURRENCY_SYMBOL);
    value = StringUtils.remove(value, " ");
    return value;
  }

  @Override
  public String getHeader() {
    return "Date, Transaction Type, Merchant, Amount, Fee, Result";
  }

  @Override
  protected String getSeparator() {
    return ",";
  }
}
