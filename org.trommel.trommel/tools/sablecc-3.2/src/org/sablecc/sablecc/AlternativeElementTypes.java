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
import java.io.*;

public class AlternativeElementTypes extends DepthFirstAdapter
{
  private Map altElemTypes = new TypedHashMap(StringCast.instance,
                             StringCast.instance);

  private ResolveIds ids;
  private String currentAlt;

  public AlternativeElementTypes(ResolveIds ids)
  {
    this.ids = ids;
  }

  public Map getMapOfAltElemType()
  {
    return altElemTypes;
  }

  public void caseAAst(AAst node)
  {}

  public void caseAProd(final AProd production)
  {
    Object []temp = production.getAlts().toArray();
    for(int i = 0; i<temp.length; i++)
    {
      ((PAlt)temp[i]).apply(this);
    }
  }

  public void caseAAlt(AAlt node)
  {
    currentAlt = (String)ids.names.get(node);
    Object []temp = node.getElems().toArray();
    for(int i = 0; i<temp.length; i++)
    {
      ((PElem)temp[i]).apply(this);
    }
  }

  public void inAElem(AElem node)
  {
    String elemType = (String)ids.elemTypes.get(node);

    if(node.getElemName() != null)
    {
      altElemTypes.put(currentAlt+"."+node.getElemName().getText(), elemType );
    }
    else
    {
      altElemTypes.put(currentAlt+"."+node.getId().getText(), elemType );
    }
  }
}
