package local.impactlife.cubetalk.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankInfo {
    @Expose
    @SerializedName("bank_name")
    private String mBankName;

    @Expose
    @SerializedName("bank_account_number")
    private String mBankAccountNumber;

    @Expose
    @SerializedName("bank_ifsc")
    private String mBankIfsc;

    public String getBankName() {
        return mBankName;
    }

    public void setBankName(String bankName) {
        this.mBankName = bankName;
    }

    public String getBankAccountNumber() {
        return mBankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.mBankAccountNumber = bankAccountNumber;
    }

    public String getBankIfsc() {
        return mBankIfsc;
    }

    public void setBankIfsc(String bankIfsc) {
        this.mBankIfsc = bankIfsc;
    }
}
