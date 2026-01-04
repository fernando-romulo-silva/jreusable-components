package org.application_example.application.query.entity.paged;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.ObjectUtils;

public class PageList<T> {

    private final int size;

    private final int index;

    private final List<T> data;

    private final List<T> fullData;

    private List<List<T>> pages;

    public PageList(final int size, final int index, final List<T> fullData) {
        this.size = size;
        this.index = index;
        this.fullData = fullData;
        this.pages = ListUtils.partition(fullData, size);
        this.data = ObjectUtils.isNotEmpty(pages) ? pages.get(index) : new ArrayList<>();
    }

    public PageList<T> next() {

        if (hasNext()) {
            return new PageList<>(this.size, this.index + 1, this.fullData);
        }

        return this;
    }

    public PageList<T> previous() {
        if (hasPrevious()) {
            return new PageList<>(this.size, this.index - 1, this.fullData);
        }

        return this;
    }

    public int getSize() {
        return size;
    }

    public int index() {
        return index;
    }

    public int getTotal() {
        return fullData.size();
    }

    public List<T> getData() {
        return data;
    }

    public boolean hasNext() {
        return index < pages.size();
    }

    public boolean hasPrevious() {
        return index > 1;
    }
}
