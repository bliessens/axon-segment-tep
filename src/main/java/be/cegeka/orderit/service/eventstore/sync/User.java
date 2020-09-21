package be.cegeka.orderit.service.eventstore.sync;

import java.io.Serializable;

class User implements Serializable {

    private final String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
