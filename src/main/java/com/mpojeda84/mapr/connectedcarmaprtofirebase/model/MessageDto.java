package com.mpojeda84.mapr.connectedcarmaprtofirebase.model;

public class MessageDto {
    private String date;
    private String message;
    private String severity;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                ", date='" + date + '\'' +
                ", message='" + message + '\'' +
                ", severity='" + severity + '\'' +
                '}';
    }
}
