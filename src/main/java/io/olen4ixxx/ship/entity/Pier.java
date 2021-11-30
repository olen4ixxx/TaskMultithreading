package io.olen4ixxx.ship.entity;

import io.olen4ixxx.ship.util.PierIdGenerator;

import java.util.Objects;

public class Pier {
    private final int pierId;
    private boolean free;

    public Pier() {
        free = true;
        pierId = PierIdGenerator.generateId();
    }

    public boolean isFree() {
        return free;
    }

    public void setFree() {
        this.free = true;
    }

    public void setBusy() {
        this.free = false;
    }

    public int getPierId() {
        return pierId;
    }

    @Override // TODO: 28.11.2021
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pier pier = (Pier) o;
        return free == pier.free;
    }

    @Override // TODO: 28.11.2021  
    public int hashCode() {
        return Objects.hash(free);
    }
}
