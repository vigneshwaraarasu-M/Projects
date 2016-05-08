/* interface for normalizer phase */
package com.queryengine.normalizer;

import java.io.IOException;

public interface Normalizer_definition {
	public boolean check_table(String table_name);

	public boolean check_column(String columns, String table_name)
			throws IOException;

	public boolean condition_check(String conditions, String table_name)
			throws IOException;
}
