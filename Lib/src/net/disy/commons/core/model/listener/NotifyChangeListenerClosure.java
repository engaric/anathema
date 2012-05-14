/**
 * Copyright (C) 2005, 2011 disy Informationssysteme GmbH and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package net.disy.commons.core.model.listener;

import net.disy.commons.core.util.IClosure;

public final class NotifyChangeListenerClosure implements IClosure<IChangeListener> {

  public final static NotifyChangeListenerClosure INSTANCE = new NotifyChangeListenerClosure();

  @Override
  public void execute(final IChangeListener listener) {
    listener.stateChanged();
  }
}