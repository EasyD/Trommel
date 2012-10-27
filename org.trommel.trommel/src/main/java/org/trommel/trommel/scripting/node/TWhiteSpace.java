/** This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class TWhiteSpace extends Token
{
    public TWhiteSpace(String text)
    {
        setText(text);
    }

    public TWhiteSpace(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TWhiteSpace(getText(), getLine(), getPos());
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTWhiteSpace(this);
    }
}
