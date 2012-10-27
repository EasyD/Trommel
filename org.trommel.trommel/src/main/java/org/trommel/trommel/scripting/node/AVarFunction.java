/** This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AVarFunction extends PFunction
{
    private TVar _var_;

    public AVarFunction()
    {
        // Constructor
    }

    public AVarFunction(
         TVar _var_)
    {
        // Constructor
        setVar(_var_);

    }

    @Override
    public Object clone()
    {
        return new AVarFunction(
            cloneNode(this._var_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAVarFunction(this);
    }

    public TVar getVar()
    {
        return this._var_;
    }

    public void setVar(TVar node)
    {
        if(this._var_ != null)
        {
            this._var_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._var_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._var_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._var_ == child)
        {
            this._var_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._var_ == oldChild)
        {
            setVar((TVar) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
