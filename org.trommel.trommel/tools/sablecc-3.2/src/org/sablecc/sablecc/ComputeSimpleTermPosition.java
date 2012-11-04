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

public class ComputeSimpleTermPosition extends DepthFirstAdapter
{
  String currentAlt;
  String currentProd;
  boolean processingParsedAlt;
  private ResolveIds ids;
  private int counter;

  public final Map positionsMap = new TypedHashMap(
                                    StringCast.instance,
                                    StringCast.instance);

  public final Map elems_position = new TypedHashMap(
                                      StringCast.instance,
                                      IntegerCast.instance);

  public ComputeSimpleTermPosition(ResolveIds ids)
  {
    this.ids = ids;
  }

  public void inAProd(AProd node)
  {
    currentProd = ids.name(node.getId().getText());
    ids.names.put(node, currentProd);
  }

  public void inAAlt(AAlt node)
  {
    counter = 0;
    processingParsedAlt = true;

    if(node.getAltName() != null)
    {
      currentAlt = "A" +
                   ids.name( node.getAltName().getText() ) +
                   currentProd;
    }
    else
    {
      currentAlt = "A" + currentProd;
    }

    ids.names.put(node, currentAlt);
  }

  public void inAElem(AElem node)
  {
    if(processingParsedAlt)
    {
      String currentElemName;
      if(node.getElemName() != null)
      {
        currentElemName = currentAlt + "." + node.getElemName().getText();
      }
      else
      {
        currentElemName = currentAlt + "." + node.getId().getText();
      }

      elems_position.put(currentElemName, new Integer(++counter));
    }

    if(node.getSpecifier() != null &&
        node.getSpecifier() instanceof ATokenSpecifier)
    {
      return;
    }

    String name = ids.name( node.getId().getText() );

    String elemType = (String)ids.elemTypes.get(node);
    if(processingParsedAlt && elemType.startsWith("P"))
    {
      String elemName;
      if(node.getElemName() != null)
      {
        elemName = node.getElemName().getText();
      }
      else
      {
        elemName = node.getId().getText();
      }

      positionsMap.put(currentAlt+"."+elemName, elemType);
    }
  }

  public void outAAlt(AAlt node)
  {
    processingParsedAlt = false;
  }
}
