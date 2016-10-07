package dmi.android.transparentaccounts.object;

import java.io.Serializable;

/**
 * Created by DZCDMAY on 15.9.2016.
 */
public class AccountTeaser{

    private String accountNumber;
    private String bankCode;
    private String transparencyFrom;
    private String transparencyTo;
    private String publicationTo;
    private String actualizationDate;
    private Double balance;
    private String currency;
    private String iban;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getTransparencyFrom() {
        return transparencyFrom;
    }

    public void setTransparencyFrom(String transparencyFrom) {
        this.transparencyFrom = transparencyFrom;
    }

    public String getTransparencyTo() {
        return transparencyTo;
    }

    public void setTransparencyTo(String transparencyTo) {
        this.transparencyTo = transparencyTo;
    }

    public String getPublicationTo() {
        return publicationTo;
    }

    public void setPublicationTo(String publicationTo) {
        this.publicationTo = publicationTo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getActualizationDate() {
        return actualizationDate;
    }

    public void setActualizationDate(String actualizationDate) {
        this.actualizationDate = actualizationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
