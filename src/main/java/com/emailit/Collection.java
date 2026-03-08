package com.emailit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Collection extends EmailitObject implements Iterable<EmailitObject> {

    private final List<EmailitObject> data;

    public Collection(Map<String, Object> values, List<EmailitObject> data) {
        super(values);
        this.data = data != null ? data : new ArrayList<>();
    }

    public List<EmailitObject> getData() {
        return Collections.unmodifiableList(data);
    }

    public int size() {
        return data.size();
    }

    public boolean hasMore() {
        return get("next_page_url") != null;
    }

    @Override
    public Iterator<EmailitObject> iterator() {
        return data.iterator();
    }
}
