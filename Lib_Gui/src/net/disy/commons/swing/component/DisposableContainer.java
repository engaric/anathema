/**
 * Copyright (C) 2005, 2011 disy Informationssysteme GmbH and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package net.disy.commons.swing.component;

import net.disy.commons.swing.dispose.AggregatedDisposable;
import net.disy.commons.swing.dispose.IDisposable;

public class DisposableContainer implements IDisposable {

  private final AggregatedDisposable disposables = new AggregatedDisposable();

  protected final void addDisposable(final IDisposable disposable) {
    disposables.add(disposable);
  }

  @Override
  public void dispose() {
    disposables.dispose();
  }
}