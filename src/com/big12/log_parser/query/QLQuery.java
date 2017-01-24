package com.big12.log_parser.query;

import java.util.Set;

public interface QLQuery
{
    Set<Object> execute(String query);
}
