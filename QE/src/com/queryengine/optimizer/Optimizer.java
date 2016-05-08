package com.queryengine.optimizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.queryengine.parser.Queryparser;
import com.queryengine.tabledefinition.Table;
import com.queryengine.tabledefinition.Table_def;

public class Optimizer implements Optimizer_definition {

	//This function will evaluate conditions and returns only row number. This row number will be merged with the resultset to filter out the values
	@Override
	public ArrayList<Integer> condition_evaluator(String conditions,
			String tablename, Table table) throws IOException {
		// TODO Auto-generated method stub

		//REGEX to identify the braces
		String pattern = "(.*)(AND|OR).*\\((.*)\\).*";
		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);
		//To store the line number index
		ArrayList<Integer> column_position = new ArrayList<Integer>();
		// Now create matcher object.

		Matcher m = r.matcher(conditions);
		if (m.find()) {
			//If braces is found. Again we need to break the conditions and evaluate it. So it is iterated with the same function
			ArrayList<Integer> first = condition_evaluator(m.group(1).trim(),
					tablename, table);
			ArrayList<Integer> second = condition_evaluator(m.group(3).trim(),
					tablename, table);
			// If AND function is found, then we need intersect the two resultset row number
			if (conditions.contains("AND")) {
				column_position = intersection(first, second);
				return column_position;
			}
			// If OR function is found, then we need to union the two resultset row number
			if (conditions.contains("OR")) {
				column_position = union(first, second);
				return column_position;
			}
		} else {
			//REGEX to identify AND OR condition
			pattern = "(.*)(AND|OR)(.*)";

			column_position = new ArrayList<Integer>();
			// Create a Pattern object
			r = Pattern.compile(pattern);

			// Now create matcher object.

			m = r.matcher(conditions);
			if (m.find()) {
				//If condition is found. Again we need to break the conditions and evaluate it. So it is iterated with the same function
				ArrayList<Integer> first = condition_evaluator(m.group(1)
						.trim(), tablename, table);
				ArrayList<Integer> second = condition_evaluator(m.group(3)
						.trim(), tablename, table);
				// If AND function is found, then we need intersect the two resultset row number
				if (conditions.contains("AND")) {
					column_position = intersection(first, second);
					return column_position;
				}
				// If OR function is found, then we need to union the two resultset row number
				if (conditions.contains("OR")) {
					column_position = union(first, second);
					return column_position;
				}
			} else {
				// To identify the condition
				pattern = "(.*)(<|>|=)(.*)";
				// Create a Pattern object
				r = Pattern.compile(pattern);

				// Now create matcher object.
				m = r.matcher(conditions);

				if (m.find()) {
					//condition column name
					String condition_col = m.group(1);
					//condition value name
					String condition_val = m.group(3);
					//for equal to condition in WHERE statement
					if (conditions.contains("=")) {
						column_position = new ArrayList<Integer>();
						// This will retrieve all the line number for the given condition. The line number arraylist will display all the line number details for the given condition
						HashMap<String, ArrayList<Integer>> table_column = table
								.getRow_number().get(condition_col.trim());
						try {
							column_position.addAll(table_column
									.get(condition_val));
						} catch (NullPointerException e) {
							// If no condition is matched then index position is zero. Since zero will be the column header. this value will be all empty
							column_position.add(0);
						}
						return column_position;
					}
					//for less than condition in WHERE statement
					if (conditions.contains("<")) {
						column_position = new ArrayList<Integer>();
						HashMap<String, ArrayList<Integer>> table_column = table
								.getRow_number().get(condition_col.trim());
						Set<String> allvalues = table_column.keySet();
						for (String individual_values : allvalues) {
							// This will retrieve all the line number for the less than condition. The line number arraylist will display all the line number details for the given condition
							try {
								float key = Float.parseFloat(individual_values
										.trim());
								if (key < Float
										.parseFloat(condition_val.trim()))
									column_position.addAll(table_column
											.get(individual_values.trim()));
							} catch (NumberFormatException nu) {
								System.out
										.println("This condition is not valid for string");
							}
						}
						return column_position;
					}
					//for greater than condition in WHERE statement
					if (conditions.contains(">")) {
						column_position = new ArrayList<Integer>();
						HashMap<String, ArrayList<Integer>> table_column = table
								.getRow_number().get(condition_col.trim());

						Set<String> allvalues = table_column.keySet();
						for (String individual_values : allvalues) {
							// This will retrieve all the line number for the greater than condition. The line number arraylist will display all the line number details for the given condition
							try {
								float key = Float.parseFloat(individual_values
										.trim());
								if (key > Float
										.parseFloat(condition_val.trim())) {

									column_position.addAll(table_column
											.get(individual_values.trim()));
								}

							} catch (NumberFormatException nu) {
								System.out
										.println("This condition is not valid for string");
							}
						}
						return column_position;
					}

				} else {

					System.out.println("put proper comparision condition");
					System.exit(0);
				}
			}

		}
		return null;

	}

	// This function is used for or condition. Result set will be unioned
	public ArrayList<Integer> union(ArrayList<Integer> first,
			ArrayList<Integer> second) {
		first.addAll(second);
		// After adding the resultset the unique values should be obtained by
		// passing it to the set
		Set<Integer> unique_and = new HashSet<Integer>(first);
		ArrayList<Integer> positions = new ArrayList<Integer>(unique_and);
		return positions;

	}

	// This function is used for And condition. Result set will be interctioned
	public ArrayList<Integer> intersection(ArrayList<Integer> first,
			ArrayList<Integer> second) {
		first.retainAll(second);
		return first;

	}

	// In this function all the values in the column are retrieved along with
	// the row number
	@Override
	public HashMap<Integer, String> column_function(String column,
			String table_name, String condition, Table table)
			throws IOException {
		// TODO Auto-generated method stub

		HashMap<Integer, String> value_map = new HashMap<Integer, String>();
		ArrayList<String> value = new ArrayList<String>();

		// If * is used, we need to print all the values
		if (column.trim().equals("*")) {

			// Get all the value in each and every row. These values are stored
			// along with the row number
			value = new ArrayList<String>(table.getRow_values().values());
			int index = 1;
			for (String ind_values : value) {
				index++;
				value_map.put(index, ind_values);
			}
			return value_map;

		} else if (column.trim().contains("(")) {

			// If we found any braces inside column, then it should be aggregate
			// functions
			// This pattern matches only MAX and UNIQ functions
			String pattern = "(MAX|UNIQ)\\((.*)\\)";
			// Create a Pattern object
			Pattern r = Pattern.compile(pattern);

			// Now create matcher object.

			Matcher m = r.matcher(column.trim());
			if (m.find()) {
				String col = m.group(2).trim();
				// If MAX function is used. We need to go to max. and UNIQ
				// function will go to uniq funciton This will return hashmap
				// with -1 as key
				if (column.trim().contains("MAX"))
					value_map = max(table, col, condition, table_name.trim());
				if (column.trim().contains("UNIQ"))
					value_map = uniq(table, col, condition, table_name.trim());
				return value_map;
			} else {
				System.out
						.println("Only MAX and UNIQ function is implemented. Please use only MAX function");
				System.exit(0);
			}
		} else {
			// If aggregate functions and * is not used. Then we need to extract
			// all the values from the column
			value = table.getColumn_values().get(column.trim());
			int index = 1;
			for (String ind_values : value) {
				index++;
				value_map.put(index, ind_values);
			}
			return value_map;

		}
		return value_map;

	}

	private HashMap<Integer, String> uniq(Table table, String col,
			String condition, String table_name) throws IOException {
		// TODO Auto-generated method stub
		ArrayList<String> value = new ArrayList<String>();
		HashMap<Integer, String> value_map = new HashMap<Integer, String>();
		/*
		 * ArrayList<Integer> filter = condition_evaluator(condition,
		 * table_name, table);
		 */
		ArrayList<Integer> filter = new ArrayList<Integer>();

		// Before aggregating the values, where condition need to be evaluated.
		// So the index position from the where condition are evaluated
		if (!(condition.trim().equals(""))) {
			filter = condition_evaluator(condition, table_name, table);
		} else {
			filter = new ArrayList<Integer>(table.getRow_values().keySet());
		}
		//Get the index position of the column
		int index = table.getTable_index().get(col.trim());
		// From the values. Iterate towards all the lines in file and split it. pick only the corresponding column field based on the index position
		for (Integer i : filter) {
			try
			{
			String line_value = table.getRow_values().get(i);
			String[] col_value = line_value.split(",");
			value.add(col_value[index]);
			}
			catch(NullPointerException ne)
			{
				System.out.println("No value for this condition");
				System.exit(0);
			}
		}
		//Identify the distinct values by passing it to set
		Set<String> dist_values = new HashSet<String>(value);
		String distinct_values = dist_values.toString().replaceAll(",", "\n").replace("[", " ").replaceAll("]", " ");
		value_map.put(-1, distinct_values);

		return value_map;
	}

	public HashMap<Integer, String> max(Table table, String col,
			String condition, String table_name) throws IOException {
		ArrayList<String> value = new ArrayList<String>();
		HashMap<Integer, String> value_map = new HashMap<Integer, String>();
		ArrayList<Integer> filter = new ArrayList<Integer>();

		// Before aggregating the values, where condition need to be evaluated.
		// So the index position from the where condition are evaluated
		if (!(condition.trim().equals(""))) {
			filter = condition_evaluator(condition, table_name, table);
		} else {
			filter = new ArrayList<Integer>(table.getRow_values().keySet());
		}

		float max_value = 0.0F;
		try {

			//Get the index position of the column
			int index = table.getTable_index().get(col.trim());
			ArrayList<Float> value_list = new ArrayList<Float>();
			// From the values. Iterate towards all the lines in file and split it. pick only the corresponding column field based on the index position
			for (Integer i : filter) {
				try
				{
				String line_value = table.getRow_values().get(i);
				String[] col_value = line_value.split(",");
				value_list.add(Float.valueOf(col_value[index]));
				}
				catch(NullPointerException ne)
				{
					System.out.println("No value for this condition");
					System.exit(0);
				}
			}
			//Identify the max value in the collection
			Object obj = Collections.max(value_list);
			max_value = (float) obj;
			value.add(Float.toString(max_value));

		} catch (NumberFormatException nu) {
			System.out
					.println("Please select the number datatype column to find MAX");
			System.exit(0);
		}
		value_map.put(-1, Float.toString(max_value));
		return value_map;
	}

	@Override
	public void execution_plan(Queryparser table_values) throws IOException {
		// TODO Auto-generated method stub
		String column = table_values.getColumns();
		String condition = table_values.getCondition();
		String table_name = table_values.getTable();
		ArrayList<Integer> resultset2 = new ArrayList<Integer>();

		// In execution plan load the file with the table name
		Table_def td = new Table_def();
		Table table = td.data_load(table_name.trim());

		// Retrieve the data from the corresponding column with row index
		// position
		HashMap<Integer, String> resultset1 = column_function(column,
				table_name, condition, table);
		// If the aggregation functions like MAX or UNIQ is implemented. the key
		// value will be -1. So print those values
		if (resultset1.keySet().contains(-1)) {
			System.out.println(column);
			for (Integer result : resultset1.keySet())
				System.out.println(resultset1.get(result));
			System.exit(0);
		}

		// The filter condition fields need to be extracted.Only row number of
		// the filtered field are stored in the resultset2
		if (!condition.trim().equals("")) {
			resultset2 = condition_evaluator(condition, table_name, table);
		} else {
			resultset2 = new ArrayList<Integer>(resultset1.keySet());
		}

		// From the column data we need to print only the filtered field index
		// values
		if (!column.trim().equals("*"))
			System.out.println(column);
		
		for (Integer result : resultset2)
			System.out.println(resultset1.get(result));
		System.exit(0);
	}

}
