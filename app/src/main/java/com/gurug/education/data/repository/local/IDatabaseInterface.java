package com.gurug.education.data.repository.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethodResouces;
import com.gurug.education.data.model.response.teachingmethod.ContentMethodBody;

import java.util.ArrayList;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IDatabaseInterface {
    @Insert(onConflict = REPLACE)
    void insertLessonPlans(ArrayList<Content> lessonPlans);

    @Insert(onConflict = REPLACE)
    void insertTeachingMethod(List<ChildrenMethod> teachingMethod);

    @Insert(onConflict = REPLACE)
    void insertMethodBody(ContentMethodBody contentMethodBody);

    @Insert(onConflict = REPLACE)
    void insertMethodResources(List<ChildrenMethodResouces> childrenMethodResouces);

    @Query("SELECT * FROM Content WHERE  board = :selectedBoard AND medium = :selectedMedium AND grade =:selectedClass AND subject=:selectedSubject")
    List<Content> getAllLessonPlans(String selectedBoard, String selectedMedium, String selectedClass, String selectedSubject);

    @Query("SELECT * FROM Content WHERE  isBookMarked = :isBookMarked AND board = :selectedBoard AND medium = :selectedMedium AND grade =:selectedClass AND subject=:selectedSubject")
    List<Content> getBookMarkedLessonPlans(boolean isBookMarked,String selectedBoard, String selectedMedium, String selectedClass, String selectedSubject);

    @Query("SELECT * FROM Content WHERE  isDone = :isDone AND board = :selectedBoard AND medium = :selectedMedium AND grade =:selectedClass AND subject=:selectedSubject")
    List<Content> getMarkAsDonePlans(boolean isDone,String selectedBoard, String selectedMedium, String selectedClass, String selectedSubject);

    @Query("SELECT * FROM ChildrenMethod WHERE pedagogyId = :contentId")
    List<ChildrenMethod> getMethodDetails(String contentId);

    @Query("SELECT * FROM ChildrenMethodResouces WHERE pedagogyId = :contentId")
    List<ChildrenMethodResouces> getResources(String contentId);

    @Query("SELECT * FROM ContentMethodBody WHERE identifier = :contentId")
    ContentMethodBody getMethodBody(String contentId);

    /*
    void insertServiceRequestLite(List<ServiceRequestLite> serviceRequestLites);

    @Query("SELECT * FROM ServiceRequestLite WHERE  Status = :status")
    List<ServiceRequestLite> getAllSR(String status);

    @Insert(onConflict = REPLACE)
    void insertServiceRequest(ServiceRequest serviceRequest);

    @Insert(onConflict = REPLACE)
    void insertUserData(UserData userData);

    @Query("SELECT * FROM UserData")
    UserData getsUserUserData();

    @Query("SELECT * FROM ServiceRequest WHERE  SRNumber = :srNume")
    ServiceRequest getSrDetails(String srNume);

    @Query("SELECT * FROM FCMNotification WHERE  SRNum = :srNumber AND status = :subStatus")
    FCMNotification getFCMNotification(String srNumber, String subStatus);

    @Query("SELECT * FROM FCMNotification WHERE  SRNum = :srNumber AND status = :subStatus")
    List<FCMNotification> getFCMNotifications(String srNumber, String subStatus);

    @Query("SELECT * FROM FCMNotification WHERE  SRNum = :srNumber")
    List<FCMNotification> getFCMNotificationsForSrNumber(String srNumber);

    @Insert(onConflict = REPLACE)
    void insertNotification(FCMNotification fcmNotification);

    @Query("SELECT * FROM FCMNotification")
    List<FCMNotification> getAllFCMNotifications();

    @Insert(onConflict = REPLACE)
    void updateFCMNotification(FCMNotification notification);

    @Delete
    void deleteFCMNotification(FCMNotification notification);

    @Query("DELETE FROM FCMNotification")
    void deleteAllNotifications();

    @Insert(onConflict = REPLACE)
    void insertNotifications(ArrayList<FCMNotification> fcmNotifications);
     */
}
