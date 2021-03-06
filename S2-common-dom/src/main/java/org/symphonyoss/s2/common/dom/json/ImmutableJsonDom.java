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

package org.symphonyoss.s2.common.dom.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.annotation.Nonnull;

import org.symphonyoss.s2.common.dom.DomSerializer;

import com.google.common.collect.ImmutableList;

public class ImmutableJsonDom extends JsonDom<IImmutableJsonDomNode> implements IImmutableJsonDomNode
{protected static final DomSerializer SERIALIZER = DomSerializer.newBuilder().withCanonicalMode(true).build();

  private final ImmutableList<IImmutableJsonDomNode>  children_;
  private final @Nonnull String                       asString_;
  
  public ImmutableJsonDom(Collection<IJsonDomNode> children)
  {
    ArrayList<IImmutableJsonDomNode> c = new ArrayList<>(children.size());
    
    for(IJsonDomNode child : children)
    {
      if(child instanceof IImmutableJsonDomNode)
      {
        c.add((IImmutableJsonDomNode) child);
      }
      else
      {
        c.add(((IMutableJsonDomNode)child).immutify());
      }
    }
    children_ = ImmutableList.copyOf(c);
    asString_ = SERIALIZER.serialize(this);
  }

  @Override
  public IImmutableJsonDomNode immutify()
  {
    return this;
  }
  
  @Override
  public int size()
  {
    return children_.size();
  }

  @Override
  public boolean isEmpty()
  {
    return children_.isEmpty();
  }

  @Override
  public IImmutableJsonDomNode getFirst()
  {
    return children_.get(0);
  }

  @Override
  public Iterator<IImmutableJsonDomNode> iterator()
  {
    return children_.iterator();
  }
  
  @Override
  public @Nonnull String toString()
  {
    return asString_;
  }
  
  @Override
  public int hashCode()
  {
    return asString_.hashCode();
  }

  @Override
  public boolean equals(Object other)
  {
    return other instanceof ImmutableJsonDom && asString_.equals(((ImmutableJsonDom)other).asString_);
  }

  @Override
  public int compareTo(IImmutableJsonDomNode other)
  {
    return asString_.compareTo(other.toString());
  }
}
