package local.impactlife.cubetalk.utils.customview;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.textview.MaterialTextView;

import local.impactlife.cubetalk.R;
import local.impactlife.cubetalk.databinding.CustomAlertReviewUpcomingDialogBinding;
import local.impactlife.cubetalk.models.common.ConsultingSlot;
import local.impactlife.cubetalk.models.common.ConsultingSlotDuration;
import local.impactlife.cubetalk.models.user_info.UserInfoFetchResponse;
import local.impactlife.cubetalk.utils.Utils;

import static local.impactlife.cubetalk.utils.Utils.convert24HoursTo12Hours;

public class CustomAlertReviewUpcomingChanges extends Dialog implements View.OnClickListener {
    Activity activity;
    String faturetimeslot;
    StringBuilder slotchaneandrate = new StringBuilder();
    StringBuilder slot = new StringBuilder();
    UserInfoFetchResponse.FatureConsultingSlotPrice fatureConsultingSlotPrice;
    CustomAlertReviewUpcomingDialogBinding mcustomAlertReviewUpcomingDialogBinding;
    MaterialTextView mtv_effectivedate;
    AppCompatImageView mImaageView;
    MaterialTextView mtvTimeChangeval;
    MaterialTextView mtvslotChangeandrate;
    //ConsultingSlotDuration consultingSlotDuration;
    ConsultingSlot consultingSlot;
    public CustomAlertReviewUpcomingChanges(Activity act, String fature_booktime_slot, UserInfoFetchResponse.FatureConsultingSlotPrice fatureConsultingSlotPrice, ConsultingSlot consultingSlot) {
        super(act);
        this.activity= act;
        this.faturetimeslot=fature_booktime_slot;
        this.fatureConsultingSlotPrice=fatureConsultingSlotPrice;
         this.consultingSlot=consultingSlot;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCanceledOnTouchOutside(false);

       // Window window =getWindow();
        //window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mcustomAlertReviewUpcomingDialogBinding=CustomAlertReviewUpcomingDialogBinding.inflate(LayoutInflater.from(activity));

       /* WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mcustomAlertReviewUpcomingDialogBinding.getRoot().setLayoutParams(lp);
        */
        setContentView(mcustomAlertReviewUpcomingDialogBinding.getRoot());
        mtv_effectivedate=mcustomAlertReviewUpcomingDialogBinding.mtvEffectivedate;
        mImaageView=mcustomAlertReviewUpcomingDialogBinding.mImaageView;
        mtvTimeChangeval=mcustomAlertReviewUpcomingDialogBinding.mtvTimeChangeval;
        mtvslotChangeandrate=mcustomAlertReviewUpcomingDialogBinding.mtvslotChangeandrate;

        if (fatureConsultingSlotPrice.getDuration12().getStatus()){
            slotchaneandrate.append("12min - "+ fatureConsultingSlotPrice.getDuration12().getInr()+" INR");
            slotchaneandrate.append("\n");
        }
          if (fatureConsultingSlotPrice.getDuration25().getStatus()){
            slotchaneandrate.append("25min - "+ fatureConsultingSlotPrice.getDuration25().getInr()+" INR");
            slotchaneandrate.append("\n");

        }  if (fatureConsultingSlotPrice.getDuration50().getStatus()){

            slotchaneandrate.append("50min - "+ fatureConsultingSlotPrice.getDuration50().getInr()+" INR");
            slotchaneandrate.append("\n");
        }
        mtvslotChangeandrate.setText(slotchaneandrate);
        mtv_effectivedate.setText(Utils.convertSlotDate(faturetimeslot));

        if (consultingSlot.getSunday().getIsActive()){
            slot.append("Sunday : ");
            if (!TextUtils.isEmpty(consultingSlot.getSunday().getFrom1())){
                slot.append(convert24HoursTo12Hours(consultingSlot.getSunday().getFrom1()));
                slot.append(" to ");
                slot.append(convert24HoursTo12Hours(consultingSlot.getSunday().getTo1()));
                slot.append("\n");
            }
            if (!TextUtils.isEmpty(consultingSlot.getSunday().getFrom2())) {

                slot.append(convert24HoursTo12Hours(consultingSlot.getSunday().getFrom2()));
                slot.append(" to ");
                slot.append(convert24HoursTo12Hours(consultingSlot.getSunday().getTo2()));
            }

        }
          if (consultingSlot.getSaturday().getIsActive()){
            if (!TextUtils.isEmpty(slot))
                slot.append("\n");
            slot.append("Saturday : ");
            if (!TextUtils.isEmpty(consultingSlot.getSaturday().getFrom1())){
                slot.append(convert24HoursTo12Hours(consultingSlot.getSaturday().getFrom1()));
                slot.append(" to ");
                slot.append(convert24HoursTo12Hours(consultingSlot.getSaturday().getTo1()));
                slot.append("\n");
            }
            if (!TextUtils.isEmpty(consultingSlot.getSaturday().getFrom2())) {

                slot.append(convert24HoursTo12Hours(consultingSlot.getSaturday().getFrom2()));
                slot.append(" to ");
                slot.append(convert24HoursTo12Hours(consultingSlot.getSaturday().getTo2()));
            }
        }

          if (consultingSlot.getWeekdays().getIsActive()){
            if (!TextUtils.isEmpty(slot))
               slot.append("\n");
            slot.append("Weekdays : ");
            if (!TextUtils.isEmpty(consultingSlot.getWeekdays().getFrom1())){
                slot.append(convert24HoursTo12Hours(consultingSlot.getWeekdays().getFrom1()));
                slot.append(" to ");
                slot.append(convert24HoursTo12Hours(consultingSlot.getWeekdays().getTo1()));
                slot.append("\n");
            }
            if (!TextUtils.isEmpty(consultingSlot.getWeekdays().getFrom2())) {
               // slot.append("\n"+"                     ");

                slot.append(convert24HoursTo12Hours(consultingSlot.getWeekdays().getFrom2()));
                slot.append(" to ");
                slot.append(convert24HoursTo12Hours(consultingSlot.getWeekdays().getTo2()));
            }

        }

     if (!TextUtils.isEmpty(slot)){
         mtvTimeChangeval.setText(slot);
     }else
         mtvTimeChangeval.setText("None");

        mImaageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}
