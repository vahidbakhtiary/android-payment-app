package ir.sep.android.merchantapp.ui.inbox;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import ir.sep.android.merchantapp.data.entities.Inbox;
import ir.sep.android.merchantapp.data.repository.InboxRepository;

public class InboxViewModel extends ViewModel {

    private InboxRepository repository;
    private LiveData<List<Inbox>> liveData;

    @ViewModelInject
    @Inject
    public InboxViewModel(InboxRepository repository) {
        this.repository = repository;
    }




    public LiveData<List<Inbox>> getAllInbox()
    {
        return this.repository.getAllInbox();
    }

    public void insert(Inbox inbox)
    {
        this.repository.insert(inbox);
    }
    public void updateToRead()
    {
        this.repository.updateToRead();
    }

    public LiveData<Integer> getUnReadMessage()
    {
        return this.repository.getUnReadMessage();
    }


}
