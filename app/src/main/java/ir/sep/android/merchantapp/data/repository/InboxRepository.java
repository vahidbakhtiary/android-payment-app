package ir.sep.android.merchantapp.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import ir.sep.android.merchantapp.data.entities.Inbox;
import ir.sep.android.merchantapp.data.room.dao.InboxDao;

public class InboxRepository {

    private InboxDao inboxDao;


    @Inject
    public InboxRepository(InboxDao inboxDao) {
        this.inboxDao = inboxDao;
    }

    public LiveData<List<Inbox>> getAllInbox()
    {
       return this.inboxDao.getAllInbox();
    }


    public void insert(Inbox inbox)
    {
        this.inboxDao.insert(inbox);
    }

    public void updateToRead()
    {
        this.inboxDao.updateToRead();
    }
    public LiveData<Integer> getUnReadMessage()
    {
        return this.inboxDao.getUnReadMessage();
    }
}
