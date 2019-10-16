package com.databasepreservation.common.shared.client.common.visualization.browse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.databasepreservation.common.shared.ViewerStructure.ViewerDatabase;
import com.databasepreservation.common.shared.ViewerStructure.ViewerRoutine;
import com.databasepreservation.common.shared.ViewerStructure.ViewerRoutineParameter;
import com.databasepreservation.common.shared.ViewerStructure.ViewerSchema;
import com.databasepreservation.common.shared.client.breadcrumb.BreadcrumbPanel;
import com.databasepreservation.common.shared.client.common.MetadataField;
import com.databasepreservation.common.shared.client.common.RightPanel;
import com.databasepreservation.common.shared.client.common.desktop.GenericField;
import com.databasepreservation.common.shared.client.common.lists.BasicTablePanel;
import com.databasepreservation.common.shared.client.common.utils.JavascriptUtils;
import com.databasepreservation.common.shared.client.tools.BreadcrumbManager;
import com.databasepreservation.common.shared.client.tools.ViewerStringUtils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;

import config.i18n.client.ClientMessages;

/**
 * @author Bruno Ferreira <bferreira@keep.pt>
 */
public class SchemaRoutinesPanel extends RightPanel {
  private static final ClientMessages messages = GWT.create(ClientMessages.class);
  private static Map<String, SchemaRoutinesPanel> instances = new HashMap<>();

  public static SchemaRoutinesPanel getInstance(ViewerDatabase database) {
    SchemaRoutinesPanel instance = instances.get(database.getUUID());
    if (instance == null) {
      instance = new SchemaRoutinesPanel(database);
      instances.put(database.getUUID(), instance);
    }
    return instance;
  }

  interface SchemaRoutinesUiBinder extends UiBinder<Widget, SchemaRoutinesPanel> {
  }

  private static SchemaRoutinesUiBinder uiBinder = GWT.create(SchemaRoutinesUiBinder.class);

  private ViewerDatabase database;

  @UiField
  FlowPanel contentItems;

  @UiField
  Label title;

  private SchemaRoutinesPanel(ViewerDatabase database) {
    this.database = database;
    initWidget(uiBinder.createAndBindUi(this));
    init();
  }

  @Override
  public void handleBreadcrumb(BreadcrumbPanel breadcrumb) {
      BreadcrumbManager.updateBreadcrumb(breadcrumb, BreadcrumbManager.forSchemaRoutines(database.getUUID(), database.getMetadata().getName()          ));
  }

  private void init() {
    if (database.getMetadata().getSchemas().size() == 1) {
      contentItems.add(buildRoutinesForSingleSchema(database.getMetadata().getSchemas().get(0)));
    } else {
      buildRoutinesForMultipleSchemas();
    }

    title.setText(messages.menusidebar_routines());

    JavascriptUtils.runHighlighter(contentItems.getElement());
  }

  private FlowPanel buildRoutinesForSingleSchema(ViewerSchema schema) {
    FlowPanel panel = new FlowPanel();

    List<ViewerRoutine> routines = new ArrayList<>(schema.getRoutines());
    routines.sort(Comparator.comparing(ViewerRoutine::getName));

    if (routines.isEmpty()) {
      Label noRoutinesMsg = new Label(messages.routines_thisSchemaDoesNotHaveAnyRoutines());
      noRoutinesMsg.addStyleName("strong");
      panel.add(noRoutinesMsg);
    } else {
      for (ViewerRoutine viewerRoutine : routines) {
        if (viewerRoutine.getParameters().isEmpty()) {
          panel.add(addRoutineHeaderAndDescription(viewerRoutine));
        } else {
          panel.add(getBasicTablePanelForSchemaRoutines(viewerRoutine));
        }
      }
    }

    return panel;
  }

  private void buildRoutinesForMultipleSchemas() {
    TabPanel tabPanel = new TabPanel();
    for (ViewerSchema schema : database.getMetadata().getSchemas()) {
      tabPanel.addStyleName("browseItemMetadata");
      tabPanel.add(buildRoutinesForSingleSchema(schema), schema.getName());

    }
    tabPanel.selectTab(0);
    contentItems.add(tabPanel);
  }

