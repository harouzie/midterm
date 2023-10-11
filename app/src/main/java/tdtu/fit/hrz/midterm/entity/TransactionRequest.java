package tdtu.fit.hrz.midterm.entity;

public enum TransactionRequest {
    ADD(100,"add"),
//    UPDATE(200, "update"),
    DISPLAY(300, "display");

    private int requestCode;
    private String action;
    private TransactionRequest(int code, String action){
        this.requestCode = code;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
