package tw.hd.com.guessnum;

public class ShowData {
    private  int id;
    private String result;
    private String oneA;
    private String twoB;

    public ShowData(int id, String result, String oneA, String twoB) {
        this.id = id;
        this.result = result;
        this.oneA = oneA;
        this.twoB = twoB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getOneA() {
        return oneA;
    }

    public void setOneA(String oneA) {
        this.oneA = oneA;
    }

    public String getTwoB() {
        return twoB;
    }

    public void setTwoB(String twoB) {
        this.twoB = twoB;
    }
}
