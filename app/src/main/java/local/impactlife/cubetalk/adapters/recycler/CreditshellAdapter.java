package local.impactlife.cubetalk.adapters.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.activities.CreditShellActivity;
import local.impactlife.cubetalk.databinding.ItemCardFutureBookingBinding;
import local.impactlife.cubetalk.databinding.ItemCardReviewRefundBookingBinding;
import local.impactlife.cubetalk.databinding.ItemCreditShellBinding;
import local.impactlife.cubetalk.models.creditshell.CreditShellResponse;
import local.impactlife.cubetalk.utils.Utils;
import okhttp3.ResponseBody;
import retrofit2.Callback;

public class CreditshellAdapter extends  RecyclerView.Adapter<CreditshellAdapter.ViewHolder>{
    CreditShellActivity creditShellActivity;
    List<CreditShellResponse.Creditshell> creditshell;
    public CreditshellAdapter(CreditShellActivity creditShellActivity, List<CreditShellResponse.Creditshell> creditshell) {
        this.creditShellActivity=creditShellActivity;
        this.creditshell=creditshell;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreditshellAdapter.ViewHolder(ItemCreditShellBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mCreditDate.setText(Utils.convertSlotDateCreDitshell(creditshell.get(position).getCreatedAt()));
        if (creditshell.get(position).getTypeOfTransaction()==1) {
            holder.credittype.setText("credit");
            holder.mCreditAmount.setText(String.valueOf(creditshell.get(position).getFinalAmount()-creditshell.get(position).getAmountBeforeTransaction()));

        }else {
            holder.credittype.setText("debit");
            holder.mCreditAmount.setText(String.valueOf(creditshell.get(position).getAmountBeforeTransaction()-creditshell.get(position).getFinalAmount()));

        }

    }

    @Override
    public int getItemCount() {
        return creditshell.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView mCreditDate;
        MaterialTextView mCreditAmount;
        MaterialTextView mCredittype;
        MaterialTextView credittype;
        public ViewHolder(@NotNull ItemCreditShellBinding itemView) {
            super(itemView.getRoot());
            mCreditDate=itemView.tvCreditDate;
            mCreditAmount=itemView.tvCreditshellItemamount;
           // mCredittype=itemView.credittype;
            credittype=itemView.credittype;
        }
    }
}
