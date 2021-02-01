package com.cubetalktest.cubetalk.models.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpertDocuments {

    @Expose
    @SerializedName("expert_cv")
    private ExpertCv mExpertCv;

    @Expose
    @SerializedName("expert_kyc_doc_1")
    private ExpertKycDocOne mExpertKycDocOne;

    @Expose
    @SerializedName("expert_kyc_doc_2")
    private ExpertKycDocTwo mExpertKycDocTwo;

    public ExpertCv getExpertCv() {
        return mExpertCv;
    }

    public void setExpertCv(ExpertCv expertCv) {
        this.mExpertCv = expertCv;
    }

    public ExpertKycDocOne getExpertKycDocOne() {
        return mExpertKycDocOne;
    }

    public void setExpertKycDocOne(ExpertKycDocOne mExpertKycDocOne) {
        this.mExpertKycDocOne = mExpertKycDocOne;
    }

    public ExpertKycDocTwo getExpertKycDocTwo() {
        return mExpertKycDocTwo;
    }

    public void setExpertKycDocTwo(ExpertKycDocTwo expertkycDocTwo) {
        this.mExpertKycDocTwo = expertkycDocTwo;
    }
}
