package com.databasepreservation.common.client.common.utils;

/**
 * @author Gabriel Barros <gbarros@keep.pt>
 */
import com.google.gwt.core.client.GWT;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Tree<T> implements Serializable {
  private static final long serialVersionUID = 5586782882694784478L;
  private T value;
  private Tree<T> parent;
  private List<Tree<T>> children;

  public Tree() {
    super();
  }

  public Tree(T value) {
    this.value = value;
    this.children = new LinkedList<>();
  }

  public Tree<T> addChild(T value) {
    Tree<T> childNode = new Tree<>(value);
    childNode.parent = this;
    this.children.add(childNode);
    return childNode;
  }

  public T getValue() {
    return value;
  }

  public void setValue(T value) {
    this.value = value;
  }

  public Tree<T> getParent() {
    return parent;
  }

  public void setParent(Tree<T> parent) {
    this.parent = parent;
  }

  public List<Tree<T>> getChildren() {
    return children;
  }

  public void setChildren(List<Tree<T>> children) {
    this.children = children;
  }

  public Tree<T> searchTop(T target){
    if(this.getParent() == null){
      return null;
    }

    if(parent.getValue().equals(target)){
      return parent;
    }
    return this.getParent().searchTop(target);
  }
}
