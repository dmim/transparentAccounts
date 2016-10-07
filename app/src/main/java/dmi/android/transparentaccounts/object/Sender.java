package dmi.android.transparentaccounts.object;

/**
 * Created by DZCDMAY on 7.10.2016.
 */
public class Sender {

    private String accountNumber;
    private String bankCode;
    private String iban;
    private String specificSymbol;
    private String specificSymbolParty;
    private String constantSymbol;

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

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public String getSpecificSymbolParty() {
        return specificSymbolParty;
    }

    public void setSpecificSymbolParty(String specificSymbolParty) {
        this.specificSymbolParty = specificSymbolParty;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }
}
