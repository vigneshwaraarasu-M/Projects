/* This is Executer phase
 * Executer - The executer will execute the query by passing it to various phases
 * This executer phase has three stages Queryparser,normalizer and optimizer
 * Queryparser - This function will parse the query and check the syntax in the query
 * normalizer - This function will normalize the query by identifying the valid tablename,column, conditions
 * optimize - This function will develop the execution plan pased on the parser results
 */
package com.queryengine.executer;

import java.io.IOException;

import com.queryengine.normalizer.Normalizer;
import com.queryengine.normalizer.Normalizer_definition;
import com.queryengine.optimizer.Optimizer;
import com.queryengine.optimizer.Optimizer_definition;
import com.queryengine.parser.Parser;
import com.queryengine.parser.Parser_definition;
import com.queryengine.parser.Queryparser;

public class Executer implements Executer_definition{

	//Will parse the query and returns Queryparser object which contains table, column and condition details
	public Queryparser parse(String query) {
		
		
		Parser_definition parse = new Parser();
		
		//The query syntax is validated
		if (parse.syntax_checker(query)) {
			//This phase will return query parser details. if query is invalid it will pass null
			Queryparser table_details = parse.keyword_identifier(query);
			return table_details;
		} else {

			return null;
		}
	}

	// Will normalize the query to validate the Queryparser objects
	public void normalize(Queryparser table_def) throws IOException {
		Normalizer_definition normalize = new Normalizer();
		
		//Check the table name. If it is valid true is returned
		if (normalize.check_table(table_def.getTable().trim())) {
			//Check the column fields . If it is valid true is returned
			if (normalize.check_column(table_def.getColumns().trim(), table_def
					.getTable().trim())) {
				//Check the where condition fileds. If it is valid true is returned
				if (!(table_def.getCondition().trim().equals(""))) {
					if (normalize.condition_check(table_def.getCondition()
							.trim(), table_def.getTable().trim())) {
						// System.out.println("valid condition");
					} else {
						System.out.println("invalid condition");
						System.exit(0);
					}
				}
			}else {
				System.out.println("invalid column in table");
				System.exit(0);
			}
		} else
		{
			System.out.println("invalid table name");
			System.exit(0);
		}

	}

	public void optimize(Queryparser table_def) throws IOException {
		Optimizer_definition optimize = new Optimizer();
		//This function will execute the query based on the queryparser objects
		optimize.execution_plan(table_def);
	}

}
