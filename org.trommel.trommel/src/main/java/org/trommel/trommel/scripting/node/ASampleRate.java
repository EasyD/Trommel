/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ASampleRate extends PSampleRate
{
    private TInteger _integer_;
    private TPercent _percent_;

    public ASampleRate()
    {
        // Constructor
    }

    public ASampleRate(
        @SuppressWarnings("hiding") TInteger _integer_,
        @SuppressWarnings("hiding") TPercent _percent_)
    {
        // Constructor
        setInteger(_integer_);

        setPercent(_percent_);

    }

    @Override
    public Object clone()
    {
        return new ASampleRate(
            cloneNode(this._integer_),
            cloneNode(this._percent_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASampleRate(this);
    }

    public TInteger getInteger()
    {
        return this._integer_;
    }

    public void setInteger(TInteger node)
    {
        if(this._integer_ != null)
        {
            this._integer_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._integer_ = node;
    }

    public TPercent getPercent()
    {
        return this._percent_;
    }

    public void setPercent(TPercent node)
    {
        if(this._percent_ != null)
        {
            this._percent_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._percent_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._integer_)
            + toString(this._percent_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._integer_ == child)
        {
            this._integer_ = null;
            return;
        }

        if(this._percent_ == child)
        {
            this._percent_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._integer_ == oldChild)
        {
            setInteger((TInteger) newChild);
            return;
        }

        if(this._percent_ == oldChild)
        {
            setPercent((TPercent) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}