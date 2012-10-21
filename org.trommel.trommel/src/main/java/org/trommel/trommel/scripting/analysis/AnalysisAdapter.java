/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.analysis;

import java.util.*;
import org.trommel.trommel.scripting.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    public void caseAProfileDataTrommelScript(AProfileDataTrommelScript node)
    {
        defaultCase(node);
    }

    public void caseAReportDataTrommelScript(AReportDataTrommelScript node)
    {
        defaultCase(node);
    }

    public void caseASampleDataTrommelScript(ASampleDataTrommelScript node)
    {
        defaultCase(node);
    }

    public void caseALoadDataStatement(ALoadDataStatement node)
    {
        defaultCase(node);
    }

    public void caseALoadedFile(ALoadedFile node)
    {
        defaultCase(node);
    }

    public void caseALoadedFields(ALoadedFields node)
    {
        defaultCase(node);
    }

    public void caseASingleFieldList(ASingleFieldList node)
    {
        defaultCase(node);
    }

    public void caseAListFieldList(AListFieldList node)
    {
        defaultCase(node);
    }

    public void caseAField(AField node)
    {
        defaultCase(node);
    }

    public void caseADefaultFieldsTerminatedBy(ADefaultFieldsTerminatedBy node)
    {
        defaultCase(node);
    }

    public void caseACustomFieldsTerminatedBy(ACustomFieldsTerminatedBy node)
    {
        defaultCase(node);
    }

    public void caseAFieldTerminator(AFieldTerminator node)
    {
        defaultCase(node);
    }

    public void caseAProfileDataStatement(AProfileDataStatement node)
    {
        defaultCase(node);
    }

    public void caseASingleProfiledFields(ASingleProfiledFields node)
    {
        defaultCase(node);
    }

    public void caseAListProfiledFields(AListProfiledFields node)
    {
        defaultCase(node);
    }

    public void caseAProfiledField(AProfiledField node)
    {
        defaultCase(node);
    }

    public void caseAListProfilers(AListProfilers node)
    {
        defaultCase(node);
    }

    public void caseAAllBuiltinProfilers(AAllBuiltinProfilers node)
    {
        defaultCase(node);
    }

    public void caseASingleFunctionList(ASingleFunctionList node)
    {
        defaultCase(node);
    }

    public void caseAListFunctionList(AListFunctionList node)
    {
        defaultCase(node);
    }

    public void caseAMaxFunction(AMaxFunction node)
    {
        defaultCase(node);
    }

    public void caseAMinFunction(AMinFunction node)
    {
        defaultCase(node);
    }

    public void caseADistinctFunction(ADistinctFunction node)
    {
        defaultCase(node);
    }

    public void caseAEmptyFunction(AEmptyFunction node)
    {
        defaultCase(node);
    }

    public void caseAReqFunction(AReqFunction node)
    {
        defaultCase(node);
    }

    public void caseAVarFunction(AVarFunction node)
    {
        defaultCase(node);
    }

    public void caseALinFunction(ALinFunction node)
    {
        defaultCase(node);
    }

    public void caseAConfFunction(AConfFunction node)
    {
        defaultCase(node);
    }

    public void caseADefaultLinearity(ADefaultLinearity node)
    {
        defaultCase(node);
    }

    public void caseADefaultParenLinearity(ADefaultParenLinearity node)
    {
        defaultCase(node);
    }

    public void caseAParmLinearity(AParmLinearity node)
    {
        defaultCase(node);
    }

    public void caseADefaultConfidence(ADefaultConfidence node)
    {
        defaultCase(node);
    }

    public void caseADefaultParenConfidence(ADefaultParenConfidence node)
    {
        defaultCase(node);
    }

    public void caseAParmConfidence(AParmConfidence node)
    {
        defaultCase(node);
    }

    public void caseAReportDataStatement(AReportDataStatement node)
    {
        defaultCase(node);
    }

    public void caseASingleReportedFields(ASingleReportedFields node)
    {
        defaultCase(node);
    }

    public void caseAListReportedFields(AListReportedFields node)
    {
        defaultCase(node);
    }

    public void caseAReportedField(AReportedField node)
    {
        defaultCase(node);
    }

    public void caseASampleDataStatement(ASampleDataStatement node)
    {
        defaultCase(node);
    }

    public void caseASampleRate(ASampleRate node)
    {
        defaultCase(node);
    }

    public void caseAStoreStorage(AStoreStorage node)
    {
        defaultCase(node);
    }

    public void caseAExportStorage(AExportStorage node)
    {
        defaultCase(node);
    }

    public void caseAStoreExportStorage(AStoreExportStorage node)
    {
        defaultCase(node);
    }

    public void caseAExportStoreStorage(AExportStoreStorage node)
    {
        defaultCase(node);
    }

    public void caseAHdfsFilePath(AHdfsFilePath node)
    {
        defaultCase(node);
    }

    public void caseALocalFilePath(ALocalFilePath node)
    {
        defaultCase(node);
    }

    public void caseTLoad(TLoad node)
    {
        defaultCase(node);
    }

    public void caseTColon(TColon node)
    {
        defaultCase(node);
    }

    public void caseTFieldType(TFieldType node)
    {
        defaultCase(node);
    }

    public void caseTFields(TFields node)
    {
        defaultCase(node);
    }

    public void caseTTerminated(TTerminated node)
    {
        defaultCase(node);
    }

    public void caseTBy(TBy node)
    {
        defaultCase(node);
    }

    public void caseTProfile(TProfile node)
    {
        defaultCase(node);
    }

    public void caseTWith(TWith node)
    {
        defaultCase(node);
    }

    public void caseTAll(TAll node)
    {
        defaultCase(node);
    }

    public void caseTBuiltin(TBuiltin node)
    {
        defaultCase(node);
    }

    public void caseTMax(TMax node)
    {
        defaultCase(node);
    }

    public void caseTMin(TMin node)
    {
        defaultCase(node);
    }

    public void caseTDistinct(TDistinct node)
    {
        defaultCase(node);
    }

    public void caseTEmpty(TEmpty node)
    {
        defaultCase(node);
    }

    public void caseTReq(TReq node)
    {
        defaultCase(node);
    }

    public void caseTVar(TVar node)
    {
        defaultCase(node);
    }

    public void caseTConf(TConf node)
    {
        defaultCase(node);
    }

    public void caseTLin(TLin node)
    {
        defaultCase(node);
    }

    public void caseTReport(TReport node)
    {
        defaultCase(node);
    }

    public void caseTFor(TFor node)
    {
        defaultCase(node);
    }

    public void caseTSample(TSample node)
    {
        defaultCase(node);
    }

    public void caseTAt(TAt node)
    {
        defaultCase(node);
    }

    public void caseTPercent(TPercent node)
    {
        defaultCase(node);
    }

    public void caseTData(TData node)
    {
        defaultCase(node);
    }

    public void caseTStore(TStore node)
    {
        defaultCase(node);
    }

    public void caseTInto(TInto node)
    {
        defaultCase(node);
    }

    public void caseTExport(TExport node)
    {
        defaultCase(node);
    }

    public void caseTTo(TTo node)
    {
        defaultCase(node);
    }

    public void caseTSemicolon(TSemicolon node)
    {
        defaultCase(node);
    }

    public void caseTLeftParen(TLeftParen node)
    {
        defaultCase(node);
    }

    public void caseTRightParen(TRightParen node)
    {
        defaultCase(node);
    }

    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    public void caseTAs(TAs node)
    {
        defaultCase(node);
    }

    public void caseTQuotedString(TQuotedString node)
    {
        defaultCase(node);
    }

    public void caseTIdentifier(TIdentifier node)
    {
        defaultCase(node);
    }

    public void caseTInteger(TInteger node)
    {
        defaultCase(node);
    }

    public void caseTBlockComment(TBlockComment node)
    {
        defaultCase(node);
    }

    public void caseTSingleLineComment(TSingleLineComment node)
    {
        defaultCase(node);
    }

    public void caseTWhiteSpace(TWhiteSpace node)
    {
        defaultCase(node);
    }

    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    public void defaultCase(Node node)
    {
        // do nothing
    }
}
