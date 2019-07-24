/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/roda
 */
package com.databasepreservation.main.common.shared.client.common.dialogs;

import com.databasepreservation.main.common.shared.client.common.NoAsyncCallback;
import com.databasepreservation.main.desktop.client.common.ComboBoxField;
import com.databasepreservation.main.desktop.client.common.FileUploadField;
import com.databasepreservation.main.desktop.client.common.GenericField;
import com.databasepreservation.main.desktop.shared.models.ExternalLobsDialogBoxResult;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

import config.i18n.client.ClientMessages;

public class Dialogs {
  private static final ClientMessages messages = GWT.create(ClientMessages.class);

  public static void showExternalLobsSetupDialog(String title, ComboBoxField referencesType, GenericField genericField,
    String cancelButtonText, String confirmButtonText, boolean toDelete,
    final AsyncCallback<ExternalLobsDialogBoxResult> callback) {

    final DialogBox dialogBox = new DialogBox(false, true);
    dialogBox.setText(title);

    FlowPanel layout = new FlowPanel();
    Button cancelButton = new Button(cancelButtonText);
    Button confirmButton = new Button(confirmButtonText);
    Button deleteButton = null;
    if (toDelete) {
      deleteButton = new Button(messages.delete());
    }
    FlowPanel footer = new FlowPanel();

    layout.add(referencesType);
    layout.add(genericField);
    layout.add(footer);
    footer.add(cancelButton);
    footer.add(confirmButton);
    if (deleteButton != null) {
      deleteButton.addClickHandler(event -> {
        dialogBox.hide();
        ExternalLobsDialogBoxResult result = new ExternalLobsDialogBoxResult("delete", true);
        callback.onSuccess(result);
      });
    }

    dialogBox.setWidget(layout);

    dialogBox.setGlassEnabled(true);
    dialogBox.setAnimationEnabled(false);

    cancelButton.addClickHandler(event -> {
      dialogBox.hide();
      ExternalLobsDialogBoxResult result = new ExternalLobsDialogBoxResult("add", false);
      callback.onSuccess(result);
    });

    confirmButton.addClickHandler(event -> {
      dialogBox.hide();
      ExternalLobsDialogBoxResult result = new ExternalLobsDialogBoxResult("add", true);
      callback.onSuccess(result);
    });

    dialogBox.addStyleName("dialog-external-lobs");
    layout.addStyleName("dialog-external-lobs-layout");
    footer.addStyleName("dialog-external-lobs-layout-footer");
    FlowPanel btnItemCancelButton = new FlowPanel();
    btnItemCancelButton.addStyleName("btn-item");
    btnItemCancelButton.add(cancelButton);
    cancelButton.addStyleName("btn btn-link");
    FlowPanel btnItemConfirmButton = new FlowPanel();
    btnItemConfirmButton.addStyleName("btn-item");
    btnItemConfirmButton.add(confirmButton);
    confirmButton.addStyleName("btn btn-play");
    footer.add(btnItemCancelButton);
    footer.add(btnItemConfirmButton);
    if (deleteButton != null) {
      FlowPanel btnItemDeleteButton = new FlowPanel();
      btnItemDeleteButton.addStyleName("btn-item");
      btnItemDeleteButton.add(deleteButton);
      footer.add(btnItemDeleteButton);
      deleteButton.addStyleName("btn");
    }

    dialogBox.center();
    dialogBox.show();
  }

  public static void showExternalLobsSetupDialog(String title, ComboBoxField referencesType, FileUploadField genericField,
    String cancelButtonText, String confirmButtonText, boolean toDelete, final AsyncCallback<ExternalLobsDialogBoxResult> callback) {

    final DialogBox dialogBox = new DialogBox(false, true);
    dialogBox.setText(title);

    FlowPanel layout = new FlowPanel();
    Button cancelButton = new Button(cancelButtonText);
    Button confirmButton = new Button(confirmButtonText);
    Button deleteButton = null;
    if (toDelete) {
      deleteButton = new Button(messages.delete());
    }
    FlowPanel footer = new FlowPanel();

    layout.add(referencesType);
    layout.add(genericField);
    layout.add(footer);
    footer.add(cancelButton);
    footer.add(confirmButton);
    if (deleteButton != null) {
      footer.add(deleteButton);
      deleteButton.addClickHandler(event -> {
        dialogBox.hide();
        ExternalLobsDialogBoxResult result = new ExternalLobsDialogBoxResult("delete", true);
        callback.onSuccess(result);
      });
    }

    dialogBox.setWidget(layout);

    dialogBox.setGlassEnabled(true);
    dialogBox.setAnimationEnabled(false);

    cancelButton.addClickHandler(event -> {
      dialogBox.hide();
      ExternalLobsDialogBoxResult result = new ExternalLobsDialogBoxResult("add", false);
      callback.onSuccess(result);
    });

    confirmButton.addClickHandler(event -> {
      dialogBox.hide();
      ExternalLobsDialogBoxResult result = new ExternalLobsDialogBoxResult("add", true);
      callback.onSuccess(result);
    });

    dialogBox.addStyleName("dialog-external-lobs");
    layout.addStyleName("dialog-external-lobs-layout");
    footer.addStyleName("dialog-external-lobs-layout-footer");
    cancelButton.addStyleName("btn btn-link");
    confirmButton.addStyleName("btn btn-play");
    if (deleteButton != null) {
      deleteButton.addStyleName("btn");
    }

    dialogBox.center();
    dialogBox.show();
  }

