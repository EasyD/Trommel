/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class TRightParen extends Token
{
    public TRightParen()
    {
        super.setText(")");
    }

    public TRightParen(int line, int pos)
    {
        super.setText(")");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TRightParen(getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTRightParen(this);
    }

    @Override
    public void setText( String text)
    {
        throw new RuntimeException("Cannot change TRightParen text.");
    }
}
