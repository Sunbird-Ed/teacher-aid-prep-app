package com.gurug.education.data.repository.remote;

import com.gurug.education.data.model.request.framework.RequestFramework;
import com.gurug.education.data.model.request.lessonplans.RequestLessonPlan;
import com.gurug.education.data.model.response.facetsearh.ResponseFacetSearch;
import com.gurug.education.data.model.response.frameworkdetail.ResponseFramwork;
import com.gurug.education.data.model.response.framwork.ResponseFramework;
import com.gurug.education.data.model.response.lessonplan.ResponseLessonPlan;
import com.gurug.education.data.model.response.settings.ResponseCustFramework;
import com.gurug.education.data.model.response.settings.ResponseSettings;
import com.gurug.education.data.model.response.teachingmethod.ReponseMethodBody;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodDetail;
import com.gurug.education.data.model.response.teachingmethod.ResponseMethodResourcesDetails;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IAPIInterface {


    @GET("api/data/v1/system/settings/list")
    Single<ResponseSettings> getSettings();

    @GET("api/framework/v1/read/{framworkId}/")
    Single<ResponseFramwork> getFramework(@Path("framworkId") String framworkId);

    @GET("api/channel/v1/read/{custId}/")
    Single<ResponseCustFramework> getFrameworkByCustId(@Path("custId") String custId);

    @POST("api/framework/v1/list")
    Single<ResponseFramework> getFrameworks(@Body RequestFramework requestFramework);

    @POST("api/content/v1/search")
    Single<ResponseLessonPlan> getLessonPlans(@Body RequestLessonPlan requestLessonPlan);

    @POST("api/content/v1/search")
    Single<ResponseFacetSearch> getFacetSearch(@Body RequestLessonPlan requestLessonPlan);

    //https://staging.open-sunbird.org/action/content/v3/hierarchy/do_21267634840004198414016?mode=edit

    @GET("action/content/v3/hierarchy/{content_id}/")
    Single<ResponseMethodDetail> getMethodDetails(@Path("content_id") String content_id, @Query("mode") String mode);

    @GET("action/content/v3/hierarchy/{content_id}/")
    Single<ResponseMethodResourcesDetails> getResources(@Path("content_id") String content_id, @Query("mode") String mode);

    //https://staging.open-sunbird.org/api/content/v1/read/do_21267634897281843214017?mode=edit&fields=
    //duration,methodtype,body,name,versionKey,description,board,gradeLevel,subject,medium

    @GET("api/content/v1/read/{content_id}/")
    Single<ReponseMethodBody> getMethodBody(@Path("content_id") String content_id, @Query("mode") String mode, @Query("fields") String fields);


}
