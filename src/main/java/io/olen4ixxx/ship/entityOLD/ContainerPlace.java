package io.olen4ixxx.ship.entityOLD;

public class ContainerPlace {
    private boolean free;

    public ContainerPlace(boolean free) {
        this.free = free;
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
}
