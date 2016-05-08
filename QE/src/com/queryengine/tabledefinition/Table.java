/* This is the pojo class for Table object. This object contains getters and setters for all the hashmap
 * These hashmap will be used during run time of the query
 */
package com.queryengine.tabledefinition;

import java.util.ArrayList;
import java.util.HashMap;

public class Table {

	private HashMap<Integer, String> table_header = new HashMap<Integer, String>();
	private HashMap<String, Integer> table_index = new HashMap<String, Integer>();
	private HashMap<String, ArrayList> column_values = new HashMap<String, ArrayList>();
	private HashMap<String, HashMap<String, ArrayList<Integer>>> row_number = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
	private HashMap<Integer, String> row_values = new HashMap<Integer, String>();

	public HashMap<Integer, String> getTable_header() {
		return table_header;
	}

	public void setTable_header(HashMap<Integer, String> table_header) {
		this.table_header = table_header;
	}

	public HashMap<String, ArrayList> getColumn_values() {
		return column_values;
	}

	public void setColumn_values(HashMap<String, ArrayList> column_values) {
		this.column_values = column_values;
	}

	public HashMap<String, HashMap<String, ArrayList<Integer>>> getRow_number() {
		return row_number;
	}

	public void setRow_number(
			HashMap<String, HashMap<String, ArrayList<Integer>>> row_number) {
		this.row_number = row_number;
	}

	public HashMap<Integer, String> getRow_values() {
		return row_values;
	}

	public void setRow_values(HashMap<Integer, String> row_values) {
		this.row_values = row_values;
	}

	public HashMap<String, Integer> getTable_index() {
		return table_index;
	}

	public void setTable_index(HashMap<String, Integer> table_index) {
		this.table_index = table_index;
	}

}