  public static void showConfirmDialog(String title, String message, String cancelButtonText, String confirmButtonText,
    final AsyncCallback<Boolean> callback) {
    final DialogBox dialogBox = new DialogBox(false, true);
    dialogBox.setText(title);

    FlowPanel layout = new FlowPanel();
    Label messageLabel = new Label(message);
    Button cancelButton = new Button(cancelButtonText);
    Button confirmButton = new Button(confirmButtonText);
    FlowPanel footer = new FlowPanel();

    layout.add(messageLabel);
    layout.add(footer);
    footer.add(cancelButton);
    footer.add(confirmButton);

    dialogBox.setWidget(layout);

    dialogBox.setGlassEnabled(true);
    dialogBox.setAnimationEnabled(false);

    cancelButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        callback.onSuccess(false);
      }
    });

    confirmButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        callback.onSuccess(true);
      }
    });

    dialogBox.addStyleName("wui-dialog-confirm");
    layout.addStyleName("wui-dialog-layout");
    footer.addStyleName("wui-dialog-layout-footer");
    messageLabel.addStyleName("wui-dialog-message");
    cancelButton.addStyleName("btn btn-link");
    confirmButton.addStyleName("btn btn-play");

    dialogBox.center();
    dialogBox.show();
  }

  public static void showInformationDialog(String title, String message, String continueButtonText) {
    showInformationDialog(title, message, continueButtonText, new NoAsyncCallback<Void>());
  }

  public static void showInformationDialog(String title, String message, String continueButtonText,
    final AsyncCallback<Void> callback) {
    final DialogBox dialogBox = new DialogBox(false, true);
    dialogBox.setText(title);

    FlowPanel layout = new FlowPanel();
    Label messageLabel = new Label(message);
    Button continueButton = new Button(continueButtonText);

    layout.add(messageLabel);
    layout.add(continueButton);

    dialogBox.setWidget(layout);

    dialogBox.setGlassEnabled(true);
    dialogBox.setAnimationEnabled(false);

    continueButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        callback.onSuccess(null);
      }
    });

    dialogBox.addStyleName("wui-dialog-information");
    layout.addStyleName("wui-dialog-layout");
    messageLabel.addStyleName("wui-dialog-message");
    continueButton.addStyleName("btn btn-play");

    dialogBox.center();
    dialogBox.show();
  }

  public static DialogBox showLoadingModel() {
    final DialogBox dialogBox = new DialogBox(false, true);
    dialogBox.setText("Loading...");

    FlowPanel layout = new FlowPanel();
    Label messageLabel = new Label(messages.name());

    layout.add(messageLabel);

    dialogBox.setWidget(layout);

    dialogBox.setGlassEnabled(true);
    dialogBox.setAnimationEnabled(false);

    dialogBox.addStyleName("wui-dialog-information");
    layout.addStyleName("wui-dialog-layout");
    messageLabel.addStyleName("wui-dialog-message");

    dialogBox.center();
    dialogBox.show();
    return dialogBox;
  }

  public static void showPromptDialog(String title, String message, String placeHolder, final RegExp validator,
    String cancelButtonText, String confirmButtonText, final AsyncCallback<String> callback) {
    final DialogBox dialogBox = new DialogBox(false, true);
    dialogBox.setText(title);

    final FlowPanel layout = new FlowPanel();

    if (message != null) {
      final Label messageLabel = new Label(message);
      layout.add(messageLabel);
      messageLabel.addStyleName("wui-dialog-message");
    }

    final TextBox inputBox = new TextBox();

    if (placeHolder != null) {
      inputBox.getElement().setPropertyString("placeholder", placeHolder);
    }

    final Button cancelButton = new Button(cancelButtonText);
    final Button confirmButton = new Button(confirmButtonText);

    layout.add(inputBox);
    layout.add(cancelButton);
    layout.add(confirmButton);

    dialogBox.setWidget(layout);

    dialogBox.setGlassEnabled(true);
    dialogBox.setAnimationEnabled(false);

    cancelButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        callback.onFailure(null);
      }
    });

    confirmButton.addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        callback.onSuccess(inputBox.getText());
      }
    });

    inputBox.addValueChangeHandler(new ValueChangeHandler<String>() {

      @Override
      public void onValueChange(ValueChangeEvent<String> event) {
        boolean isValid = validator.test(inputBox.getText());
        if (isValid) {
          inputBox.addStyleName("error");
        } else {
          inputBox.removeStyleName("error");
        }
      }
    });

    inputBox.addKeyPressHandler(new KeyPressHandler() {

      @Override
      public void onKeyPress(KeyPressEvent event) {
        boolean isValid = validator.test(inputBox.getText());
        confirmButton.setEnabled(isValid);
      }
    });

    inputBox.addKeyDownHandler(new KeyDownHandler() {

      @Override
      public void onKeyDown(KeyDownEvent event) {
        if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
          boolean isValid = validator.test(inputBox.getText());
          if (isValid) {
            dialogBox.hide();
            callback.onSuccess(inputBox.getText());
          }
        }
      }

    });

    confirmButton.setEnabled(validator.test(inputBox.getText()));

    dialogBox.addStyleName("wui-dialog-prompt");
    layout.addStyleName("wui-dialog-layout");
    inputBox.addStyleName("form-textbox wui-dialog-message");
    cancelButton.addStyleName("btn btn-link");
    confirmButton.addStyleName("pull-right btn btn-play");

    dialogBox.center();
    dialogBox.show();
    inputBox.setFocus(true);
  }
}