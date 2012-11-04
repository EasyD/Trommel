/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * This file is part of SableCC.                             *
 * See the file "LICENSE" for copyright information and the  *
 * terms and conditions for copying, distribution and        *
 * modification of SableCC.                                  *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package org.sablecc.sablecc;

import org.sablecc.sablecc.analysis.*;
import org.sablecc.sablecc.node.*;
import java.util.*;

public class Transitions extends DepthFirstAdapter
{
  public final Map tokenStates = new TypedHashMap(
                                   NodeCast.instance,
                                   NoCast.instance);

  private String state;
  private String transition;
  private Map map;

  public void caseAStateList(AStateList node)
  {
    inAStateList(node);
    if(node.getId() != null)
    {
      node.getId().apply(this);
    }
    if(node.getTransition() != null)
    {
      node.getTransition().apply(this);
    }

    outAStateList(node);  // We moved this...

    {
      Object temp[] = node.getStateLists().toArray();
      for(int i = 0; i < temp.length; i++)
      {
        ((PStateListTail) temp[i]).apply(this);
      }
    }
  }

  public void inATokenDef(ATokenDef node)
  {
    map = new TypedTreeMap(
            StringComparator.instance,
            StringCast.instance,
            StringCast.instance);
  }

  public void inAStateList(AStateList node)
  {
    state = transition = node.getId().getText().toUpperCase();
  }

  public void inAStateListTail(AStateListTail node)
  {
    state = transition = node.getId().getText().toUpperCase();
  }

  public void outATransition(ATransition node)
  {
    transition = node.getId().getText().toUpperCase();
  }

  public void outAStateList(AStateList node)
  {
    map.put(state, transition);
  }

  public void outAStateListTail(AStateListTail node)
  {
    map.put(state, transition);
  }

  public void outATokenDef(ATokenDef node)
  {
    tokenStates.put(node, map);
  }
}
