/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.osb.pulpo.lambda.handler;

/**
 * Lambda input request.
 *
 * @author David Arques
 */
public class ListBackupsRequest {

	/**
	 * Gets the repository name.
	 *
	 * @return repository name
	 */
	public String getBucket() {
		return _bucket;
	}

	/**
	 * Gets host.
	 *
	 * @return host.
	 */
	public String getHost() {
		return _host;
	}

	/**
	 * Sets the respository name.
	 *
	 * @param bucket a string with the repository name
	 */
	public void setBucket(String bucket) {
		_bucket = bucket;
	}

	/**
	 * Sets host.
	 *
	 * @param host a string with the host
	 */
	public void setHost(String host) {
		_host = host;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder(6);

		sb.append("ListBackupsRequest{");
		sb.append("_bucket='");
		sb.append(_bucket);
		sb.append("', _host='");
		sb.append(_host);
		sb.append("}");

		return sb.toString();
	}

	private String _bucket;
	private String _host;

}