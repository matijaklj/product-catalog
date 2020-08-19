package com.demo.pc.read.queries;

public class FetchProductListQuery {

    private final int skip;
    private final int take;
    private final String search;

    public FetchProductListQuery(int skip, int take, String search) {
        this.skip = skip;
        this.take = take;
        if (search != null && search.equals(""))
            this.search = null;
        else
            this.search = search;
    }

    public int getSkip() {
        return skip;
    }

    public int getTakeCount() {
        return take;
    }

    public String getSearch() {
        return search;
    }
}
