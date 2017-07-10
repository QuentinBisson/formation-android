package com.qbisson.formationandroid.model;

import java.util.Objects;

public class Message {

    private String uuid;
    private String login;
    private String message;

    public static Builder builder() {
        return new Builder();
    }

    public String getUuid() {
        return uuid;
    }

    public String getLogin() {
        return login;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return uuid == message.uuid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Message{" +
                "uuid=" + uuid +
                ", login='" + login + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public static class Builder {
        private Message building;

        public Builder() {
            building = new Message();
        }

        public Builder uuid(String uuid) {
            building.uuid = uuid;
            return this;
        }

        public Builder message(String message) {
            building.message = message;
            return this;
        }


        public Builder login(String login) {
            building.login = login;
            return this;
        }

        public Message build() {
            return building;
        }
    }
}
