/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.node;

import org.trommel.trommel.scripting.analysis.*;

@SuppressWarnings("nls")
public final class AStoreExportStorage extends PStorage
{
    private TStore _store_;
    private TInto _into_;
    private PHdfsFilePath _hdfsFilePath_;
    private TExport _export_;
    private TTo _to_;
    private PLocalFilePath _localFilePath_;
    private TAs _as_;
    private PLocalFile _localFile_;

    public AStoreExportStorage()
    {
        // Constructor
    }

    public AStoreExportStorage(
        TStore _store_,
        TInto _into_,
        PHdfsFilePath _hdfsFilePath_,
        TExport _export_,
        TTo _to_,
        PLocalFilePath _localFilePath_,
        TAs _as_,
        PLocalFile _localFile_)
    {
        // Constructor
        setStore(_store_);

        setInto(_into_);

        setHdfsFilePath(_hdfsFilePath_);

        setExport(_export_);

        setTo(_to_);

        setLocalFilePath(_localFilePath_);

        setAs(_as_);

        setLocalFile(_localFile_);

    }

    @Override
    public Object clone()
    {
        return new AStoreExportStorage(
            cloneNode(this._store_),
            cloneNode(this._into_),
            cloneNode(this._hdfsFilePath_),
            cloneNode(this._export_),
            cloneNode(this._to_),
            cloneNode(this._localFilePath_),
            cloneNode(this._as_),
            cloneNode(this._localFile_));
    }

    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStoreExportStorage(this);
    }

    public TStore getStore()
    {
        return this._store_;
    }

    public void setStore(TStore node)
    {
        if(this._store_ != null)
        {
            this._store_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._store_ = node;
    }

    public TInto getInto()
    {
        return this._into_;
    }

    public void setInto(TInto node)
    {
        if(this._into_ != null)
        {
            this._into_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._into_ = node;
    }

    public PHdfsFilePath getHdfsFilePath()
    {
        return this._hdfsFilePath_;
    }

    public void setHdfsFilePath(PHdfsFilePath node)
    {
        if(this._hdfsFilePath_ != null)
        {
            this._hdfsFilePath_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._hdfsFilePath_ = node;
    }

    public TExport getExport()
    {
        return this._export_;
    }

    public void setExport(TExport node)
    {
        if(this._export_ != null)
        {
            this._export_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._export_ = node;
    }

    public TTo getTo()
    {
        return this._to_;
    }

    public void setTo(TTo node)
    {
        if(this._to_ != null)
        {
            this._to_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._to_ = node;
    }

    public PLocalFilePath getLocalFilePath()
    {
        return this._localFilePath_;
    }

    public void setLocalFilePath(PLocalFilePath node)
    {
        if(this._localFilePath_ != null)
        {
            this._localFilePath_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._localFilePath_ = node;
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

    public PLocalFile getLocalFile()
    {
        return this._localFile_;
    }

    public void setLocalFile(PLocalFile node)
    {
        if(this._localFile_ != null)
        {
            this._localFile_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._localFile_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._store_)
            + toString(this._into_)
            + toString(this._hdfsFilePath_)
            + toString(this._export_)
            + toString(this._to_)
            + toString(this._localFilePath_)
            + toString(this._as_)
            + toString(this._localFile_);
    }

    @Override
    void removeChild( Node child)
    {
        // Remove child
        if(this._store_ == child)
        {
            this._store_ = null;
            return;
        }

        if(this._into_ == child)
        {
            this._into_ = null;
            return;
        }

        if(this._hdfsFilePath_ == child)
        {
            this._hdfsFilePath_ = null;
            return;
        }

        if(this._export_ == child)
        {
            this._export_ = null;
            return;
        }

        if(this._to_ == child)
        {
            this._to_ = null;
            return;
        }

        if(this._localFilePath_ == child)
        {
            this._localFilePath_ = null;
            return;
        }

        if(this._as_ == child)
        {
            this._as_ = null;
            return;
        }

        if(this._localFile_ == child)
        {
            this._localFile_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild( Node oldChild,  Node newChild)
    {
        // Replace child
        if(this._store_ == oldChild)
        {
            setStore((TStore) newChild);
            return;
        }

        if(this._into_ == oldChild)
        {
            setInto((TInto) newChild);
            return;
        }

        if(this._hdfsFilePath_ == oldChild)
        {
            setHdfsFilePath((PHdfsFilePath) newChild);
            return;
        }

        if(this._export_ == oldChild)
        {
            setExport((TExport) newChild);
            return;
        }

        if(this._to_ == oldChild)
        {
            setTo((TTo) newChild);
            return;
        }

        if(this._localFilePath_ == oldChild)
        {
            setLocalFilePath((PLocalFilePath) newChild);
            return;
        }

        if(this._as_ == oldChild)
        {
            setAs((TAs) newChild);
            return;
        }

        if(this._localFile_ == oldChild)
        {
            setLocalFile((PLocalFile) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
