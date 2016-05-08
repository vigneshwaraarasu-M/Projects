/* This class file will contain the table definitions. The table name and file location details are
 * available in table_mapping.properties file. The properties file key will be the table name and the properties file
 * value will be file location. During run time based on the table name the necessary files will be loaded into Hashmap
 *   
 */
package com.queryengine.tabledefinition;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class Table_def {
	public Table data_load(String tablename) throws IOException {
		//load the properties file
		Properties table_prop = new Properties();
		String line = "";
		int line_number = 0;
		HashMap<Integer, String> table_header = new HashMap<Integer, String>();
		HashMap<String, Integer> table_index = new HashMap<String, Integer>();
		HashMap<String, ArrayList> column_values = new HashMap<String, ArrayList>();
		HashMap<String, HashMap<String, ArrayList<Integer>>> row_number = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
		HashMap<Integer, String> row_values = new HashMap<Integer, String>();
		
		table_prop.load(new FileInputStream("./src/table_mapping.properties"));
		String file_location = table_prop.getProperty(tablename);
	
		// if the file is not able to be located, then user need to be verify table name or update properties file
		if(file_location == null)
		{
			System.out.println("please check your table name or update the table_mapping.properties document");
			System.exit(0);
		}
		
		//Read the file and store all the necessary details in hashmap
		BufferedReader br = new BufferedReader(new FileReader(new File(
				file_location)));
		while ((line = br.readLine()) != null) {
			line_number++;
			if (line_number == 1) {
				String[] split_line = line.split(",");
				for (int i = 0; i < split_line.length; i++) {
					//The first line of the csv will be the header. So store the column header with index position
					table_header.put(i, split_line[i]);
					table_index.put(split_line[i], i);
				}

			} else {
				//Iterate into each line of the file
				String[] split_line = line.split(",");
				
				for (int i = 0; i < split_line.length; i++) {
				//Get the column name based on the String index position
					String table_column = table_header.get(i).trim();
					try {
						//Store the column name as key and list of values as arraylist
						ArrayList<String> values_retrived = column_values
								.get(table_column);
						values_retrived.add(split_line[i]);
						column_values.put(table_column, values_retrived);
					} catch (NullPointerException e) {
						ArrayList<String> values_retrived = new ArrayList<String>();
						values_retrived.add(split_line[i]);
						column_values.put(table_column, values_retrived);
					}

					try {
						HashMap<String, ArrayList<Integer>> values_position = row_number
								.get(table_column);
						try {
							//Store all the values with row number with value as key and list of row number as values
							ArrayList<Integer> position = values_position
									.get(split_line[i].trim());
							position.add(line_number);
							values_position.put(split_line[i].trim(), position);
						} catch (NullPointerException e) {
							ArrayList<Integer> position = new ArrayList<Integer>();
							position.add(line_number);
							values_position.put(split_line[i].trim(), position);
						}
						//Store the obtained hashmap in another hashmap with table column as key and other hashmap as value (Which is having field value as key and list of row number as values.
						//Row number is line number of the file
						row_number.put(table_column, values_position);
					} catch (NullPointerException e) {
						HashMap<String, ArrayList<Integer>> values_position = new HashMap<String, ArrayList<Integer>>();
						try {
							ArrayList<Integer> position = values_position
									.get(split_line[i].trim());
							position.add(line_number);
							values_position.put(split_line[i].trim(), position);
						} catch (NullPointerException ex) {
							ArrayList<Integer> position = new ArrayList<Integer>();
							position.add(line_number);
							values_position.put(split_line[i].trim(), position);
						}
						
						row_number.put(table_column, values_position);
					}

				}
				//Keep the line number as key and entire line as value
				row_values.put(line_number, line);
			}
		}
		Table table = new Table();
		table.setColumn_values(column_values);
		table.setRow_number(row_number);
		table.setRow_values(row_values);
		table.setTable_header(table_header);
		table.setTable_index(table_index);
		return table;

	}

}
