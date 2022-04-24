package com.hnup.osmp.jiance.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Collections;
import java.util.List;

/**
 * @author LiuHaoming
 * @description: TODO
 * @date 2020/3/30 19:43
 */
public class Pagination<T> implements IPagination<T> {

    private static final long serialVersionUID = 8545996863226528310L;

    private List<T> records;
    private long total;
    private long size;
    private long current;
    private Object fldInfo;

    public Pagination() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.size = 30L;
        this.current = 1L;
    }

    public Pagination(long current, long size) {
        this(current, size, 0L);
    }

    public Pagination(long current, long size, long total) {

        this.records = Collections.emptyList();
        this.total = total;
        this.size = size;
        this.current = current;
    }

    public boolean hasPrevious() {
        return this.current > 1L;
    }

    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public Pagination<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public Pagination<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.size;
    }

    @Override
    public Pagination<T> setSize(long size) {
        this.size = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public Pagination<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
	public Object getFldInfo() {
		return this.fldInfo;
	}

	@Override
	public IPagination<T> setFldInfo(Object fldInfo) {
		this.fldInfo = fldInfo;
        return this;
	}

	/**
     * @description: MybatisPlus分页对象转通用分页
     * @param page
     * @return
     * @throws
     * @author LiuHaoming
     * @date 2020/3/31 8:28
     */
    public Pagination(IPage<T> page) {
        this.setCurrent(page.getCurrent())
                .setSize(page.getSize())
                .setTotal(page.getTotal())
                .setRecords(page.getRecords());
    }

}
