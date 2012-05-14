/**
 * Copyright (C) 2005, 2011 disy Informationssysteme GmbH and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 */
package net.disy.commons.swing.resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class DisyCommonsSwingMessages {
  private static final String BUNDLE_NAME = "net.disy.commons.swing.resources.messages";//$NON-NLS-1$

  private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

  private DisyCommonsSwingMessages() {
    //no instance available
  }

  public static String getString(final String key) {
    try {
      return RESOURCE_BUNDLE.getString(key);
    }
    catch (final MissingResourceException e) {
      return '!' + key + '!';
    }
  }
}