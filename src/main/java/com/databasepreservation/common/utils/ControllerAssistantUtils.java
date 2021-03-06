package com.databasepreservation.common.utils;

import com.databasepreservation.common.client.models.activity.logs.ActivityLogEntry;
import com.databasepreservation.common.client.models.activity.logs.LogEntryState;
import com.databasepreservation.common.client.models.user.User;
import com.databasepreservation.common.server.ViewerFactory;
import com.databasepreservation.common.server.index.utils.SolrUtils;
import org.roda.core.data.exceptions.GenericException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Miguel Guimarães <mguimaraes@keep.pt>
 */
public class ControllerAssistantUtils {
  private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAssistantUtils.class);

  private ControllerAssistantUtils() {
    // do nothing
  }

  protected static void registerAction(User user, String actionComponent, String actionMethod, String relatedObjectId,
                                       long duration, LogEntryState state, Object... parameters) {
    ActivityLogEntry logEntry = createLogEntry(user, actionComponent, actionMethod, relatedObjectId, duration, state,
      parameters);
    registerAction(logEntry);
  }

  private static ActivityLogEntry createLogEntry(User user, String actionComponent, String actionMethod,
    String relatedObjectId, long duration, LogEntryState status, Map<String, String> parameters) {
    ActivityLogEntry logEntry = new ActivityLogEntry();
    logEntry.setUuid(SolrUtils.randomUUID());
    logEntry.setAddress(user.getIpAddress());
    logEntry.setUsername(user.getName());
    logEntry.setActionComponent(actionComponent);
    logEntry.setActionMethod(actionMethod);
    logEntry.setParameters(parameters);
    logEntry.setDuration(duration);
    logEntry.setDatetime(new Date());
    logEntry.setRelatedObjectID(relatedObjectId);
    logEntry.setState(status);
    return logEntry;
  }

  private static ActivityLogEntry createLogEntry(User user, String actionComponent, String actionMethod, String relatedObjectId,
                                                 long duration, LogEntryState status, Object... parameters) {
    Map<String, String> logParameters = new HashMap<>();
    if (parameters != null && parameters.length > 0) {
      if ((parameters.length % 2) != 0) {
        LOGGER.warn(
          "registerAction (actionComponent={}, actionMethod={}) failed because parameters array must have pairs of elements (even length)",
          actionComponent, actionMethod);
      } else {
        for (int i = 0; i < parameters.length; i += 2) {
          Object key = parameters[i];
          Object value = parameters[i + 1];
          logParameters.put(key != null ? key.toString() : "null", value != null ? value.toString() : "null");
        }
      }
    }
    return createLogEntry(user, actionComponent, actionMethod, relatedObjectId, duration, status, logParameters);
  }

  private static void registerAction(ActivityLogEntry logEntry) {
    try {
      ViewerFactory.getConfigurationManager().addLogEntry(logEntry,
        ViewerFactory.getViewerConfiguration().getActivityLogsPath());
    } catch (GenericException e) {
      LOGGER.error("Error registering action (actionComponent={}, actionMethod={})", logEntry.getActionComponent(),
        logEntry.getActionMethod(), e);
    }
  }
}
