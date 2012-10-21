/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ADefaultLinearity extends PLinearity
{
    private TLin _lin_;

    public ADefaultLinearity()
    {
        // Constructor
    }

    public ADefaultLinearity(TLin _lin_)
    {
        // Constructor
        setLin(_lin_);

    }

    @Override
    public Object clone()
    {
        return new ADefaultLinearity(
            cloneNode(this._lin_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADefaultLinearity(this);
    }

    public TLin getLin()
    {
        return this._lin_;
    }

    public void setLin(TLin node)
    {
        if(this._lin_ != null)
        {
            this._lin_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._lin_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._lin_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._lin_ == child)
        {
            this._lin_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._lin_ == oldChild)
        {
            setLin((TLin) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
