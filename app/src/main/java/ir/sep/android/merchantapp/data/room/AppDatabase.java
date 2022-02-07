package ir.sep.android.merchantapp.data.room;


import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dagger.hilt.android.qualifiers.ApplicationContext;
import ir.sep.android.merchantapp.data.entities.Inbox;
import ir.sep.android.merchantapp.data.room.dao.InboxDao;

@Database(entities = Inbox.class,version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract InboxDao getInboxDao();

}
