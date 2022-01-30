package pojo;

public class Sma {

    int count;
    long total;

    public Sma(int i, long equity) {
        // TODO Auto-generated constructor stub
        count = i;
        total = equity;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void countUpdate() {
        ++count;
    }

    public void updateTotal(long equity) {
        total += equity;
    }

    @Override
    public String toString() {
        return "Sma [count=" + count + ", total=" + total + "]";
    }
}