/** This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AMinFunction extends PFunction
{
    private TMin _min_;

    public AMinFunction()
    {
        // Constructor
    }

    public AMinFunction(
         TMin _min_)
    {
        // Constructor
        setMin(_min_);

    }

    @Override
    public Object clone()
    {
        return new AMinFunction(
            cloneNode(this._min_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMinFunction(this);
    }

    public TMin getMin()
    {
        return this._min_;
    }

    public void setMin(TMin node)
    {
        if(this._min_ != null)
        {
            this._min_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._min_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._min_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._min_ == child)
        {
            this._min_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._min_ == oldChild)
        {
            setMin((TMin) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