  private FlowPanel getRoutineDescription(ViewerRoutine viewerRoutine) {
    FlowPanel panel = new FlowPanel();

    if (ViewerStringUtils.isNotBlank(viewerRoutine.getName())) {
      MetadataField schemaName = MetadataField.createInstance(messages.name(), viewerRoutine.getName());
      schemaName.setCSSMetadata("metadata-field", "metadata-information-element-label",
              "metadata-information-element-value");
      panel.add(schemaName);
    }
    if (ViewerStringUtils.isNotBlank(viewerRoutine.getDescription())) {
      MetadataField description = MetadataField.createInstance(messages.description(), viewerRoutine.getDescription());
      description.setCSSMetadata("metadata-field", "metadata-information-element-label",
              "metadata-information-element-value");
      panel.add(description);
    }
    if (ViewerStringUtils.isNotBlank(viewerRoutine.getSource())) {
      MetadataField sourceCode = MetadataField.createInstance(messages.routine_sourceCode(), viewerRoutine.getSource());
      sourceCode.setCSSMetadata("metadata-field", "metadata-information-element-label",
              "metadata-information-element-value");
      panel.add(sourceCode);
    }
    if (ViewerStringUtils.isNotBlank(viewerRoutine.getBody())) {
      GenericField field = GenericField.createInstance(messages.routine_body(), new HTMLPanel(SafeHtmlUtils
              .fromSafeConstant("<pre><code>" + SafeHtmlUtils.htmlEscape(viewerRoutine.getBody()) + "</code></pre>")));
      field.setCSSMetadata("metadata-field", "metadata-information-element-label");
      panel.add(field);
    }
    if (ViewerStringUtils.isNotBlank(viewerRoutine.getCharacteristic())) {
      MetadataField field = MetadataField.createInstance(messages.routine_characteristic(), viewerRoutine.getCharacteristic());
      field.setCSSMetadata("metadata-field", "metadata-information-element-label",
              "metadata-information-element-value");
      panel.add(field);
    }
    if (ViewerStringUtils.isNotBlank(viewerRoutine.getReturnType())) {
      MetadataField field = MetadataField.createInstance(messages.routine_returnType(), viewerRoutine.getReturnType());
      field.setCSSMetadata("metadata-field", "metadata-information-element-label",
              "metadata-information-element-value");
      panel.add(field);
    }

    return panel;
  }

  private FlowPanel addRoutineHeaderAndDescription(ViewerRoutine routine) {
    FlowPanel panel = new FlowPanel();
    panel.addStyleName("card");

    Label header = new Label(routine.getName());
    header.addStyleName("h4");

    FlowPanel info = getRoutineDescription(routine);

    panel.add(header);
    panel.add(info);

    return panel;
  }

  private BasicTablePanel<ViewerRoutineParameter> getBasicTablePanelForSchemaRoutines(final ViewerRoutine routine) {
    Label header = new Label(routine.getName());
    header.addStyleName("h4");

    FlowPanel info = getRoutineDescription(routine);

    return new BasicTablePanel<ViewerRoutineParameter>(header, info, routine.getParameters().iterator(),

      new BasicTablePanel.ColumnInfo<>(messages.name(), 15, new TextColumn<ViewerRoutineParameter>() {
        @Override
        public String getValue(ViewerRoutineParameter viewerRoutineParameter) {
          return viewerRoutineParameter.getName();
        }
      }),

      new BasicTablePanel.ColumnInfo<>(messages.routineParameter_mode(), 15, new TextColumn<ViewerRoutineParameter>() {
        @Override
        public String getValue(ViewerRoutineParameter viewerRoutineParameter) {
          return viewerRoutineParameter.getMode();
        }
      }),

      new BasicTablePanel.ColumnInfo<>(messages.typeName(), 15, new TextColumn<ViewerRoutineParameter>() {
        @Override
        public String getValue(ViewerRoutineParameter viewerRoutineParameter) {
          if (viewerRoutineParameter.getType() != null) {
            if (ViewerStringUtils.isNotBlank(viewerRoutineParameter.getType().getTypeName())) {
              return viewerRoutineParameter.getType().getTypeName();
            }
          }
          return "";
        }
      }),

      new BasicTablePanel.ColumnInfo<>(messages.originalTypeName(), 15, new TextColumn<ViewerRoutineParameter>() {
        @Override
        public String getValue(ViewerRoutineParameter viewerRoutineParameter) {
          if (viewerRoutineParameter.getType() != null) {
            if (ViewerStringUtils.isNotBlank(viewerRoutineParameter.getType().getOriginalTypeName())) {
              return viewerRoutineParameter.getType().getOriginalTypeName();
            }
          }
          return "";
        }
      }),

      new BasicTablePanel.ColumnInfo<>(messages.description(), 35, new TextColumn<ViewerRoutineParameter>() {
        @Override
        public String getValue(ViewerRoutineParameter viewerRoutineParameter) {
          if (viewerRoutineParameter.getType() != null) {
            if (ViewerStringUtils.isNotBlank(viewerRoutineParameter.getDescription())) {
              return viewerRoutineParameter.getDescription();
            }
          }
          return "";
        }
      })

    );
  }
}
