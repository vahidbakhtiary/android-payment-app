package ir.sep.android.merchantapp.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ir.sep.android.merchantapp.data.entities.Inbox;


@Dao
public interface InboxDao {

    @Insert
    void insert(Inbox inbox);

    @Query("SELECT * FROM Inbox ORDER BY id desc")
    LiveData<List<Inbox>> getAllInbox();


    @Query("update Inbox set state=0")
    public void updateToRead();

    @Query("select COUNT(*) from Inbox where state>0 ")
    LiveData<Integer> getUnReadMessage();

}
