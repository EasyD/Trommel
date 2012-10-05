/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AConfFunction extends PFunction
{
    private PConfidence _confidence_;

    public AConfFunction()
    {
        // Constructor
    }

    public AConfFunction(
        @SuppressWarnings("hiding") PConfidence _confidence_)
    {
        // Constructor
        setConfidence(_confidence_);

    }

    @Override
    public Object clone()
    {
        return new AConfFunction(
            cloneNode(this._confidence_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAConfFunction(this);
    }

    public PConfidence getConfidence()
    {
        return this._confidence_;
    }

    public void setConfidence(PConfidence node)
    {
        if(this._confidence_ != null)
        {
            this._confidence_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._confidence_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._confidence_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._confidence_ == child)
        {
            this._confidence_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._confidence_ == oldChild)
        {
            setConfidence((PConfidence) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}