/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class ALoadDataStatement extends PLoadDataStatement
{
    private TLoad _load_;
    private TData _data_;
    private PLoadedFile _loadedFile_;
    private TAs _as_;
    private PLoadedFields _loadedFields_;
    private PFieldsTerminatedBy _fieldsTerminatedBy_;

    public ALoadDataStatement()
    {
        // Constructor
    }

    public ALoadDataStatement(
         TLoad _load_,
         TData _data_,
         PLoadedFile _loadedFile_,
         TAs _as_,
         PLoadedFields _loadedFields_,
         PFieldsTerminatedBy _fieldsTerminatedBy_)
    {
        // Constructor
        setLoad(_load_);

        setData(_data_);

        setLoadedFile(_loadedFile_);

        setAs(_as_);

        setLoadedFields(_loadedFields_);

        setFieldsTerminatedBy(_fieldsTerminatedBy_);

    }

    @Override
    public Object clone()
    {
        return new ALoadDataStatement(
            cloneNode(this._load_),
            cloneNode(this._data_),
            cloneNode(this._loadedFile_),
            cloneNode(this._as_),
            cloneNode(this._loadedFields_),
            cloneNode(this._fieldsTerminatedBy_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALoadDataStatement(this);
    }

    public TLoad getLoad()
    {
        return this._load_;
    }

    public void setLoad(TLoad node)
    {
        if(this._load_ != null)
        {
            this._load_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._load_ = node;
    }

    public TData getData()
    {
        return this._data_;
    }

    public void setData(TData node)
    {
        if(this._data_ != null)
        {
            this._data_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._data_ = node;
    }

    public PLoadedFile getLoadedFile()
    {
        return this._loadedFile_;
    }

    public void setLoadedFile(PLoadedFile node)
    {
        if(this._loadedFile_ != null)
        {
            this._loadedFile_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._loadedFile_ = node;
    }

    public TAs getAs()
    {
        return this._as_;
    }

    public void setAs(TAs node)
    {
        if(this._as_ != null)
        {
            this._as_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._as_ = node;
    }

    public PLoadedFields getLoadedFields()
    {
        return this._loadedFields_;
    }

    public void setLoadedFields(PLoadedFields node)
    {
        if(this._loadedFields_ != null)
        {
            this._loadedFields_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._loadedFields_ = node;
    }

    public PFieldsTerminatedBy getFieldsTerminatedBy()
    {
        return this._fieldsTerminatedBy_;
    }

    public void setFieldsTerminatedBy(PFieldsTerminatedBy node)
    {
        if(this._fieldsTerminatedBy_ != null)
        {
            this._fieldsTerminatedBy_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._fieldsTerminatedBy_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._load_)
            + toString(this._data_)
            + toString(this._loadedFile_)
            + toString(this._as_)
            + toString(this._loadedFields_)
            + toString(this._fieldsTerminatedBy_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._load_ == child)
        {
            this._load_ = null;
            return;
        }

        if(this._data_ == child)
        {
            this._data_ = null;
            return;
        }

        if(this._loadedFile_ == child)
        {
            this._loadedFile_ = null;
            return;
        }

        if(this._as_ == child)
        {
            this._as_ = null;
            return;
        }

        if(this._loadedFields_ == child)
        {
            this._loadedFields_ = null;
            return;
        }

        if(this._fieldsTerminatedBy_ == child)
        {
            this._fieldsTerminatedBy_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._load_ == oldChild)
        {
            setLoad((TLoad) newChild);
            return;
        }

        if(this._data_ == oldChild)
        {
            setData((TData) newChild);
            return;
        }

        if(this._loadedFile_ == oldChild)
        {
            setLoadedFile((PLoadedFile) newChild);
            return;
        }

        if(this._as_ == oldChild)
        {
            setAs((TAs) newChild);
            return;
        }

        if(this._loadedFields_ == oldChild)
        {
            setLoadedFields((PLoadedFields) newChild);
            return;
        }

        if(this._fieldsTerminatedBy_ == oldChild)
        {
            setFieldsTerminatedBy((PFieldsTerminatedBy) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
