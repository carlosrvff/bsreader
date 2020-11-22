package com.carlosrvff.bsreader.converter;

import com.carlosrvff.bsreader.domain.Balance;
import com.carlosrvff.bsreader.domain.Credit;
import com.carlosrvff.bsreader.domain.Debit;
import com.carlosrvff.bsreader.domain.Statement;
import java.util.HashSet;
import lombok.NonNull;

public class ItauCsvConverter extends BankConverter {

  @Override
  public Statement toStatement(@NonNull String line) {
    validate(line);
    String[] parts = line.split(getSeparator());
    if (parts.length < 3) {
      throw new IllegalArgumentException("Incorrect numbers of fields: " + line);
    }
    String date = parts[0];
    String details = parts[1];
    String value = parts[2];

    if (getHashBalanceDetailsValues().contains(details)) {
      return Balance.builder().date(date).value(value).originalText(line).build();
    } else {
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
  }

  private HashSet<String> getHashBalanceDetailsValues() {
    HashSet<String> result = new HashSet<>();
    result.add("RES APLIC AUT MAIS");
    result.add("SALDO APLIC AUT MAIS");
    result.add("SALDO FINAL");
    result.add("SALDO PARCIAL");
    result.add("SALDO INICIAL");
    return result;
  }

  @Override
  public String getHeader() {
    return "data;detalhe;valor";
  }

  @Override
  protected String getSeparator() {
    return ";";
  }

  private String removeDebitSymbol(String value) {
    return value.substring(1);
  }
}
