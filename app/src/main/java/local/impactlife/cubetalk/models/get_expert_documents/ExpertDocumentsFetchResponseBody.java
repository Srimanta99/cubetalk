package local.impactlife.cubetalk.models.get_expert_documents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import local.impactlife.cubetalk.models.common.ExpertDocuments;

public class ExpertDocumentsFetchResponseBody {

    @Expose
    @SerializedName("data")
    private Data mData;

    @Expose
    @SerializedName("message")
    private String mMessage;

    @Expose
    @SerializedName("success")
    private boolean mSuccess;

    public Data getData() {
        return mData;
    }

    public void setData(Data mData) {
        this.mData = mData;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public boolean getSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public static class Data {
        @Expose
        @SerializedName("name")
        private String mName;
        @Expose
        @SerializedName("expert_documents")
        private ExpertDocuments mExpertDocuments;
        @Expose
        @SerializedName("_id")
        private String mId;

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            this.mName = name;
        }

        public ExpertDocuments getExpertDocuments() {
            return mExpertDocuments;
        }

        public void setExpertDocuments(ExpertDocuments expertDocuments) {
            this.mExpertDocuments = expertDocuments;
        }

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            this.mId = id;
        }
    }

//    public static class ExpertDocuments {
//        @Expose
//        @SerializedName("expert_cv")
//        private ExpertCv mExpertCv;
//        @Expose
//        @SerializedName("expert_kyc_doc_1")
//        private ExpertKycDocOne mExpertKycDocOne;
//        @Expose
//        @SerializedName("expert_kyc_doc_2")
//        private ExpertKycDocTwo mExpertKycDocTwo;
//
//        public ExpertCv getExpertCv() {
//            return mExpertCv;
//        }
//
//        public void setExpertCv(ExpertCv expertCv) {
//            this.mExpertCv = expertCv;
//        }
//
//        public ExpertKycDocOne getExpertKycDocOne() {
//            return mExpertKycDocOne;
//        }
//
//        public void setExpertKycDocOne(ExpertKycDocOne mExpertKycDocOne) {
//            this.mExpertKycDocOne = mExpertKycDocOne;
//        }
//
//        public ExpertKycDocTwo getExpertKycDocTwo() {
//            return mExpertKycDocTwo;
//        }
//
//        public void setExpertKycDocTwo(ExpertKycDocTwo expertkycDocTwo) {
//            this.mExpertKycDocTwo = expertkycDocTwo;
//        }
//    }

//    public static class ExpertCv {
//        @Expose
//        @SerializedName("title")
//        private String mTitle;
//        @Expose
//        @SerializedName("image")
//        private String mImage;
//
//        public String getTitle() {
//            return mTitle;
//        }
//
//        public void setTitle(String title) {
//            this.mTitle = title;
//        }
//
//        public String getImage() {
//            return mImage;
//        }
//
//        public void setImage(String image) {
//            this.mImage = image;
//        }
//    }

//    public static class ExpertKycDocOne {
//        @Expose
//        @SerializedName("title")
//        private String mTitle;
//        @Expose
//        @SerializedName("image")
//        private String mImage;
//
//        public String getTitle() {
//            return mTitle;
//        }
//
//        public void setTitle(String title) {
//            this.mTitle = title;
//        }
//
//        public String getImage() {
//            return mImage;
//        }
//
//        public void setImage(String image) {
//            this.mImage = image;
//        }
//    }

//    public static class ExpertKycDocTwo {
//        @Expose
//        @SerializedName("title")
//        private String mTitle;
//        @Expose
//        @SerializedName("image")
//        private String mImage;
//
//        public String getTitle() {
//            return mTitle;
//        }
//
//        public void setTitle(String title) {
//            this.mTitle = title;
//        }
//
//        public String getImage() {
//            return mImage;
//        }
//
//        public void setImage(String image) {
//            this.mImage = image;
//        }
//    }
}
