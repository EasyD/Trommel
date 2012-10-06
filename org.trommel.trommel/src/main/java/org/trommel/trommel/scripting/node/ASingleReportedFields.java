/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ASingleReportedFields extends PReportedFields
{
    private PReportedField _reportedField_;

    public ASingleReportedFields()
    {
        // Constructor
    }

    public ASingleReportedFields(
        @SuppressWarnings("hiding") PReportedField _reportedField_)
    {
        // Constructor
        setReportedField(_reportedField_);

    }

    @Override
    public Object clone()
    {
        return new ASingleReportedFields(
            cloneNode(this._reportedField_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseASingleReportedFields(this);
    }

    public PReportedField getReportedField()
    {
        return this._reportedField_;
    }

    public void setReportedField(PReportedField node)
    {
        if(this._reportedField_ != null)
        {
            this._reportedField_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._reportedField_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._reportedField_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._reportedField_ == child)
        {
            this._reportedField_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._reportedField_ == oldChild)
        {
            setReportedField((PReportedField) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}