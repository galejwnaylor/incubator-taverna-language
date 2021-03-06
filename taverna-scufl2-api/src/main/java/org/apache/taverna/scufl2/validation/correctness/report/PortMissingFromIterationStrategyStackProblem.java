package org.apache.taverna.scufl2.validation.correctness.report;
/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/


import org.apache.taverna.scufl2.api.iterationstrategy.IterationStrategyStack;
import org.apache.taverna.scufl2.api.port.Port;
import org.apache.taverna.scufl2.validation.ValidationProblem;


/**
 * @author alanrw
 * 
 */
public class PortMissingFromIterationStrategyStackProblem extends
		ValidationProblem {
	private final Port port;

	public PortMissingFromIterationStrategyStackProblem(Port port,
			IterationStrategyStack iterationStrategyStack) {
		super(iterationStrategyStack);
		this.port = port;
	}

	/**
	 * @return the port
	 */
	public Port getPort() {
		return port;
	}

	@Override
	public String toString() {
		return getBean() + " does not include " + port;
	}
}
