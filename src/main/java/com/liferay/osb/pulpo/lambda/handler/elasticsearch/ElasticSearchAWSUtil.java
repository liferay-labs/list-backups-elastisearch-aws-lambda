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

package com.liferay.osb.pulpo.lambda.handler.elasticsearch;

import com.amazonaws.AmazonWebServiceResponse;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.util.StringUtils;

import com.liferay.osb.pulpo.lambda.handler.ListBackupsRequest;
import com.liferay.osb.pulpo.lambda.handler.http.SimpleHttpErrorResponseHandler;
import com.liferay.osb.pulpo.lambda.handler.http.StringResponseHandler;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.URI;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author David Arques
 */
public class ElasticSearchAWSUtil {

	/**
	 * Lists the snapshots made in the given AWS Elasticsearch repository.
	 *
	 * @param listBackupsRequest input request
	 * @param lambdaLogger lambda logger
	 * @see <a href='https://www.elastic.co/guide/en/elasticsearch/reference/current/cat-snapshots.html'>cat-snapshots</a>
	 * @return String containig the result of the request
	 */
	public static String listBackups(
		ListBackupsRequest listBackupsRequest, LambdaLogger lambdaLogger) {

		_validateInputRequest(listBackupsRequest);

		Request<Void> awsRequest = _createAwsRequest(
			listBackupsRequest.getHost(),
			_getRequestContext(listBackupsRequest), _getRequestParams(),
			HttpMethodName.GET, null);

		lambdaLogger.log("Executing AWS Request: " + awsRequest);

		Response<AmazonWebServiceResponse<String>> awsResponse =
			_executeAwsRequest(awsRequest);

		lambdaLogger.log("Amazon Web Service Response: " + awsResponse);

		return awsResponse.getAwsResponse().getResult();
	}

	private static Request<Void> _createAwsRequest(
		String host, String path, Map<String, List<String>> params,
		HttpMethodName httpMethodName, String content) {

		Request<Void> request = new DefaultRequest<>("es");

		request.setHttpMethod(httpMethodName);

		request.setEndpoint(URI.create(host));

		request.setResourcePath(path);

		if (!params.isEmpty()) {
			request.setParameters(params);
		}

		if (content != null) {
			InputStream contentInputStream = new ByteArrayInputStream(
				content.getBytes());

			request.setContent(contentInputStream);
		}

		AWS4Signer signer = new AWS4Signer();

		signer.setServiceName(request.getServiceName());
		signer.setRegionName(_REGION);
		signer.sign(
			request, new DefaultAWSCredentialsProviderChain().getCredentials());

		return request;
	}

	private static Response<AmazonWebServiceResponse<String>>
		_executeAwsRequest(Request<Void> request) {

		ClientConfiguration config = new ClientConfiguration();

		AmazonHttpClient.RequestExecutionBuilder builder = new AmazonHttpClient(
			config).requestExecutionBuilder();

		return builder.executionContext(
			new ExecutionContext(true)
		).request(
			request
		).errorResponseHandler(
			new SimpleHttpErrorResponseHandler()
		).execute(
			new StringResponseHandler()
		);
	}

	private static String _getRequestContext(
		ListBackupsRequest listBackupsRequest) {

		// /_cat/snapshots/repo?v&s=start_epoch:desc&format=json

		return String.format(_REQUEST_URL, listBackupsRequest.getBucket());
	}

	private static Map<String, List<String>> _getRequestParams() {
		return Collections.unmodifiableMap(
			new HashMap<String, List<String>>() {
				{
					put("v", Collections.emptyList());
					put("s", Collections.singletonList("start_epoch:desc"));
					put("format", Collections.singletonList("json"));
					//put("pretty", Collections.emptyList());
					//put("h", Arrays.asList("id", "start_epoch", "end_epoch"));
					put("h", Collections.singletonList("id,start_epoch"));
				}
			});
	}

	private static void _validateInputRequest(
		ListBackupsRequest listBackupsRequest) {

		if (listBackupsRequest == null) {
			throw new IllegalArgumentException(
				"ListBackupsRequest must not be null");
		}

		if (StringUtils.isNullOrEmpty(listBackupsRequest.getHost())) {
			throw new IllegalArgumentException("Host must not be empty");
		}

		if (StringUtils.isNullOrEmpty(listBackupsRequest.getBucket())) {
			throw new IllegalArgumentException(
				"Repository name must not be empty");
		}
	}

	private static final String _REGION = System.getenv(
		SDKGlobalConfiguration.AWS_REGION_ENV_VAR);

	private static final String _REQUEST_URL = "_cat/snapshots/%s";

}