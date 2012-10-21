/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ACustomFieldsTerminatedBy extends PFieldsTerminatedBy
{
    private TFields _fields_;
    private TTerminated _terminated_;
    private TBy _by_;
    private PFieldTerminator _fieldTerminator_;
    private TSemicolon _semicolon_;

    public ACustomFieldsTerminatedBy()
    {
        // Constructor
    }

    public ACustomFieldsTerminatedBy(
         TFields _fields_,
         TTerminated _terminated_,
         TBy _by_,
         PFieldTerminator _fieldTerminator_,
         TSemicolon _semicolon_)
    {
        // Constructor
        setFields(_fields_);

        setTerminated(_terminated_);

        setBy(_by_);

        setFieldTerminator(_fieldTerminator_);

        setSemicolon(_semicolon_);

    }

    @Override
    public Object clone()
    {
        return new ACustomFieldsTerminatedBy(
            cloneNode(this._fields_),
            cloneNode(this._terminated_),
            cloneNode(this._by_),
            cloneNode(this._fieldTerminator_),
            cloneNode(this._semicolon_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACustomFieldsTerminatedBy(this);
    }

    public TFields getFields()
    {
        return this._fields_;
    }

    public void setFields(TFields node)
    {
        if(this._fields_ != null)
        {
            this._fields_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fields_ = node;
    }

    public TTerminated getTerminated()
    {
        return this._terminated_;
    }

    public void setTerminated(TTerminated node)
    {
        if(this._terminated_ != null)
        {
            this._terminated_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._terminated_ = node;
    }

    public TBy getBy()
    {
        return this._by_;
    }

    public void setBy(TBy node)
    {
        if(this._by_ != null)
        {
            this._by_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._by_ = node;
    }

    public PFieldTerminator getFieldTerminator()
    {
        return this._fieldTerminator_;
    }

    public void setFieldTerminator(PFieldTerminator node)
    {
        if(this._fieldTerminator_ != null)
        {
            this._fieldTerminator_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fieldTerminator_ = node;
    }

    public TSemicolon getSemicolon()
    {
        return this._semicolon_;
    }

    public void setSemicolon(TSemicolon node)
    {
        if(this._semicolon_ != null)
        {
            this._semicolon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._semicolon_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._fields_)
            + toString(this._terminated_)
            + toString(this._by_)
            + toString(this._fieldTerminator_)
            + toString(this._semicolon_);
    }

    @Override
    void removeChild(Node child)
    {
        // Remove child
        if(this._fields_ == child)
        {
            this._fields_ = null;
            return;
        }

        if(this._terminated_ == child)
        {
            this._terminated_ = null;
            return;
        }

        if(this._by_ == child)
        {
            this._by_ = null;
            return;
        }

        if(this._fieldTerminator_ == child)
        {
            this._fieldTerminator_ = null;
            return;
        }

        if(this._semicolon_ == child)
        {
            this._semicolon_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        // Replace child
        if(this._fields_ == oldChild)
        {
            setFields((TFields) newChild);
            return;
        }

        if(this._terminated_ == oldChild)
        {
            setTerminated((TTerminated) newChild);
            return;
        }

        if(this._by_ == oldChild)
        {
            setBy((TBy) newChild);
            return;
        }

        if(this._fieldTerminator_ == oldChild)
        {
            setFieldTerminator((PFieldTerminator) newChild);
            return;
        }

        if(this._semicolon_ == oldChild)
        {
            setSemicolon((TSemicolon) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
