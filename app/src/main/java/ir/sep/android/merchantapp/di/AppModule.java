package ir.sep.android.merchantapp.di;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;
import ir.sep.android.merchantapp.data.room.AppDatabase;
import ir.sep.android.merchantapp.data.room.dao.InboxDao;


@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {

    @Provides
    @Singleton
    public static AppDatabase getDatabase(@ApplicationContext Context context) {
        return Room
                .databaseBuilder(context,AppDatabase.class,"App.DB")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

    }

    @Singleton
    @Provides
    public InboxDao provideInboxDao(AppDatabase appDatabase)
    {
       return appDatabase.getInboxDao();

    }

}