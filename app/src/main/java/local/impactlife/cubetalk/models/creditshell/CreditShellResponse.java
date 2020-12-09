package local.impactlife.cubetalk.models.creditshell;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditShellResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("total_shell_balance")
    @Expose
    private Integer totalShellBalance;
    @SerializedName("creditshells")
    @Expose
    private List<Creditshell> creditshells = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalShellBalance() {
        return totalShellBalance;
    }

    public void setTotalShellBalance(Integer totalShellBalance) {
        this.totalShellBalance = totalShellBalance;
    }

    public List<Creditshell> getCreditshells() {
        return creditshells;
    }

    public void setCreditshells(List<Creditshell> creditshells) {
        this.creditshells = creditshells;
    }
   public class Creditshell{
       @SerializedName("_id")
       @Expose
       private String id;
       @SerializedName("createdAt")
       @Expose
       private String createdAt;
       @SerializedName("type_of_transaction")
       @Expose
       private Integer typeOfTransaction;
       @SerializedName("final_amount")
       @Expose
       private Integer finalAmount;
       @SerializedName("amount_after_transaction")
       @Expose
       private Integer amountAfterTransaction;
       @SerializedName("amount_before_transaction")
       @Expose
       private Integer amountBeforeTransaction;
       @SerializedName("slot_date")
       @Expose
       private String slotDate;
       @SerializedName("slot_time")
       @Expose
       private String slotTime;

       public String getId() {
           return id;
       }

       public void setId(String id) {
           this.id = id;
       }

       public String getCreatedAt() {
           return createdAt;
       }

       public void setCreatedAt(String createdAt) {
           this.createdAt = createdAt;
       }

       public Integer getTypeOfTransaction() {
           return typeOfTransaction;
       }

       public void setTypeOfTransaction(Integer typeOfTransaction) {
           this.typeOfTransaction = typeOfTransaction;
       }

       public Integer getFinalAmount() {
           return finalAmount;
       }

       public void setFinalAmount(Integer finalAmount) {
           this.finalAmount = finalAmount;
       }

       public Integer getAmountAfterTransaction() {
           return amountAfterTransaction;
       }

       public void setAmountAfterTransaction(Integer amountAfterTransaction) {
           this.amountAfterTransaction = amountAfterTransaction;
       }

       public Integer getAmountBeforeTransaction() {
           return amountBeforeTransaction;
       }

       public void setAmountBeforeTransaction(Integer amountBeforeTransaction) {
           this.amountBeforeTransaction = amountBeforeTransaction;
       }

       public String getSlotDate() {
           return slotDate;
       }

       public void setSlotDate(String slotDate) {
           this.slotDate = slotDate;
       }

       public String getSlotTime() {
           return slotTime;
       }

       public void setSlotTime(String slotTime) {
           this.slotTime = slotTime;
       }
    }

}
