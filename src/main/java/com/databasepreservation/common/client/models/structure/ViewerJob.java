package com.databasepreservation.common.client.models.structure;

import com.databasepreservation.common.client.index.IsIndexed;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Gabriel Barros <gbarros@keep.pt>
 */
public class ViewerJob extends IsIndexed implements Serializable {

  private String uuid;
  private Long jobId;
  private String databaseUuid;
  private String databaseName;
  private String tableUuid;
  private String tableName;
  private String name;
  private ViewerJobStatus status;
  private Date createTime;
  private Date startTime;
  private Date endTime;
  private String exitCode;
  private String exitDescription;

  @Override
  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  @Override
  public String getUuid() {
    return uuid;
  }

  public Long getJobId() {
    return jobId;
  }

  public void setJobId(Long jobId) {
    this.jobId = jobId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ViewerJobStatus getStatus() {
    return status;
  }

  public void setStatus(ViewerJobStatus status) {
    this.status = status;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public void setDatabaseUuid(String databaseUuid) {
    this.databaseUuid = databaseUuid;
  }

  public String getDatabaseUuid() {
    return databaseUuid;
  }

  public void setTableUuid(String tableUuid) {
    this.tableUuid = tableUuid;
  }

  public String getTableUuid() {
    return tableUuid;
  }

  public String getExitCode() {
    return exitCode;
  }

  public void setExitCode(String exitCode) {
    this.exitCode = exitCode;
  }

  public String getExitDescription() {
    return exitDescription;
  }

  public void setExitDescription(String exitDescription) {
    this.exitDescription = exitDescription;
  }

  public String getDatabaseName() {
    return databaseName;
  }

  public void setDatabaseName(String databaseName) {
    this.databaseName = databaseName;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
}