/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AField extends PField
{
    private TIdentifier _identifier_;
    private TColon _colon_;
    private TFieldType _fieldType_;

    public AField()
    {
        // Constructor
    }

    public AField(
         TIdentifier _identifier_,
         TColon _colon_,
         TFieldType _fieldType_)
    {
        // Constructor
        setIdentifier(_identifier_);

        setColon(_colon_);

        setFieldType(_fieldType_);

    }

    @Override
    public Object clone()
    {
        return new AField(
            cloneNode(this._identifier_),
            cloneNode(this._colon_),
            cloneNode(this._fieldType_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAField(this);
    }

    public TIdentifier getIdentifier()
    {
        return this._identifier_;
    }

    public void setIdentifier(TIdentifier node)
    {
        if(this._identifier_ != null)
        {
            this._identifier_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._identifier_ = node;
    }

    public TColon getColon()
    {
        return this._colon_;
    }

    public void setColon(TColon node)
    {
        if(this._colon_ != null)
        {
            this._colon_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._colon_ = node;
    }

    public TFieldType getFieldType()
    {
        return this._fieldType_;
    }

    public void setFieldType(TFieldType node)
    {
        if(this._fieldType_ != null)
        {
            this._fieldType_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fieldType_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._identifier_)
            + toString(this._colon_)
            + toString(this._fieldType_);
    }

    @Override
    void removeChild(Node child)
    {
        // Remove child
        if(this._identifier_ == child)
        {
            this._identifier_ = null;
            return;
        }

        if(this._colon_ == child)
        {
            this._colon_ = null;
            return;
        }

        if(this._fieldType_ == child)
        {
            this._fieldType_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(Node oldChild, Node newChild)
    {
        // Replace child
        if(this._identifier_ == oldChild)
        {
            setIdentifier((TIdentifier) newChild);
            return;
        }

        if(this._colon_ == oldChild)
        {
            setColon((TColon) newChild);
            return;
        }

        if(this._fieldType_ == oldChild)
        {
            setFieldType((TFieldType) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
