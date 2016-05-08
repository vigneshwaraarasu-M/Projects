/* This is pojo class for Query parser. This contains getter and setter methods for query parse which 
 * contains table name, columns and conditions
 */

package com.queryengine.parser;

public class Queryparser {
	private String columns;
	private String table;
	private String condition;

	public String getColumns() {
		return columns;
	}

	public void setColumns(String columns) {
		this.columns = columns;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

}
