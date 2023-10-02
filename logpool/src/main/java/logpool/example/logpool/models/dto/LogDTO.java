package logpool.example.logpool.models.dto;

import logpool.example.logpool.models.Log;

import java.io.Serializable;
import java.util.Date;

public class LogDTO<T> implements Serializable {
    private String id;
    private String action;
    private Date date = new Date();
    private T object;
    private String objectType;

    public LogDTO() {
    }

    public LogDTO(String id, String action, Date date, T object, String objectType) {
        this.id = id;
        this.action = action;
        this.date = date;
        this.object = object;
        this.objectType = objectType;
    }

    public LogDTO(Log log) {
        this.id = log.getId();
        this.action = log.getAction();
        this.date = log.getDate();
        this.object = (T)log.getObject();
        this.objectType = log.getObjectType();
    }

    public Log toLog() {
        String id;
        var log = new Log<T>();
        log.setAction(this.action);
        log.setDate(this.date);
        log.setObject(this.object);

        if(this.id != null && !this.id.isBlank())
            log.setId(this.id);

        if(this.objectType != null && !this.objectType.isBlank())
            log.setObjectType(this.objectType);

        return log;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    @Override
    public String toString() {
        return "LogDTO{" +
                "id='" + id + '\'' +
                ", action='" + action + '\'' +
                ", date=" + date +
                ", object=" + object +
                ", objectType='" + objectType + '\'' +
                '}';
    }
}
