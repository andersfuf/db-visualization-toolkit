package com.databasepreservation.common.client.common.visualization.metadata.schemas.routines;

import com.databasepreservation.common.client.common.utils.CommonClientUtils;
import com.databasepreservation.common.client.common.utils.JavascriptUtils;
import com.databasepreservation.common.client.models.structure.ViewerRoutine;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

import config.i18n.client.ClientMessages;

/**
 * @author Gabriel Barros <gbarros@keep.pt>
 */
public class MetadataRoutineInfos {
  private static final ClientMessages messages = GWT.create(ClientMessages.class);
  private final ViewerRoutine routine;

  MetadataRoutineInfos(ViewerRoutine routine) {
    this.routine = routine;
  }

  public ScrollPanel createInfo() {
    FlowPanel flowPanel = new FlowPanel();

    if (routine.getSource() == null || routine.getSource().isEmpty()) {
      flowPanel.add(new HTMLPanel(CommonClientUtils.getFieldHTML(messages.routine_sourceCode(),
        messages.routines_thisRoutineFieldDoesNotHaveContent())));
    } else {
      flowPanel.add(new HTMLPanel(CommonClientUtils.getFieldHTML(messages.routine_sourceCode(), routine.getSource())));
    }

    if (routine.getBody() == null || routine.getBody().isEmpty()) {
      flowPanel.add(new HTMLPanel(CommonClientUtils.getFieldHTML(messages.routine_body(),
        messages.routines_thisRoutineFieldDoesNotHaveContent())));
    } else {
      flowPanel.add(new HTMLPanel(CommonClientUtils.getFieldHTML(messages.routine_body(), SafeHtmlUtils
        .fromSafeConstant("<pre><code>" + SafeHtmlUtils.htmlEscape(routine.getBody()) + "</code></pre>"))));
    }

    if (routine.getCharacteristic() == null || routine.getCharacteristic().isEmpty()) {
      flowPanel.add(new HTMLPanel(CommonClientUtils.getFieldHTML(messages.routine_characteristic(),
        messages.routines_thisRoutineFieldDoesNotHaveContent())));
    } else {
      flowPanel.add(
        new HTMLPanel(CommonClientUtils.getFieldHTML(messages.routine_characteristic(), routine.getCharacteristic())));
    }

    if (routine.getReturnType() == null || routine.getReturnType().isEmpty()) {
      flowPanel.add(new HTMLPanel(CommonClientUtils.getFieldHTML(messages.routine_returnType(),
        messages.routines_thisRoutineFieldDoesNotHaveContent())));
    } else {
      flowPanel
        .add(new HTMLPanel(CommonClientUtils.getFieldHTML(messages.routine_returnType(), routine.getReturnType())));
    }

    JavascriptUtils.runHighlighter(flowPanel.getElement());
    ScrollPanel panel = new ScrollPanel(flowPanel);
    panel.setSize("100%", "100%");
    return panel;
  }
}
