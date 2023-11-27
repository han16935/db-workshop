package dto;

public class MemberDto {
    private int id;
    private String userId;
    private String passWord;

    public MemberDto(int id, String userId, String passWord) {
        this.id = id;
        this.userId = userId;
        this.passWord = passWord;
    }

    public MemberDto(String userId, String passWord){
        this.userId = userId;
        this.passWord = passWord;
    }

    public MemberDto(int id, String userId){
        this.id = id;
        this.userId = userId;
    }

    public String toString(){
        return this.id + " " + this.userId + " " + this.passWord;
    }

    public int getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassWord() {
        return passWord;
    }
}
