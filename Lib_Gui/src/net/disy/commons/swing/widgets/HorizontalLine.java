/**
 * Copyright (C) 2005, 2011 disy Informationssysteme GmbH and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package net.disy.commons.swing.widgets;

import net.disy.commons.swing.color.SwingColors;

import javax.swing.JComponent;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;

/**
 * A horizontal line that can be used for visual structuring of gui layouts.
 */
public class HorizontalLine extends JComponent {

  private Insets margin = new Insets(0, 0, 0, 0);

  public HorizontalLine() {
    this(100);
  }

  public HorizontalLine(final int preferredWidth) {
    setPreferredSize(new Dimension(preferredWidth, 2));
  }

  @Override
  public Dimension getPreferredSize() {
    final Dimension dimension = super.getPreferredSize();
    return new Dimension(dimension.width + margin.left + margin.right, dimension.height
        + margin.top
        + margin.bottom);
  }

  @Override
  protected void paintComponent(final Graphics g) {
    final int y = margin.top + (getSize().height - 2 - margin.top - margin.bottom) / 2;
    g.setColor(SwingColors.getControlShadowColor());
    g.drawLine(margin.left, y, getSize().width - margin.right, y);
    g.setColor(SwingColors.getControlLtHighlightColor());
    g.drawLine(margin.left, y + 1, getSize().width - margin.right, y + 1);
  }
}