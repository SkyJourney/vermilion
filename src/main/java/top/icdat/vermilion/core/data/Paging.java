package top.icdat.vermilion.core.data;

public class Paging {
    private int recordAmount;
    private int pageNum;

    public Paging(int recordAmount, int pageNum) {
        this.recordAmount = recordAmount;
        this.pageNum = pageNum;
    }

    public Paging() {
    }

    public static Paging ofFirstPage(int recordAmount) {
        return of(recordAmount, 1);
    }

    public static Paging of(int recordAmount, int pageNum) {
        return new Paging(recordAmount, pageNum);
    }

    public int getRecordAmount() {
        return recordAmount;
    }

    public void setRecordAmount(int recordAmount) {
        this.recordAmount = recordAmount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
