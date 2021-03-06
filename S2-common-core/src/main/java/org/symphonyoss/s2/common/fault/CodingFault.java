/*
 *
 *
 * Copyright 2017 Symphony Communication Services, LLC.
 *
 * Licensed to The Symphony Software Foundation (SSF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The SSF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.symphonyoss.s2.common.fault;

/**
 * A type of ProgramFault resulting from faulty code.
 * It is appropriate to throw this fault from a supposedly
 * unreachable catch block or from the default case of
 * a switch statement with cases for all values of an 
 * enumeration for example.
 * 
 * @author bruce.skingle
 *
 */
public class CodingFault extends ProgramFault
{
  private static final long serialVersionUID = 1L;

  public CodingFault()
  {
  }
  
  public CodingFault(String message)
  {
    super(message);
  }

  public CodingFault(Throwable cause)
  {
    super(cause);
  }

  public CodingFault(String message, Throwable cause)
  {
    super(message, cause);
  }
}
