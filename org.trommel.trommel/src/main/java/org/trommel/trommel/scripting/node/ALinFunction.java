/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ALinFunction extends PFunction
{
    private PLinearity _linearity_;

    public ALinFunction()
    {
        // Constructor
    }

    public ALinFunction(
        @SuppressWarnings("hiding") PLinearity _linearity_)
    {
        // Constructor
        setLinearity(_linearity_);

    }

    @Override
    public Object clone()
    {
        return new ALinFunction(
            cloneNode(this._linearity_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALinFunction(this);
    }

    public PLinearity getLinearity()
    {
        return this._linearity_;
    }

    public void setLinearity(PLinearity node)
    {
        if(this._linearity_ != null)
        {
            this._linearity_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._linearity_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._linearity_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._linearity_ == child)
        {
            this._linearity_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._linearity_ == oldChild)
        {
            setLinearity((PLinearity) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
