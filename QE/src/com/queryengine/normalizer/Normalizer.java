/* This Normailizer will check the table name, column name and condition details 
 * 
 */
package com.queryengine.normalizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.queryengine.tabledefinition.Table;
import com.queryengine.tabledefinition.Table_def;

public class Normalizer implements Normalizer_definition {
	public Table_def table = new Table_def();
	public int nonmatch = 0;

	//Will check the table name. If it is valid true is returned
	@Override
	public boolean check_table(String table_name) {
		// TODO Auto-generated method stub

		try {
			table.data_load(table_name);
			return true;
		} catch (Exception e) {
			System.out.println(e.toString());
			return false;
		}

	}

	//Will check the column name. If it is valid true is returned
	@Override
	public boolean check_column(String columns, String table_name)
			throws IOException {
		// TODO Auto-generated method stub
		int match = 0;
		//If the column is match the counter is set to 1. The condition will be checked only if it not *
		if (!columns.equals("*")&&(!columns.contains("("))) {
			Table user_table = table.data_load(table_name);
			//Get the column details from the hashmap and check the values
			ArrayList<String> table_column = new ArrayList<String>(user_table
					.getTable_header().values());
			Iterator<String> column = table_column.iterator();

			while (column.hasNext()) {
				String col = column.next();
				
				if (col.equals(columns)) {
					match = 1;
				}
			}

		}
		else
		{
			return true;
		}
		
		if (match == 1)
			return true;
		else
			return false;

	}

	//This funciton will validate the conditions. And analyze the column names after WHERE statement
	@Override
	public boolean condition_check(String conditions, String tablename)
			throws IOException {
		// TODO Auto-generated method stub

		//REGEX to identify the braces
		String pattern = "(.*)(AND|OR).*\\((.*)\\).*";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.

		Matcher m = r.matcher(conditions);
		if (m.find()) {
			//Now again split the condition whichever is coming inside braces and before AND OR condition
			condition_check(m.group(1).trim(), tablename);
			condition_check(m.group(3).trim(), tablename);
		} else {
			//REGEX to identify AND OR condition
			pattern = "(.*)(AND|OR)(.*)";
			// Create a Pattern object
			r = Pattern.compile(pattern);

			// Now create matcher object.

			m = r.matcher(conditions);
			if (m.find()) {
				//If AND or OR condition is found. Identify it and split it and again validate it by passing it in this function
				condition_check(m.group(1).trim(), tablename);
				condition_check(m.group(3).trim(), tablename);
			} else {
				//Identify the equal, less than or greater than symbol in the condition
				pattern = "(.*)(<|>|=)(.*)";
				// Create a Pattern object
				r = Pattern.compile(pattern);

				// Now create matcher object.
				m = r.matcher(conditions);

				if (m.find()) {
					String col = m.group(1).trim();

					//Parse the column name appearing befor the condition and check it
					if (check_column(col, tablename)) {

						nonmatch = 0;
					} else {
						System.out.println("invalid column named " + col);
						System.exit(0);
					}
				} else {

					System.out.println("invalid condition");
					System.exit(0);
				}
			}

		}
		if (nonmatch == 0) {
			return true;
		} else {
			return false;
		}

	}

}
