package com.portal.common.web.editor;

import com.portal.common.utils.DateHelper;

import java.beans.PropertyEditorSupport;

/**
 * @author dcp
 */
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        setValue(DateHelper.parseDate(text));
    }

}
