/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AListProfilers extends PProfilers
{
    private PFunctionList _functionList_;

    public AListProfilers()
    {
        // Constructor
    }

    public AListProfilers(
        @SuppressWarnings("hiding") PFunctionList _functionList_)
    {
        // Constructor
        setFunctionList(_functionList_);

    }

    @Override
    public Object clone()
    {
        return new AListProfilers(
            cloneNode(this._functionList_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAListProfilers(this);
    }

    public PFunctionList getFunctionList()
    {
        return this._functionList_;
    }

    public void setFunctionList(PFunctionList node)
    {
        if(this._functionList_ != null)
        {
            this._functionList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._functionList_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._functionList_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._functionList_ == child)
        {
            this._functionList_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._functionList_ == oldChild)
        {
            setFunctionList((PFunctionList) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
