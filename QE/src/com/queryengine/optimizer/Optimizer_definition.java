/* interface for optimizer phase */
package com.queryengine.optimizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.queryengine.parser.Queryparser;
import com.queryengine.tabledefinition.Table;
import com.queryengine.tabledefinition.Table_def;

public interface Optimizer_definition {
	public ArrayList<Integer> condition_evaluator(String conditions,
			String tablename, Table table) throws IOException;

	public HashMap<Integer, String> column_function(String column, String table_name,
			String condition, Table table) throws IOException;

	public void execution_plan(Queryparser table) throws IOException;

}
