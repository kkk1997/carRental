package carrental.domain;

public enum Status {
    EXPIRED,
    SOON,
    OK,
    WRONG;

    public boolean isExpire(Status status){
        return status.equals(EXPIRED) || status.equals(SOON);
    }
}
