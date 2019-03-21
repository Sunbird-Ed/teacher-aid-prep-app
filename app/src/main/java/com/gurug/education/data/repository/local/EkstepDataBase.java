package com.gurug.education.data.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.gurug.education.BuildConfig;
import com.gurug.education.data.model.response.lessonplan.Content;
import com.gurug.education.data.model.response.lessonplan.Converters;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethod;
import com.gurug.education.data.model.response.teachingmethod.ChildrenMethodResouces;
import com.gurug.education.data.model.response.teachingmethod.ContentMethodBody;

@Database(entities = {Content.class, ChildrenMethod.class, ContentMethodBody.class, ChildrenMethodResouces.class}, version = BuildConfig.DB_VERSION, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class EkstepDataBase extends RoomDatabase {
    public abstract IDatabaseInterface daoAccess();
}
