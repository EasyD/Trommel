/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ASampleDataTrommelScript extends PTrommelScript
{
    private PLoadDataStatement _loadDataStatement_;
    private PSampleDataStatement _sampleDataStatement_;

    public ASampleDataTrommelScript()
    {
        // Constructor
    }

    public ASampleDataTrommelScript(
         PLoadDataStatement _loadDataStatement_,
         PSampleDataStatement _sampleDataStatement_)
    {
        // Constructor
        setLoadDataStatement(_loadDataStatement_);

        setSampleDataStatement(_sampleDataStatement_);

    }

    @Override
    public Object clone()
    {
        return new ASampleDataTrommelScript(
            cloneNode(this._loadDataStatement_),
            cloneNode(this._sampleDataStatement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASampleDataTrommelScript(this);
    }

    public PLoadDataStatement getLoadDataStatement()
    {
        return this._loadDataStatement_;
    }

    public void setLoadDataStatement(PLoadDataStatement node)
    {
        if(this._loadDataStatement_ != null)
        {
            this._loadDataStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._loadDataStatement_ = node;
    }

    public PSampleDataStatement getSampleDataStatement()
    {
        return this._sampleDataStatement_;
    }

    public void setSampleDataStatement(PSampleDataStatement node)
    {
        if(this._sampleDataStatement_ != null)
        {
            this._sampleDataStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._sampleDataStatement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._loadDataStatement_)
            + toString(this._sampleDataStatement_);
    }

    @Override
    void removeChild(Node child)
    {
        // Remove child
        if(this._loadDataStatement_ == child)
        {
            this._loadDataStatement_ = null;
            return;
        }

        if(this._sampleDataStatement_ == child)
        {
            this._sampleDataStatement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        // Replace child
        if(this._loadDataStatement_ == oldChild)
        {
            setLoadDataStatement((PLoadDataStatement) newChild);
            return;
        }

        if(this._sampleDataStatement_ == oldChild)
        {
            setSampleDataStatement((PSampleDataStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
