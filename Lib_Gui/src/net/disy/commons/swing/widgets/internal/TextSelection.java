/**
 * Copyright (C) 2005, 2011 disy Informationssysteme GmbH and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package net.disy.commons.swing.widgets.internal;

import net.disy.commons.core.util.Ensure;

public class TextSelection {

  public static TextSelection createSelection(
      final TextPosition position1,
      final TextPosition position2) {
    Ensure.ensureArgumentNotNull(position1);
    Ensure.ensureArgumentNotNull(position2);
    if (position1.getBlockIndex() < position2.getBlockIndex()) {
      return new TextSelection(position1, position2);
    }
    if (position1.getBlockIndex() == position2.getBlockIndex()
        && position1.getIndexInBlock() <= position2.getIndexInBlock()) {
      return new TextSelection(position1, position2);
    }
    return new TextSelection(position2, position1);
  }

  public final TextPosition endPosition;
  public final TextPosition startPosition;

  private TextSelection(final TextPosition startPosition, final TextPosition endPosition) {
    this.startPosition = startPosition;
    this.endPosition = endPosition;
  }

  @Override
  public boolean equals(final Object obj) {
    if (!(obj instanceof TextSelection)) {
      return false;
    }
    final TextSelection other = (TextSelection) obj;
    return startPosition.equals(other.startPosition) && endPosition.equals(other.endPosition);
  }

  @Override
  public int hashCode() {
    return startPosition.hashCode() + endPosition.hashCode() * 17;
  }
}