/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AReportDataStatement extends PReportDataStatement
{
    private TReport _report_;
    private TData _data_;
    private TFor _for_;
    private PReportedFields _reportedFields_;
    private PStorage _storage_;
    private TSemicolon _semicolon_;

    public AReportDataStatement()
    {
        // Constructor
    }

    public AReportDataStatement(
        TReport _report_,
        TData _data_,
        TFor _for_,
        PReportedFields _reportedFields_,
        PStorage _storage_,
        TSemicolon _semicolon_)
    {
        // Constructor
        setReport(_report_);

        setData(_data_);

        setFor(_for_);

        setReportedFields(_reportedFields_);

        setStorage(_storage_);

        setSemicolon(_semicolon_);

    }

    @Override
    public Object clone()
    {
        return new AReportDataStatement(
            cloneNode(this._report_),
            cloneNode(this._data_),
            cloneNode(this._for_),
            cloneNode(this._reportedFields_),
            cloneNode(this._storage_),
            cloneNode(this._semicolon_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAReportDataStatement(this);
    }

    public TReport getReport()
    {
        return this._report_;
    }

    public void setReport(TReport node)
    {
        if(this._report_ != null)
        {
            this._report_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._report_ = node;
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

    public TFor getFor()
    {
        return this._for_;
    }

    public void setFor(TFor node)
    {
        if(this._for_ != null)
        {
            this._for_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._for_ = node;
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

    public PStorage getStorage()
    {
        return this._storage_;
    }

    public void setStorage(PStorage node)
    {
        if(this._storage_ != null)
        {
            this._storage_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._storage_ = node;
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
            + toString(this._report_)
            + toString(this._data_)
            + toString(this._for_)
            + toString(this._reportedFields_)
            + toString(this._storage_)
            + toString(this._semicolon_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._report_ == child)
        {
            this._report_ = null;
            return;
        }

        if(this._data_ == child)
        {
            this._data_ = null;
            return;
        }

        if(this._for_ == child)
        {
            this._for_ = null;
            return;
        }

        if(this._reportedFields_ == child)
        {
            this._reportedFields_ = null;
            return;
        }

        if(this._storage_ == child)
        {
            this._storage_ = null;
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
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._report_ == oldChild)
        {
            setReport((TReport) newChild);
            return;
        }

        if(this._data_ == oldChild)
        {
            setData((TData) newChild);
            return;
        }

        if(this._for_ == oldChild)
        {
            setFor((TFor) newChild);
            return;
        }

        if(this._reportedFields_ == oldChild)
        {
            setReportedFields((PReportedFields) newChild);
            return;
        }

        if(this._storage_ == oldChild)
        {
            setStorage((PStorage) newChild);
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
