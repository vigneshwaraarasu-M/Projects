/* interface for parser phase */
package com.queryengine.parser;

public interface Parser_definition {
	public boolean syntax_checker(String query);

	public Queryparser keyword_identifier(String query);

}
