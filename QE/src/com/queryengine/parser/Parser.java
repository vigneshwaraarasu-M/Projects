/* This class has two funciton syntax checker and Queryparser to parse the query */

package com.queryengine.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser implements Parser_definition {

	//The obtained query is validated with regex. This function will verify the SELECT and FROM statement
	//This function will return false if the syntax is not matched
	@Override
	public boolean syntax_checker(String query) {
		// TODO Auto-generated method stub
		
		//regex to create identify select and from clause from SQL 
		String pattern = "(SELECT[\\w\\W]*?FROM[\\w\\W]*?)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(query);
		if (m.find()) {
			return true;
		} else {
			return false;
		}

	}

	
	//The obtained query is parsed with regex. It splits the table name,column,condition from query
	@Override
	public Queryparser keyword_identifier(String query) {
		// TODO Auto-generated method stub

		String pattern = "SELECT([\\w\\W]*?)FROM(.*)";
		String column = "";
		String table = "";
		String condition = "";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(query);
		if (m.find()) {
			//Identify the columns from the query
			column = m.group(1);
			
			String table_condition = m.group(2);
			String pattern_where = "(.*)WHERE(.*)";

			// Create a Pattern object
			r = Pattern.compile(pattern_where);

			// Now create matcher object.
			m = r.matcher(table_condition);

			if (m.find()) {
				//Identify the table name which will be before where condition
				table = m.group(1);
				//Identify the conditions which will be after where condition
				condition = m.group(2);
			} else {
				//If where condition is not found the entire valuw will be table name
				table = table_condition;
			}

		}
		//Set all the column name, table name, conditions to Queryparser object
		Queryparser qp = new Queryparser();
		qp.setColumns(column);
		qp.setCondition(condition);
		qp.setTable(table);
		return qp;
	}

}