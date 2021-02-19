package cn.itcast.core.pojo.entity;

import java.io.Serializable;
import java.util.List;

public class PageResult implements Serializable {
    public String code;
    public Long total;
    public List rows;

    public PageResult() {
    }

    public PageResult(Long total, List rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult(String code, Long total, List rows) {
        this.code = code;
        this.total = total;
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
