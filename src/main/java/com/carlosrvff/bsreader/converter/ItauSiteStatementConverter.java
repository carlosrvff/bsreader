package com.carlosrvff.bsreader.converter;

import com.carlosrvff.bsreader.domain.Statement;
import com.carlosrvff.bsreader.domain.statement.Balance;
import com.carlosrvff.bsreader.domain.statement.Credit;
import com.carlosrvff.bsreader.domain.statement.Debit;
import org.apache.commons.lang3.StringUtils;

public class ItauSiteStatementConverter extends StatementConverter {

  @Override
  public String getSeparator() {
    return "\t";
  }

  @Override
  protected boolean arePartsInvalid(String[] parts) {
    return parts.length < 6;
  }

  @Override
  protected Statement toStatement(String[] parts, String line) {
    if (parts.length >= 8) {
      return Balance.builder().date(parts[0]).value(parts[7]).originalText(line).build();
    }
    String date = parts[0];
    String details = parts[3].trim();
    String value = parts[5];
    if (isDebitValue(value)) {
      return Debit.builder()
          .date(date)
          .store(details)
          .value(removeDebitSymbol(value))
          .originalText(line)
          .build();
    } else {
      return Credit.builder().date(date).from(details).value(value).originalText(line).build();
    }
  }

  @Override
  public boolean isDebitValue(String value) {
    return value.charAt(value.length() - 1) == getDebitSymbol();
  }

  private String removeDebitSymbol(String value) {
    return StringUtils.chop(value);
  }
}
