/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AReportDataTrommelScript extends PTrommelScript
{
    private PLoadDataStatement _loadDataStatement_;
    private PReportDataStatement _reportDataStatement_;

    public AReportDataTrommelScript()
    {
        // Constructor
    }

    public AReportDataTrommelScript(
         PLoadDataStatement _loadDataStatement_,
         PReportDataStatement _reportDataStatement_)
    {
        // Constructor
        setLoadDataStatement(_loadDataStatement_);

        setReportDataStatement(_reportDataStatement_);

    }

    @Override
    public Object clone()
    {
        return new AReportDataTrommelScript(
            cloneNode(this._loadDataStatement_),
            cloneNode(this._reportDataStatement_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAReportDataTrommelScript(this);
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

    public PReportDataStatement getReportDataStatement()
    {
        return this._reportDataStatement_;
    }

    public void setReportDataStatement(PReportDataStatement node)
    {
        if(this._reportDataStatement_ != null)
        {
            this._reportDataStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._reportDataStatement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._loadDataStatement_)
            + toString(this._reportDataStatement_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._loadDataStatement_ == child)
        {
            this._loadDataStatement_ = null;
            return;
        }

        if(this._reportDataStatement_ == child)
        {
            this._reportDataStatement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._loadDataStatement_ == oldChild)
        {
            setLoadDataStatement((PLoadDataStatement) newChild);
            return;
        }

        if(this._reportDataStatement_ == oldChild)
        {
            setReportDataStatement((PReportDataStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
