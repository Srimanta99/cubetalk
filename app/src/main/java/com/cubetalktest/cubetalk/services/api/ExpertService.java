package com.cubetalktest.cubetalk.services.api;

import com.cubetalktest.cubetalk.models.expert_cancel_status_update.ExperCancelStatusUpdateResponse;
import com.cubetalktest.cubetalk.models.expert_cancel_status_update.ExpertCancelStatusUpdateRequest;
import com.cubetalktest.cubetalk.models.expert_document_upload.ExpertDocumentUploadResponseBody;
import com.cubetalktest.cubetalk.models.expert_review_refund.ExpertRefundRequest;
import com.cubetalktest.cubetalk.models.expert_review_refund.ExpertRefundResponse;
import com.cubetalktest.cubetalk.models.get_expert_documents.ExpertDocumentsFetchResponseBody;
import com.cubetalktest.cubetalk.models.get_expert_status.ExpertStatusResponseBody;
import com.cubetalktest.cubetalk.models.get_expert_terms.ExpertTermsResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ExpertService {

    @Multipart
    @POST("api/users/expert-documents-upload/{user_id}")
    Call<ExpertDocumentUploadResponseBody> uploadExpertDocument(
            @Header ("token") String token,
            @Path("user_id") String id,
            @Part("expert_cv_title") RequestBody expertCvTitle,
            @Part MultipartBody.Part expertCvDocument,
            @Part("expert_kyc_doc_1_title") RequestBody expertKycDocOneTitle,
            @Part MultipartBody.Part expertKycOneImage,
            @Part("expert_kyc_doc_2_title") RequestBody expertKycDocTwoTitle,
            @Part MultipartBody.Part expertKycTwoImage
    );

    @GET("api/users/expert-document-info/{user_id}")
    Call<ExpertDocumentsFetchResponseBody> getExpertDocuments(@Header ("token") String token,@Path("user_id") String id);

    @GET("api/users/expert-status/{user_id}")
    Call<ExpertStatusResponseBody> getExpertStatus( @Header ("token") String token, @Path("user_id")String mUserId);

    @GET("api/cms/{cms_id}")
    Call<ExpertTermsResponse> getExpertTerms(@Header("token") String token,@Path("cms_id")String id);

    @POST("api/bookings/user-refund-request/")
    Call<ExpertRefundResponse>  getExpertRefunds(@Header("token") String token,@Body ExpertRefundRequest expertRefund);

    @POST("api/bookings/update-booking-cancel-status/")
    Call<ExperCancelStatusUpdateResponse>  getExpertRefundrequestUpdate(@Header ("token") String token,@Body ExpertCancelStatusUpdateRequest expertCancelStatusUpdateRequest);
}
