package dmi.android.transparentaccounts.object;

/**
 * Created by DZCDMAY on 7.10.2016.
 */
public class Amount {

    private double value;
    private int precision;
    private String currency;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
