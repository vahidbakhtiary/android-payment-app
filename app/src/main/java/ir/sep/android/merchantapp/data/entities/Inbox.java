package ir.sep.android.merchantapp.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Inbox {


  public enum MessageState
    {
        Read(0),
        UnRead(1);

        private int id;
        MessageState(int id) {
            this.id=id;
        }

        public int getId() {
            return id;
        }
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String time;
    private String date;
    private int state;

    @Ignore
    public Inbox(String title, String description, String time, String date, int state) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.date = date;
        this.state = state;
    }

    public Inbox(int id, String title, String description, String time, String date, int state) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.time = time;
        this.date = date;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
