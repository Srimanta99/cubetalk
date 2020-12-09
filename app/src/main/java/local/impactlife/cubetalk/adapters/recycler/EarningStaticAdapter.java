package local.impactlife.cubetalk.adapters.recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import local.impactlife.cubetalk.activities.CreditShellActivity;
import local.impactlife.cubetalk.databinding.ItemCreditShellBinding;
import local.impactlife.cubetalk.models.creditshell.CreditShellResponse;
import local.impactlife.cubetalk.models.earning_statics.EarningStaticesResponse;
import local.impactlife.cubetalk.utils.Utils;

public class EarningStaticAdapter extends  RecyclerView.Adapter<EarningStaticAdapter.ViewHolder>{
    Activity activity;
    List<EarningStaticesResponse.BookingsList> bookingsLists;

    public EarningStaticAdapter(Activity activity, List<EarningStaticesResponse.BookingsList> bookingsLists) {
        this.activity=activity;
        this.bookingsLists=bookingsLists;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EarningStaticAdapter.ViewHolder(ItemCreditShellBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mCreditDate.setText(Utils.convertSlotDateforearningstatics(bookingsLists.get(position).getDate()));
        holder.mCreditAmount.setText(bookingsLists.get(position).getExpertBoookings().toString());
        holder.credittype.setText("INR "+ bookingsLists.get(position).getExpertShareAmount().toString());
       /* if (bookingsLists.get(position).getExpertBoookings()==1)
             holder.credittype.setText("Credit");
        else
            holder.credittype.setText("Debit");*/

    }

    @Override
    public int getItemCount() {
        return bookingsLists.size();
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
