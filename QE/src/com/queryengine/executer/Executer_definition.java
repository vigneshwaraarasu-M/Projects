package com.queryengine.executer;

import java.io.IOException;

import com.queryengine.parser.Queryparser;

public interface Executer_definition {

	public Queryparser parse(String query);
	public void normalize(Queryparser table_def) throws IOException;
	public void optimize(Queryparser table_def) throws IOException;
}
