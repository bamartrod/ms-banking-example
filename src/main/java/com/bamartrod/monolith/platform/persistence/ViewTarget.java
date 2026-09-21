package com.bamartrod.monolith.platform.persistence;


/**
 * Allow-list for Oracle views — prevents SQL injection by restricting view names.
 *
 * @author Brandon Martinez
 */
public enum ViewTarget {
    ACCOUNT("ACCOUNT_VIEW"),
    SALES("SALES_VIEW"),
    CASES("CASES_VIEW"),
    MARKETING("MARKETING_VIEW"),
    ENRICHMENT("ENRICHMENT_VIEW"),
    CLIENT("CLIENT_VIEW");

    private final String viewName;
    ViewTarget(String viewName) { this.viewName = viewName; }
    public String viewName() { return viewName; }
}
