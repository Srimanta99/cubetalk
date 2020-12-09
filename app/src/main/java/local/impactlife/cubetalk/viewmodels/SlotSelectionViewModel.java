package local.impactlife.cubetalk.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import local.impactlife.cubetalk.models.get_booking_time_slots.TimeSlotsResponse;

public class SlotSelectionViewModel extends ViewModel {

    private MutableLiveData<TimeSlotsResponse.Data> mTimeSlots = new MutableLiveData<>();
    private String mSelectedTimeSlot;

    public LiveData<TimeSlotsResponse.Data> getTimeSlots() {
        return mTimeSlots;
    }

    public String getSelectedTimeSlot() {
        return mSelectedTimeSlot;
    }

    public void setSelectedTimeSlot(String selectedTimeSlot) {
        this.mSelectedTimeSlot = selectedTimeSlot;
    }
}
