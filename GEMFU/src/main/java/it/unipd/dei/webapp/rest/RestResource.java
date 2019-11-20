/*
 * Copyright 2018 University of Padua, Italy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.unipd.dei.webapp.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;

/**
 * Represents a generic REST resource.
 * 
 * @author Nicola Ferro (ferro@dei.unipd.it)
 * @version 1.00
 * @since 1.00
 */
public abstract class RestResource {

	/**
	 * The name of the resource parameter in POST and PUT requests.
	 */
	protected static final String RESOURCE_PARAMETER = "resource";

	/**
	 * The HTTP request
	 */
	protected final HttpServletRequest req;

	/**
	 * The HTTP response
	 */
	protected final HttpServletResponse res;

	/**
	 * The connection to the database
	 */
	protected final Connection con;

	/**
	 * Creates a new REST resource.
	 *
	 * @param req the HTTP request.
	 * @param res the HTTP response.
	 * @param con the connection to the database.
	 */
	protected RestResource(final HttpServletRequest req, final HttpServletResponse res, Connection con) {
		this.req = req;
		this.res = res;
		this.con = con;
	}
}
