/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AFieldTerminator extends PFieldTerminator
{
    private TQuotedString _quotedString_;

    public AFieldTerminator()
    {
        // Constructor
    }

    public AFieldTerminator(TQuotedString _quotedString_)
    {
        // Constructor
        setQuotedString(_quotedString_);

    }

    @Override
    public Object clone()
    {
        return new AFieldTerminator(
            cloneNode(this._quotedString_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAFieldTerminator(this);
    }

    public TQuotedString getQuotedString()
    {
        return this._quotedString_;
    }

    public void setQuotedString(TQuotedString node)
    {
        if(this._quotedString_ != null)
        {
            this._quotedString_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._quotedString_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._quotedString_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._quotedString_ == child)
        {
            this._quotedString_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._quotedString_ == oldChild)
        {
            setQuotedString((TQuotedString) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
