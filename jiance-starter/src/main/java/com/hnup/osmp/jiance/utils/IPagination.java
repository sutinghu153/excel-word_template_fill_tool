package com.hnup.osmp.jiance.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @author LiuHaoming
 * @description: TODO
 * @date 2020/3/30 19:40
 */
public interface IPagination<T> extends Serializable {

    List<T> getRecords();

    IPagination<T> setRecords(List<T> records);

    long getTotal();

    IPagination<T> setTotal(long total);

    long getSize();

    IPagination<T> setSize(long size);

    long getCurrent();

    IPagination<T> setCurrent(long current);
    
    Object getFldInfo();
    
    IPagination<T> setFldInfo(Object fldInfo);

    default long getPages() {
        if (this.getSize() == 0L) {
            return 0L;
        } else {
            long pages = this.getTotal() / this.getSize();
            if (this.getTotal() % this.getSize() != 0L) {
                ++pages;
            }

            return pages;
        }
    }
}
