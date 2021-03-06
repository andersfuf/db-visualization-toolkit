package com.databasepreservation.common.client.common.utils;

import com.databasepreservation.common.client.models.status.collection.CollectionStatus;
import com.databasepreservation.common.client.models.structure.ViewerDatabase;
import com.databasepreservation.common.client.common.ContentPanel;

/**
 * @author Gabriel Barros <gbarros@keep.pt>
 */
public abstract class ContentPanelLoader {
    public abstract ContentPanel load(ViewerDatabase database, CollectionStatus status);
}
