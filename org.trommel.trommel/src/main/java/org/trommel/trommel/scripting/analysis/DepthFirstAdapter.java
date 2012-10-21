/* This file was generated by SableCC (http://www.sablecc.org/). */

package org.trommel.trommel.scripting.analysis;

import org.trommel.trommel.scripting.node.*;

public class DepthFirstAdapter extends AnalysisAdapter
{
    public void inStart(Start node)
    {
        defaultIn(node);
    }

    public void outStart(Start node)
    {
        defaultOut(node);
    }

    public void defaultIn( Node node)
    {
        // Do nothing
    }

    public void defaultOut( Node node)
    {
        // Do nothing
    }

    @Override
    public void caseStart(Start node)
    {
        inStart(node);
        node.getPTrommelScript().apply(this);
        node.getEOF().apply(this);
        outStart(node);
    }

    public void inAProfileDataTrommelScript(AProfileDataTrommelScript node)
    {
        defaultIn(node);
    }

    public void outAProfileDataTrommelScript(AProfileDataTrommelScript node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAProfileDataTrommelScript(AProfileDataTrommelScript node)
    {
        inAProfileDataTrommelScript(node);
        if(node.getLoadDataStatement() != null)
        {
            node.getLoadDataStatement().apply(this);
        }
        if(node.getProfileDataStatement() != null)
        {
            node.getProfileDataStatement().apply(this);
        }
        outAProfileDataTrommelScript(node);
    }

    public void inAReportDataTrommelScript(AReportDataTrommelScript node)
    {
        defaultIn(node);
    }

    public void outAReportDataTrommelScript(AReportDataTrommelScript node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAReportDataTrommelScript(AReportDataTrommelScript node)
    {
        inAReportDataTrommelScript(node);
        if(node.getLoadDataStatement() != null)
        {
            node.getLoadDataStatement().apply(this);
        }
        if(node.getReportDataStatement() != null)
        {
            node.getReportDataStatement().apply(this);
        }
        outAReportDataTrommelScript(node);
    }

    public void inASampleDataTrommelScript(ASampleDataTrommelScript node)
    {
        defaultIn(node);
    }

    public void outASampleDataTrommelScript(ASampleDataTrommelScript node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASampleDataTrommelScript(ASampleDataTrommelScript node)
    {
        inASampleDataTrommelScript(node);
        if(node.getSampleDataStatement() != null)
        {
            node.getSampleDataStatement().apply(this);
        }
        outASampleDataTrommelScript(node);
    }

    public void inALoadDataStatement(ALoadDataStatement node)
    {
        defaultIn(node);
    }

    public void outALoadDataStatement(ALoadDataStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALoadDataStatement(ALoadDataStatement node)
    {
        inALoadDataStatement(node);
        if(node.getLoad() != null)
        {
            node.getLoad().apply(this);
        }
        if(node.getData() != null)
        {
            node.getData().apply(this);
        }
        if(node.getLoadedFile() != null)
        {
            node.getLoadedFile().apply(this);
        }
        if(node.getAs() != null)
        {
            node.getAs().apply(this);
        }
        if(node.getLoadedFields() != null)
        {
            node.getLoadedFields().apply(this);
        }
        if(node.getFieldsTerminatedBy() != null)
        {
            node.getFieldsTerminatedBy().apply(this);
        }
        outALoadDataStatement(node);
    }

    public void inALoadedFile(ALoadedFile node)
    {
        defaultIn(node);
    }

    public void outALoadedFile(ALoadedFile node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALoadedFile(ALoadedFile node)
    {
        inALoadedFile(node);
        if(node.getQuotedString() != null)
        {
            node.getQuotedString().apply(this);
        }
        outALoadedFile(node);
    }

    public void inALoadedFields(ALoadedFields node)
    {
        defaultIn(node);
    }

    public void outALoadedFields(ALoadedFields node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALoadedFields(ALoadedFields node)
    {
        inALoadedFields(node);
        if(node.getLeftParen() != null)
        {
            node.getLeftParen().apply(this);
        }
        if(node.getFieldList() != null)
        {
            node.getFieldList().apply(this);
        }
        if(node.getRightParen() != null)
        {
            node.getRightParen().apply(this);
        }
        outALoadedFields(node);
    }

    public void inASingleFieldList(ASingleFieldList node)
    {
        defaultIn(node);
    }

    public void outASingleFieldList(ASingleFieldList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASingleFieldList(ASingleFieldList node)
    {
        inASingleFieldList(node);
        if(node.getField() != null)
        {
            node.getField().apply(this);
        }
        outASingleFieldList(node);
    }

    public void inAListFieldList(AListFieldList node)
    {
        defaultIn(node);
    }

    public void outAListFieldList(AListFieldList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAListFieldList(AListFieldList node)
    {
        inAListFieldList(node);
        if(node.getFieldList() != null)
        {
            node.getFieldList().apply(this);
        }
        if(node.getComma() != null)
        {
            node.getComma().apply(this);
        }
        if(node.getField() != null)
        {
            node.getField().apply(this);
        }
        outAListFieldList(node);
    }

    public void inAField(AField node)
    {
        defaultIn(node);
    }

    public void outAField(AField node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAField(AField node)
    {
        inAField(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        if(node.getColon() != null)
        {
            node.getColon().apply(this);
        }
        if(node.getFieldType() != null)
        {
            node.getFieldType().apply(this);
        }
        outAField(node);
    }

    public void inADefaultFieldsTerminatedBy(ADefaultFieldsTerminatedBy node)
    {
        defaultIn(node);
    }

    public void outADefaultFieldsTerminatedBy(ADefaultFieldsTerminatedBy node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADefaultFieldsTerminatedBy(ADefaultFieldsTerminatedBy node)
    {
        inADefaultFieldsTerminatedBy(node);
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outADefaultFieldsTerminatedBy(node);
    }

    public void inACustomFieldsTerminatedBy(ACustomFieldsTerminatedBy node)
    {
        defaultIn(node);
    }

    public void outACustomFieldsTerminatedBy(ACustomFieldsTerminatedBy node)
    {
        defaultOut(node);
    }

    @Override
    public void caseACustomFieldsTerminatedBy(ACustomFieldsTerminatedBy node)
    {
        inACustomFieldsTerminatedBy(node);
        if(node.getFields() != null)
        {
            node.getFields().apply(this);
        }
        if(node.getTerminated() != null)
        {
            node.getTerminated().apply(this);
        }
        if(node.getBy() != null)
        {
            node.getBy().apply(this);
        }
        if(node.getFieldTerminator() != null)
        {
            node.getFieldTerminator().apply(this);
        }
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outACustomFieldsTerminatedBy(node);
    }

    public void inAFieldTerminator(AFieldTerminator node)
    {
        defaultIn(node);
    }

    public void outAFieldTerminator(AFieldTerminator node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAFieldTerminator(AFieldTerminator node)
    {
        inAFieldTerminator(node);
        if(node.getQuotedString() != null)
        {
            node.getQuotedString().apply(this);
        }
        outAFieldTerminator(node);
    }

    public void inAProfileDataStatement(AProfileDataStatement node)
    {
        defaultIn(node);
    }

    public void outAProfileDataStatement(AProfileDataStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAProfileDataStatement(AProfileDataStatement node)
    {
        inAProfileDataStatement(node);
        if(node.getProfile() != null)
        {
            node.getProfile().apply(this);
        }
        if(node.getProfiledFields() != null)
        {
            node.getProfiledFields().apply(this);
        }
        if(node.getWith() != null)
        {
            node.getWith().apply(this);
        }
        if(node.getProfilers() != null)
        {
            node.getProfilers().apply(this);
        }
        if(node.getStorage() != null)
        {
            node.getStorage().apply(this);
        }
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outAProfileDataStatement(node);
    }

    public void inASingleProfiledFields(ASingleProfiledFields node)
    {
        defaultIn(node);
    }

    public void outASingleProfiledFields(ASingleProfiledFields node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASingleProfiledFields(ASingleProfiledFields node)
    {
        inASingleProfiledFields(node);
        if(node.getProfiledField() != null)
        {
            node.getProfiledField().apply(this);
        }
        outASingleProfiledFields(node);
    }

    public void inAListProfiledFields(AListProfiledFields node)
    {
        defaultIn(node);
    }

    public void outAListProfiledFields(AListProfiledFields node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAListProfiledFields(AListProfiledFields node)
    {
        inAListProfiledFields(node);
        if(node.getProfiledFields() != null)
        {
            node.getProfiledFields().apply(this);
        }
        if(node.getComma() != null)
        {
            node.getComma().apply(this);
        }
        if(node.getProfiledField() != null)
        {
            node.getProfiledField().apply(this);
        }
        outAListProfiledFields(node);
    }

    public void inAProfiledField(AProfiledField node)
    {
        defaultIn(node);
    }

    public void outAProfiledField(AProfiledField node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAProfiledField(AProfiledField node)
    {
        inAProfiledField(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAProfiledField(node);
    }

    public void inAListProfilers(AListProfilers node)
    {
        defaultIn(node);
    }

    public void outAListProfilers(AListProfilers node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAListProfilers(AListProfilers node)
    {
        inAListProfilers(node);
        if(node.getFunctionList() != null)
        {
            node.getFunctionList().apply(this);
        }
        outAListProfilers(node);
    }

    public void inAAllBuiltinProfilers(AAllBuiltinProfilers node)
    {
        defaultIn(node);
    }

    public void outAAllBuiltinProfilers(AAllBuiltinProfilers node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAAllBuiltinProfilers(AAllBuiltinProfilers node)
    {
        inAAllBuiltinProfilers(node);
        if(node.getAll() != null)
        {
            node.getAll().apply(this);
        }
        if(node.getBuiltin() != null)
        {
            node.getBuiltin().apply(this);
        }
        outAAllBuiltinProfilers(node);
    }

    public void inASingleFunctionList(ASingleFunctionList node)
    {
        defaultIn(node);
    }

    public void outASingleFunctionList(ASingleFunctionList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASingleFunctionList(ASingleFunctionList node)
    {
        inASingleFunctionList(node);
        if(node.getFunction() != null)
        {
            node.getFunction().apply(this);
        }
        outASingleFunctionList(node);
    }

    public void inAListFunctionList(AListFunctionList node)
    {
        defaultIn(node);
    }

    public void outAListFunctionList(AListFunctionList node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAListFunctionList(AListFunctionList node)
    {
        inAListFunctionList(node);
        if(node.getFunctionList() != null)
        {
            node.getFunctionList().apply(this);
        }
        if(node.getComma() != null)
        {
            node.getComma().apply(this);
        }
        if(node.getFunction() != null)
        {
            node.getFunction().apply(this);
        }
        outAListFunctionList(node);
    }

    public void inAMaxFunction(AMaxFunction node)
    {
        defaultIn(node);
    }

    public void outAMaxFunction(AMaxFunction node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMaxFunction(AMaxFunction node)
    {
        inAMaxFunction(node);
        if(node.getMax() != null)
        {
            node.getMax().apply(this);
        }
        outAMaxFunction(node);
    }

    public void inAMinFunction(AMinFunction node)
    {
        defaultIn(node);
    }

    public void outAMinFunction(AMinFunction node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAMinFunction(AMinFunction node)
    {
        inAMinFunction(node);
        if(node.getMin() != null)
        {
            node.getMin().apply(this);
        }
        outAMinFunction(node);
    }

    public void inADistinctFunction(ADistinctFunction node)
    {
        defaultIn(node);
    }

    public void outADistinctFunction(ADistinctFunction node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADistinctFunction(ADistinctFunction node)
    {
        inADistinctFunction(node);
        if(node.getDistinct() != null)
        {
            node.getDistinct().apply(this);
        }
        outADistinctFunction(node);
    }

    public void inAEmptyFunction(AEmptyFunction node)
    {
        defaultIn(node);
    }

    public void outAEmptyFunction(AEmptyFunction node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAEmptyFunction(AEmptyFunction node)
    {
        inAEmptyFunction(node);
        if(node.getEmpty() != null)
        {
            node.getEmpty().apply(this);
        }
        outAEmptyFunction(node);
    }

    public void inAReqFunction(AReqFunction node)
    {
        defaultIn(node);
    }

    public void outAReqFunction(AReqFunction node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAReqFunction(AReqFunction node)
    {
        inAReqFunction(node);
        if(node.getReq() != null)
        {
            node.getReq().apply(this);
        }
        outAReqFunction(node);
    }

    public void inAVarFunction(AVarFunction node)
    {
        defaultIn(node);
    }

    public void outAVarFunction(AVarFunction node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAVarFunction(AVarFunction node)
    {
        inAVarFunction(node);
        if(node.getVar() != null)
        {
            node.getVar().apply(this);
        }
        outAVarFunction(node);
    }

    public void inALinFunction(ALinFunction node)
    {
        defaultIn(node);
    }

    public void outALinFunction(ALinFunction node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALinFunction(ALinFunction node)
    {
        inALinFunction(node);
        if(node.getLinearity() != null)
        {
            node.getLinearity().apply(this);
        }
        outALinFunction(node);
    }

    public void inAConfFunction(AConfFunction node)
    {
        defaultIn(node);
    }

    public void outAConfFunction(AConfFunction node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAConfFunction(AConfFunction node)
    {
        inAConfFunction(node);
        if(node.getConfidence() != null)
        {
            node.getConfidence().apply(this);
        }
        outAConfFunction(node);
    }

    public void inADefaultLinearity(ADefaultLinearity node)
    {
        defaultIn(node);
    }

    public void outADefaultLinearity(ADefaultLinearity node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADefaultLinearity(ADefaultLinearity node)
    {
        inADefaultLinearity(node);
        if(node.getLin() != null)
        {
            node.getLin().apply(this);
        }
        outADefaultLinearity(node);
    }

    public void inADefaultParenLinearity(ADefaultParenLinearity node)
    {
        defaultIn(node);
    }

    public void outADefaultParenLinearity(ADefaultParenLinearity node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADefaultParenLinearity(ADefaultParenLinearity node)
    {
        inADefaultParenLinearity(node);
        if(node.getLin() != null)
        {
            node.getLin().apply(this);
        }
        if(node.getLeftParen() != null)
        {
            node.getLeftParen().apply(this);
        }
        if(node.getRightParen() != null)
        {
            node.getRightParen().apply(this);
        }
        outADefaultParenLinearity(node);
    }

    public void inAParmLinearity(AParmLinearity node)
    {
        defaultIn(node);
    }

    public void outAParmLinearity(AParmLinearity node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAParmLinearity(AParmLinearity node)
    {
        inAParmLinearity(node);
        if(node.getLin() != null)
        {
            node.getLin().apply(this);
        }
        if(node.getLeftParen() != null)
        {
            node.getLeftParen().apply(this);
        }
        if(node.getInteger() != null)
        {
            node.getInteger().apply(this);
        }
        if(node.getRightParen() != null)
        {
            node.getRightParen().apply(this);
        }
        outAParmLinearity(node);
    }

    public void inADefaultConfidence(ADefaultConfidence node)
    {
        defaultIn(node);
    }

    public void outADefaultConfidence(ADefaultConfidence node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADefaultConfidence(ADefaultConfidence node)
    {
        inADefaultConfidence(node);
        if(node.getConf() != null)
        {
            node.getConf().apply(this);
        }
        outADefaultConfidence(node);
    }

    public void inADefaultParenConfidence(ADefaultParenConfidence node)
    {
        defaultIn(node);
    }

    public void outADefaultParenConfidence(ADefaultParenConfidence node)
    {
        defaultOut(node);
    }

    @Override
    public void caseADefaultParenConfidence(ADefaultParenConfidence node)
    {
        inADefaultParenConfidence(node);
        if(node.getConf() != null)
        {
            node.getConf().apply(this);
        }
        if(node.getLeftParen() != null)
        {
            node.getLeftParen().apply(this);
        }
        if(node.getRightParen() != null)
        {
            node.getRightParen().apply(this);
        }
        outADefaultParenConfidence(node);
    }

    public void inAParmConfidence(AParmConfidence node)
    {
        defaultIn(node);
    }

    public void outAParmConfidence(AParmConfidence node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAParmConfidence(AParmConfidence node)
    {
        inAParmConfidence(node);
        if(node.getConf() != null)
        {
            node.getConf().apply(this);
        }
        if(node.getLeftParen() != null)
        {
            node.getLeftParen().apply(this);
        }
        if(node.getInteger() != null)
        {
            node.getInteger().apply(this);
        }
        if(node.getRightParen() != null)
        {
            node.getRightParen().apply(this);
        }
        outAParmConfidence(node);
    }

    public void inAReportDataStatement(AReportDataStatement node)
    {
        defaultIn(node);
    }

    public void outAReportDataStatement(AReportDataStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAReportDataStatement(AReportDataStatement node)
    {
        inAReportDataStatement(node);
        if(node.getReport() != null)
        {
            node.getReport().apply(this);
        }
        if(node.getData() != null)
        {
            node.getData().apply(this);
        }
        if(node.getFor() != null)
        {
            node.getFor().apply(this);
        }
        if(node.getReportedFields() != null)
        {
            node.getReportedFields().apply(this);
        }
        if(node.getStorage() != null)
        {
            node.getStorage().apply(this);
        }
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outAReportDataStatement(node);
    }

    public void inASingleReportedFields(ASingleReportedFields node)
    {
        defaultIn(node);
    }

    public void outASingleReportedFields(ASingleReportedFields node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASingleReportedFields(ASingleReportedFields node)
    {
        inASingleReportedFields(node);
        if(node.getReportedField() != null)
        {
            node.getReportedField().apply(this);
        }
        outASingleReportedFields(node);
    }

    public void inAListReportedFields(AListReportedFields node)
    {
        defaultIn(node);
    }

    public void outAListReportedFields(AListReportedFields node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAListReportedFields(AListReportedFields node)
    {
        inAListReportedFields(node);
        if(node.getReportedFields() != null)
        {
            node.getReportedFields().apply(this);
        }
        if(node.getComma() != null)
        {
            node.getComma().apply(this);
        }
        if(node.getReportedField() != null)
        {
            node.getReportedField().apply(this);
        }
        outAListReportedFields(node);
    }

    public void inAReportedField(AReportedField node)
    {
        defaultIn(node);
    }

    public void outAReportedField(AReportedField node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAReportedField(AReportedField node)
    {
        inAReportedField(node);
        if(node.getIdentifier() != null)
        {
            node.getIdentifier().apply(this);
        }
        outAReportedField(node);
    }

    public void inASampleDataStatement(ASampleDataStatement node)
    {
        defaultIn(node);
    }

    public void outASampleDataStatement(ASampleDataStatement node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASampleDataStatement(ASampleDataStatement node)
    {
        inASampleDataStatement(node);
        if(node.getSample() != null)
        {
            node.getSample().apply(this);
        }
        if(node.getData() != null)
        {
            node.getData().apply(this);
        }
        if(node.getSampledFile() != null)
        {
            node.getSampledFile().apply(this);
        }
        if(node.getAt() != null)
        {
            node.getAt().apply(this);
        }
        if(node.getSampleRate() != null)
        {
            node.getSampleRate().apply(this);
        }
        if(node.getStorage() != null)
        {
            node.getStorage().apply(this);
        }
        if(node.getSemicolon() != null)
        {
            node.getSemicolon().apply(this);
        }
        outASampleDataStatement(node);
    }

    public void inASampledFile(ASampledFile node)
    {
        defaultIn(node);
    }

    public void outASampledFile(ASampledFile node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASampledFile(ASampledFile node)
    {
        inASampledFile(node);
        if(node.getQuotedString() != null)
        {
            node.getQuotedString().apply(this);
        }
        outASampledFile(node);
    }

    public void inASampleRate(ASampleRate node)
    {
        defaultIn(node);
    }

    public void outASampleRate(ASampleRate node)
    {
        defaultOut(node);
    }

    @Override
    public void caseASampleRate(ASampleRate node)
    {
        inASampleRate(node);
        if(node.getInteger() != null)
        {
            node.getInteger().apply(this);
        }
        if(node.getPercent() != null)
        {
            node.getPercent().apply(this);
        }
        outASampleRate(node);
    }

    public void inAStoreStorage(AStoreStorage node)
    {
        defaultIn(node);
    }

    public void outAStoreStorage(AStoreStorage node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAStoreStorage(AStoreStorage node)
    {
        inAStoreStorage(node);
        if(node.getStore() != null)
        {
            node.getStore().apply(this);
        }
        if(node.getInto() != null)
        {
            node.getInto().apply(this);
        }
        if(node.getHdfsFilePath() != null)
        {
            node.getHdfsFilePath().apply(this);
        }
        outAStoreStorage(node);
    }

    public void inAExportStorage(AExportStorage node)
    {
        defaultIn(node);
    }

    public void outAExportStorage(AExportStorage node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAExportStorage(AExportStorage node)
    {
        inAExportStorage(node);
        if(node.getExport() != null)
        {
            node.getExport().apply(this);
        }
        if(node.getTo() != null)
        {
            node.getTo().apply(this);
        }
        if(node.getLocalFilePath() != null)
        {
            node.getLocalFilePath().apply(this);
        }
        outAExportStorage(node);
    }

    public void inAStoreExportStorage(AStoreExportStorage node)
    {
        defaultIn(node);
    }

    public void outAStoreExportStorage(AStoreExportStorage node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAStoreExportStorage(AStoreExportStorage node)
    {
        inAStoreExportStorage(node);
        if(node.getStore() != null)
        {
            node.getStore().apply(this);
        }
        if(node.getInto() != null)
        {
            node.getInto().apply(this);
        }
        if(node.getHdfsFilePath() != null)
        {
            node.getHdfsFilePath().apply(this);
        }
        if(node.getExport() != null)
        {
            node.getExport().apply(this);
        }
        if(node.getTo() != null)
        {
            node.getTo().apply(this);
        }
        if(node.getLocalFilePath() != null)
        {
            node.getLocalFilePath().apply(this);
        }
        outAStoreExportStorage(node);
    }

    public void inAExportStoreStorage(AExportStoreStorage node)
    {
        defaultIn(node);
    }

    public void outAExportStoreStorage(AExportStoreStorage node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAExportStoreStorage(AExportStoreStorage node)
    {
        inAExportStoreStorage(node);
        if(node.getExport() != null)
        {
            node.getExport().apply(this);
        }
        if(node.getTo() != null)
        {
            node.getTo().apply(this);
        }
        if(node.getLocalFilePath() != null)
        {
            node.getLocalFilePath().apply(this);
        }
        if(node.getStore() != null)
        {
            node.getStore().apply(this);
        }
        if(node.getInto() != null)
        {
            node.getInto().apply(this);
        }
        if(node.getHdfsFilePath() != null)
        {
            node.getHdfsFilePath().apply(this);
        }
        outAExportStoreStorage(node);
    }

    public void inAHdfsFilePath(AHdfsFilePath node)
    {
        defaultIn(node);
    }

    public void outAHdfsFilePath(AHdfsFilePath node)
    {
        defaultOut(node);
    }

    @Override
    public void caseAHdfsFilePath(AHdfsFilePath node)
    {
        inAHdfsFilePath(node);
        if(node.getQuotedString() != null)
        {
            node.getQuotedString().apply(this);
        }
        outAHdfsFilePath(node);
    }

    public void inALocalFilePath(ALocalFilePath node)
    {
        defaultIn(node);
    }

    public void outALocalFilePath(ALocalFilePath node)
    {
        defaultOut(node);
    }

    @Override
    public void caseALocalFilePath(ALocalFilePath node)
    {
        inALocalFilePath(node);
        if(node.getQuotedString() != null)
        {
            node.getQuotedString().apply(this);
        }
        outALocalFilePath(node);
    }
}
