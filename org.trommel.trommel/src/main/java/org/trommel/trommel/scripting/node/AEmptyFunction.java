/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AEmptyFunction extends PFunction
{
    private TEmpty _empty_;

    public AEmptyFunction()
    {
        // Constructor
    }

    public AEmptyFunction(
         TEmpty _empty_)
    {
        // Constructor
        setEmpty(_empty_);

    }

    @Override
    public Object clone()
    {
        return new AEmptyFunction(
            cloneNode(this._empty_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAEmptyFunction(this);
    }

    public TEmpty getEmpty()
    {
        return this._empty_;
    }

    public void setEmpty(TEmpty node)
    {
        if(this._empty_ != null)
        {
            this._empty_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._empty_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._empty_);
    }

    @Override
    void removeChild(Node child)
    {
        // Remove child
        if(this._empty_ == child)
        {
            this._empty_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        // Replace child
        if(this._empty_ == oldChild)
        {
            setEmpty((TEmpty) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
