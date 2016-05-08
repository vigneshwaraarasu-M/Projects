/* This program will execute limited SQL select queries. This driver program will get input queries from the user and pass the query to executer phase. 
 * Similar to other databases, this program contains Driver,Parser, Normalizer,Optimizer Phases. The table definition is stored in Table_def class
 * Driver - This driver phase will get input from user, and pass the query to Executer phase
 * Executer - The executer will execute the query by passing it to various phases
 * Parser - This phase will parse the query to check the syntax and parse the query with table name, fields and conditions
 * Normalizer - This phase will check the column details and conditions of the query
 * Optimizer - This phase will create a execution plan and display the output in console
 * Tabledefinition - The loading of file values in tables are maintained dynamically in memory while executing the program.
 * 					 The table values are stored in HashMap in for faster retrieval 
 * Null value will be returned, if resultset don't have andy values*/
package com.queryengine.driver;

import java.io.IOException;
import java.util.Scanner;

import com.queryengine.executer.Executer;
import com.queryengine.executer.Executer_definition;
import com.queryengine.parser.Queryparser;
import com.queryengine.tabledefinition.Table;
import com.queryengine.tabledefinition.Table_def;

public class Driver {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//Get input from the user
		System.out
				.println("Enter the query to execute.please enter keywords in capital. Hit enter after entering query");
		
		Scanner input = new Scanner(System.in);
		String query = input.nextLine();

		Executer_definition query_executer = new Executer();
		
		//Pass the query result to executer phase
		Queryparser table_def = query_executer.parse(query);
		
		//If parser returns null then query is invalid
		if (table_def == null)
		{
			System.out.println("Invalid query");
			System.exit(0);
		}
			
		System.out.println();
		
		// The parsed result will be validated with normalizer phase to check table, column and condition details
		// If there exist invalid values, Then program will be terminated
		query_executer.normalize(table_def);
		System.out.println();
		
		//Then parsed result will be passed to execution plan phase to prepare the execution
		query_executer.optimize(table_def);

	}

}
