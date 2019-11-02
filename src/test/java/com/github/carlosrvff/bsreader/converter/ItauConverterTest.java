package com.github.carlosrvff.bsreader.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.github.carlosrvff.bsreader.domain.Balance;
import com.github.carlosrvff.bsreader.domain.Credit;
import com.github.carlosrvff.bsreader.domain.Debit;
import com.github.carlosrvff.bsreader.domain.Statement;
import com.github.carlosrvff.bsreader.exception.InvalidStatementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItauConverterTest {

  private ItauConverter target;

  @BeforeEach
  void setUp() {
    target = new ItauConverter();
  }

  @Test
  void toStatementWhenTextIsDebit() throws InvalidStatementException {
    String textFixture = "01/12\t\t\tRSHOP-KONI ARCO V-01/12       \t\t30,00\t-";

    Statement statement = target.toStatement(textFixture);

    assertTrue(statement instanceof Debit);

    Debit debit = (Debit) statement;
    assertEquals("01/12", debit.getDate());
    assertEquals("RSHOP-KONI ARCO V-01/12", debit.getStore());
    assertEquals("30,00", debit.getValue());
  }

  @Test
  void toStatementWhenTextIsCredit() throws InvalidStatementException {
    String textFixture = "05/12\t\t\tREMUNERACAO/SALARIO       \t3032\t1.000,00\t\t\t";

    Statement statement = target.toStatement(textFixture);

    assertTrue(statement instanceof Credit);
    Credit credit = (Credit) statement;
    assertEquals("05/12", credit.getDate());
    assertEquals("REMUNERACAO/SALARIO", credit.getFrom());
    assertEquals("1.000,00", credit.getValue());
  }

  @Test
  void toStatementWhenTextIsBalance() throws InvalidStatementException {
    String textFixture = "15/12\t\t\tSALDO FINAL DISPONIVEL       \t\t\t\t1.000,00\t";

    Statement statement = target.toStatement(textFixture);

    assertTrue(statement instanceof Balance);
    Balance balance = (Balance) statement;
    assertEquals("15/12", balance.getDate());
    assertEquals("1.000,00", balance.getValue());
  }

  @Test
  void toStatementWhenTextIsBalanceIsInvalid() {
    String textFixture = "adsadas\t";
    try {
      Statement statement = target.toStatement(textFixture);
    } catch (Exception e) {
      assertTrue(e instanceof InvalidStatementException);
    }
  }

  @Test
  void toStatementWhenTextIsEmpty() {
    String textFixture = "";
    try {
      Statement statement = target.toStatement(textFixture);
    } catch (Exception e) {
      assertTrue(e instanceof InvalidStatementException);
    }
  }

  @Test
  void toStatementWhenTextIsIsNull() {
    try {
      Statement statement = target.toStatement(null);
    } catch (Exception e) {
      assertTrue(e instanceof InvalidStatementException);
    }
  }

  @Test
  void isHeaderWhenTextIsDifferentToHeader() {
    String textFixture = "TEXT DIFFERENT";
    assertFalse(target.isHeader(textFixture));
  }

  @Test
  void isHeaderWhenTextIsEqualToHeader() {
    String textFixture = ItauConverter.HEADER;
    assertTrue(target.isHeader(textFixture));
  }
}
