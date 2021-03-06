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

package org.symphonyoss.s2.common.dom;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.symphonyoss.s2.common.dom.json.IJsonDomNode;
import org.symphonyoss.s2.common.exception.InvalidValueException;
import org.symphonyoss.s2.common.fault.CodingFault;

import com.google.protobuf.ByteString;

public class TypeAdaptor
{
  static Map<Class<?>, ITypeAdaptor<?>> adaptorMap_ = new HashMap<>();
  
  // We can't use lambdas as this is java7 for the benefit of SBE. Sigh.
  static
  {
    adaptorMap_.put(ByteString.class, 
        new ITypeAdaptor<ByteString>(){@Override public ByteString adapt(IJsonDomNode node) throws InvalidValueException{
          if(node instanceof IStringProvider)
          {
            String encoded = ((IStringProvider)node).asString();
            
            if(!Base64.isBase64(encoded))
              throw new InvalidValueException("Input contains invalid Base64 characters");
            
            return ByteString.copyFrom(Base64.decodeBase64(encoded));
          }
          throw new InvalidValueException("String input is required.");
          }});
    
    adaptorMap_.put(String.class, 
        new ITypeAdaptor<String>(){@Override public String adapt(IJsonDomNode node) throws InvalidValueException{
          if(node instanceof IStringProvider)
          {
            return ((IStringProvider)node).asString();
          }
          throw new InvalidValueException("String input is required.");
          }});
    
    adaptorMap_.put(Long.class, 
        new ITypeAdaptor<Long>(){@Override public Long adapt(IJsonDomNode node) throws InvalidValueException{
          if(node instanceof ILongProvider)
          {
            return ((ILongProvider)node).asLong();
          }
          throw new InvalidValueException("Long input is required.");
          }});
    
    adaptorMap_.put(Integer.class, 
        new ITypeAdaptor<Integer>(){@Override public Integer adapt(IJsonDomNode node) throws InvalidValueException{
          if(node instanceof IIntegerProvider)
          {
            return ((IIntegerProvider)node).asInteger();
          }
          throw new InvalidValueException("Integer input is required.");
          }});
    
    adaptorMap_.put(Double.class, 
        new ITypeAdaptor<Double>(){@Override public Double adapt(IJsonDomNode node) throws InvalidValueException{
          if(node instanceof IDoubleProvider)
          {
            return ((IDoubleProvider)node).asDouble();
          }
          throw new InvalidValueException("Double input is required.");
          }});
    
    adaptorMap_.put(Float.class, 
        new ITypeAdaptor<Float>(){@Override public Float adapt(IJsonDomNode node) throws InvalidValueException{
          if(node instanceof IFloatProvider)
          {
            return ((IFloatProvider)node).asFloat();
          }
          throw new InvalidValueException("Float input is required.");
          }});
    
    adaptorMap_.put(Boolean.class, 
        new ITypeAdaptor<Boolean>(){@Override public Boolean adapt(IJsonDomNode node) throws InvalidValueException{
          if(node instanceof IBooleanProvider)
          {
            return ((IBooleanProvider)node).asBoolean();
          }
          throw new InvalidValueException("Boolean input is required.");
          }});
  }

  public static <T> T adapt(Class<T> type, IJsonDomNode node) throws InvalidValueException
  {
    @SuppressWarnings("unchecked")
    ITypeAdaptor<T> adaptor = (ITypeAdaptor<T>)adaptorMap_.get(type);
    
    if(adaptor == null)
      throw new CodingFault("Unrecognized type " + type.getName());
    
    return adaptor.adapt(node);
  }
}
