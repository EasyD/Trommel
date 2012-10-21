/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AListReportedFields extends PReportedFields
{
    private PReportedFields _reportedFields_;
    private TComma _comma_;
    private PReportedField _reportedField_;

    public AListReportedFields()
    {
        // Constructor
    }

    public AListReportedFields(
         PReportedFields _reportedFields_,
         TComma _comma_,
         PReportedField _reportedField_)
    {
        // Constructor
        setReportedFields(_reportedFields_);

        setComma(_comma_);

        setReportedField(_reportedField_);

    }

    @Override
    public Object clone()
    {
        return new AListReportedFields(
            cloneNode(this._reportedFields_),
            cloneNode(this._comma_),
            cloneNode(this._reportedField_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAListReportedFields(this);
    }

    public PReportedFields getReportedFields()
    {
        return this._reportedFields_;
    }

    public void setReportedFields(PReportedFields node)
    {
        if(this._reportedFields_ != null)
        {
            this._reportedFields_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._reportedFields_ = node;
    }

    public TComma getComma()
    {
        return this._comma_;
    }

    public void setComma(TComma node)
    {
        if(this._comma_ != null)
        {
            this._comma_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._comma_ = node;
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
            + toString(this._reportedFields_)
            + toString(this._comma_)
            + toString(this._reportedField_);
    }

    @Override
    void removeChild(Node child)
    {
        // Remove child
        if(this._reportedFields_ == child)
        {
            this._reportedFields_ = null;
            return;
        }

        if(this._comma_ == child)
        {
            this._comma_ = null;
            return;
        }

        if(this._reportedField_ == child)
        {
            this._reportedField_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        // Replace child
        if(this._reportedFields_ == oldChild)
        {
            setReportedFields((PReportedFields) newChild);
            return;
        }

        if(this._comma_ == oldChild)
        {
            setComma((TComma) newChild);
            return;
        }

        if(this._reportedField_ == oldChild)
        {
            setReportedField((PReportedField) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
